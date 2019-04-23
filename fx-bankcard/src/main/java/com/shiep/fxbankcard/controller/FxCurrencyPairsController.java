package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxCurrencyPairs;
import com.shiep.fxbankcard.service.IFxCurrencyPairsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 16:13
 * @description: 货币对控制层
 */
@RestController
@RequestMapping(path = "/currencyPairs", produces = "application/json;charset=utf-8")
public class FxCurrencyPairsController {
    @Autowired
    private IFxCurrencyPairsService currencyPairsService;

    @GetMapping
    public List<FxCurrencyPairs> getAll() {
        return currencyPairsService.getAll();
    }

    @GetMapping("/init")
    public List<FxCurrencyPairs> initCurrencyPairs() {
        return currencyPairsService.init();
    }

    @PostMapping
    public FxCurrencyPairs create(@RequestBody FxCurrencyPairs currencyPairs) {
        return currencyPairsService.create(currencyPairs);
    }

    @DeleteMapping
    public FxCurrencyPairs delete(@RequestBody FxCurrencyPairs currencyPairs) {
        return currencyPairsService.delete(currencyPairs.getBasicCurrency(), currencyPairs.getSecondaryCurrency());
    }
}
