package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxCurrency;
import com.shiep.fxbankcard.service.IFxCurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/18 14:05
 * @description: 货币控制层
 */
@RestController
@RequestMapping(path = "/currency", produces = "application/json;charset=utf-8")
public class FxCurrencyController {
    private Logger logger = LoggerFactory.getLogger(FxCurrencyController.class);

    @Autowired
    private IFxCurrencyService currencyService;

    @GetMapping
    public List<FxCurrency> getAll() {
        logger.info("query all currency");
        return currencyService.getAll();
    }

    @GetMapping("/init")
    public List<FxCurrency> initCurrency() {
        List<FxCurrency> currencyList = currencyService.init();
        logger.info("Initialize the currency:" + currencyList);
        return currencyList;
    }

    @GetMapping("/name/{currencyName}")
    public FxCurrency getFxCurrencyByName(@PathVariable("currencyName") String currencyName) {
        logger.info("query currency by name:" + currencyName);
        return currencyService.findByCurrencyName(currencyName);
    }

    @GetMapping("/code/{currencyCode}")
    public FxCurrency getFxCurrencyByCode(@PathVariable("currencyCode") String currencyCode) {
        logger.info("query currency by code:" + currencyCode);
        return currencyService.findByCurrencyCode(currencyCode);
    }

    @PostMapping
    public FxCurrency create(@RequestBody FxCurrency currency) {
        logger.info("create currency:" + currency);
        return currencyService.create(currency);
    }

    @PutMapping("/{currencyCode}/{type}/{value}")
    public Boolean updateRate(@PathVariable("currencyCode") String currencyCode, @PathVariable("type") Integer type,
                              @PathVariable("value") BigDecimal value) {
        logger.info("uppdate currency rate");
        switch (type) {
            case 1:
                return currencyService.updateCurrencyDIR(currencyCode, value);
            case 2:
                return currencyService.updateCurrencyTDR1(currencyCode, value);
            case 3:
                return currencyService.updateCurrencyTDR3(currencyCode, value);
            case 4:
                return currencyService.updateCurrencyTDR6(currencyCode, value);
            case 5:
                return currencyService.updateCurrencyTDR12(currencyCode, value);
            default:
                return false;
        }
    }

    @DeleteMapping("/{currencyCode}")
    public FxCurrency delete(@PathVariable("currencyCode") String currencyCode) {
        logger.info("delete currency with code " + currencyCode);
        return currencyService.delete(currencyCode);
    }
}
