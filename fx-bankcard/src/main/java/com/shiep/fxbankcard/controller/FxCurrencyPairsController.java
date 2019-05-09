package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxCurrencyPairs;
import com.shiep.fxbankcard.service.IFxCurrencyPairsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(FxCurrencyPairsController.class);

    @Autowired
    private IFxCurrencyPairsService currencyPairsService;

    @GetMapping
    public List<FxCurrencyPairs> getAll() {
        logger.info("query all currency pairs");
        return currencyPairsService.getAll();
    }

    @GetMapping("/init")
    public List<FxCurrencyPairs> initCurrencyPairs() {
        List<FxCurrencyPairs> currencyPairsList = currencyPairsService.init();
        logger.info("Initialize the currencyPairs:" + currencyPairsList);
        return currencyPairsList;
    }

    @PostMapping
    public FxCurrencyPairs create(@RequestBody FxCurrencyPairs currencyPairs) {
        logger.info("create currency pairs:" + currencyPairs);
        return currencyPairsService.create(currencyPairs);
    }

    @DeleteMapping
    public FxCurrencyPairs delete(@RequestBody FxCurrencyPairs currencyPairs) {
        logger.info("delete currency pairs:" + currencyPairs);
        return currencyPairsService.delete(currencyPairs.getBasicCurrency(), currencyPairs.getSecondaryCurrency());
    }
}
