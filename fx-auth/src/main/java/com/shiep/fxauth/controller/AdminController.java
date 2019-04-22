package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.ICurrencyPairsService;
import com.shiep.fxauth.endpoint.ICurrencyService;
import com.shiep.fxauth.model.FxCurrency;
import com.shiep.fxauth.model.FxCurrencyPairs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 16:55
 * @description: admin控制层
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ICurrencyService currencyService;

    @Autowired
    private ICurrencyPairsService currencyPairsService;

    @GetMapping("/init/currency")
    public List<FxCurrency> initCurrency() {
        return currencyService.initCurrency();
    }

    @GetMapping("/init/currencyPairs")
    public List<FxCurrencyPairs> initCurrencyPairs() {
        return currencyPairsService.initCurrencyPairs();
    }
}
