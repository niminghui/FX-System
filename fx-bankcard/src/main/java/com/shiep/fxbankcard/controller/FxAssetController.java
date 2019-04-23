package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxAsset;
import com.shiep.fxbankcard.service.IFxAssetService;
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
    @Autowired
    private IFxAssetService assetService;

    @GetMapping("/{bankcardID}")
    public List<FxAsset> getAll(@PathVariable("bankcardID") String bankcardID) {
        return assetService.getAll(bankcardID);
    }

    @GetMapping("/{bankcardID}/{currencyCode}")
    public FxAsset get(@PathVariable("bankcardID") String bankcardID, @PathVariable("currencyCode") String currencyCode) {
        return assetService.get(bankcardID, currencyCode);
    }

    @PostMapping
    public FxAsset create(@RequestBody FxAsset asset) {
        return assetService.create(asset);
    }

    @PutMapping("/{bankcardID}/{currencyCode}/{newBalance}")
    public FxAsset updateBalance(@PathVariable("bankcardID") String bankcardID, @PathVariable("currencyCode") String currencyCode,
                                 @PathVariable("newBalance") BigDecimal newBalance) {
        return assetService.updateBalance(bankcardID, currencyCode, newBalance);
    }
}
