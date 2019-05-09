package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxAsset;
import com.shiep.fxbankcard.service.IFxAssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 10:13
 * @description: 资产控制层
 */
@RestController
@RequestMapping(path = "/asset", produces = "application/json;charset=utf-8")
public class FxAssetController {
    private Logger logger = LoggerFactory.getLogger(FxAssetController.class);

    @Autowired
    private IFxAssetService assetService;

    @GetMapping("/{bankcardID}")
    public List<FxAsset> getAll(@PathVariable("bankcardID") String bankcardID) {
        logger.info("query all FxAsset");
        List<FxAsset> assetList = assetService.getAll(bankcardID);
        logger.info(assetList.toString());
        return assetList;
    }

    @GetMapping("/{bankcardID}/{currencyCode}")
    public FxAsset get(@PathVariable("bankcardID") String bankcardID, @PathVariable("currencyCode") String currencyCode) {
        logger.info("query FxAsset by bankcardID = " + bankcardID + " and currencyCode = " + currencyCode);
        FxAsset asset = assetService.get(bankcardID, currencyCode);
        logger.info(asset.toString());
        return asset;
    }

    @PostMapping
    public FxAsset create(@RequestBody FxAsset asset) {
        FxAsset result = assetService.create(asset);
        logger.info("create FxAsset:" + result);
        return result;
    }

    @PutMapping("/{bankcardID}/{currencyCode}/{newBalance}")
    public FxAsset updateBalance(@PathVariable("bankcardID") String bankcardID, @PathVariable("currencyCode") String currencyCode,
                                 @PathVariable("newBalance") BigDecimal newBalance) {
        logger.info("update asset balance");
        return assetService.updateBalance(bankcardID, currencyCode, newBalance);
    }
}
