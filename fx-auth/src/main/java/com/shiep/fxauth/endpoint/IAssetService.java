package com.shiep.fxauth.endpoint;

import com.shiep.fxauth.model.FxAsset;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/23 11:37
 * @description: 资产服务端点
 */
public interface IAssetService {

    /**
     * description: 通过银行卡号码查找该卡下的所有资产
     *
     * @param bankcardID 银行卡号码
     * @return java.util.List<com.shiep.fxbankcard.entity.FxAsset>
     */
    @GetMapping("/asset/{bankcardID}")
    List<FxAsset> getAll(@PathVariable("bankcardID") String bankcardID);

    /**
     * description: 通过银行卡号码和货币码查找该资产
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @return com.shiep.fxbankcard.entity.FxAsset
     */
    @GetMapping("/asset/{bankcardID}/{currencyCode}")
    FxAsset get(@PathVariable("bankcardID") String bankcardID, @PathVariable("currencyCode") String currencyCode);

    /**
     * description: 先根据银行卡和货币码查找，如果存在，则创建失败，否则创建该资产。
     *
     * @param asset 资产
     * @return com.shiep.fxbankcard.entity.FxAsset
     */
    @PostMapping("/asset")
    FxAsset create(FxAsset asset);

    /**
     * description: 先通过银行卡号码和货币码查找该资产，如果不存在则创建，存在则直接修改余额
     *
     * @param bankcardID   银行卡号码
     * @param currencyCode 货币码
     * @param newBalance   新余额
     * @return com.shiep.fxbankcard.entity.FxAsset
     */
    @PutMapping("/asset/{bankcardID}/{currencyCode}/{newBalance}")
    FxAsset updateBalance(@PathVariable("bankcardID") String bankcardID, @PathVariable("currencyCode") String currencyCode,
                          @PathVariable("newBalance") BigDecimal newBalance);
}
