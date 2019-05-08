package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.IBankCardService;
import com.shiep.fxauth.endpoint.ICurrencyPairsService;
import com.shiep.fxauth.endpoint.ICurrencyService;
import com.shiep.fxauth.model.FxAsset;
import com.shiep.fxauth.model.FxCurrency;
import com.shiep.fxauth.model.FxCurrencyPairs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 16:55
 * @description: admin控制层
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ICurrencyService currencyService;

    @Autowired
    private ICurrencyPairsService currencyPairsService;

    @SuppressWarnings("all")
    @Autowired
    private IBankCardService bankCardService;

    @GetMapping("/init/currency")
    @ResponseBody
    public List<FxCurrency> initCurrency() {
        return currencyService.initCurrency();
    }

    @GetMapping("/init/currencyPairs")
    @ResponseBody
    public List<FxCurrencyPairs> initCurrencyPairs() {
        return currencyPairsService.initCurrencyPairs();
    }

    @GetMapping("/init/asset/{bankcardID}")
    @ResponseBody
    public List<FxAsset> initDefaultAsset(@PathVariable("bankcardID") String bankcardID) {
        return bankCardService.initAsset(bankcardID, new BigDecimal("10000"));
    }

    @GetMapping("/init/asset/{bankcardID}/{money}")
    @ResponseBody
    public List<FxAsset> initAsset(@PathVariable("bankcardID") String bankcardID, @PathVariable("money") String money) {
        return bankCardService.initAsset(bankcardID, new BigDecimal(money));
    }

    @GetMapping("/a/druid/login")
    public String toAccountDruidPage() {
        return "redirect:/a/druid/login.html";
    }
}
