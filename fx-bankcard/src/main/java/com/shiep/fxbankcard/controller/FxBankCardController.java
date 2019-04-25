package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxAsset;
import com.shiep.fxbankcard.entity.FxBankCard;
import com.shiep.fxbankcard.entity.FxCurrency;
import com.shiep.fxbankcard.entity.FxTransactionRecord;
import com.shiep.fxbankcard.model.TransactionTypeEnum;
import com.shiep.fxbankcard.service.IFxAssetService;
import com.shiep.fxbankcard.service.IFxBankCardService;
import com.shiep.fxbankcard.service.IFxCurrencyService;
import com.shiep.fxbankcard.service.IFxTransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/15 17:32
 * @description: 银行卡控制层
 */
@RestController
@RequestMapping(path = "/bankcard", produces = "application/json;charset=utf-8")
public class FxBankCardController {

    @Autowired
    private IFxBankCardService bankCardService;

    @Autowired
    private IFxAssetService assetService;

    @Autowired
    private IFxCurrencyService currencyService;

    @Autowired
    private IFxTransactionRecordService transactionRecordService;

    @GetMapping("/userID/{userID}")
    public FxBankCard findByUserID(@PathVariable("userID") String userID) {
        return bankCardService.findByUserID(userID);
    }

    @GetMapping("/status/{status}")
    public List<FxBankCard> findByStatus(@PathVariable("status") Integer status) {
        return bankCardService.findByStatus(status);
    }

    @GetMapping("/after/{time}")
    public List<FxBankCard> findByCreatedTimeAfter(@PathVariable("time") Timestamp time) {
        return bankCardService.findByCreatedTimeAfter(time);
    }

    @GetMapping("/before/{time}")
    public List<FxBankCard> findByCreatedTimeBefore(@PathVariable("time") Timestamp time) {
        return bankCardService.findByCreatedTimeBefore(time);
    }

    @GetMapping("/between/{beginTime}/{endTime}")
    public List<FxBankCard> findByCreatedTimeBetween(@PathVariable("beginTime") Timestamp beginTime, @PathVariable("endTime") Timestamp endTime) {
        return bankCardService.findByCreatedTimeBetween(beginTime, endTime);
    }

    @GetMapping("/id/{id}")
    public FxBankCard findByBankCardId(@PathVariable("id") String bankCardId) {
        return bankCardService.findByBankCardId(bankCardId);
    }

    @PostMapping("/create/{createdPlace}/{userID}")
    public FxBankCard createInitBankCard(@PathVariable("createdPlace") String createdPlace, @PathVariable("userID") String userID) {
        return bankCardService.createInitBankCard(createdPlace, userID);
    }

    @PostMapping("/initAsset")
    public List<FxAsset> initAsset(@RequestParam("bankcardID") String bankcardID, @RequestParam("money") BigDecimal money){
        if (bankCardService.findByBankCardId(bankcardID) == null){
            return null;
        }
        List<FxCurrency> currencyList = currencyService.getAll();
        List<FxAsset> result = new ArrayList<>();
        for (FxCurrency currency : currencyList){
            FxAsset asset = new FxAsset();
            asset.setBankcardId(bankcardID);
            asset.setCurrencyCode(currency.getEnglishName());
            asset.setBalance(money);
            result.add(assetService.create(asset));
            FxTransactionRecord transactionRecord = new FxTransactionRecord();
            transactionRecord.setBankcardId(asset.getBankcardId());
            transactionRecord.setCurrencyCode(asset.getCurrencyCode());
            transactionRecord.setMoney(asset.getBalance());
            transactionRecord.setType(TransactionTypeEnum.TRANSFER_INTO.getCode());
            transactionRecord.setTransactionPeople("FX-System");
            transactionRecord.setTransactionPlace("localhost");
            transactionRecord.setTransactionTime(new Timestamp(System.currentTimeMillis()));
            transactionRecordService.create(transactionRecord);
        }
        return result;
    }

    @PutMapping("/active/{bankCardId}")
    public Boolean activeBankCard(@PathVariable("bankCardId") String bankCardId) {
        return bankCardService.activeBankCard(bankCardId);
    }

    @PutMapping("/freeze/{bankCardId}")
    public FxBankCard freezeBankCard(@PathVariable("bankCardId") String bankCardId) {
        return bankCardService.freezeBankCard(bankCardId);
    }

    @PutMapping("/unfreeze/{bankCardId}")
    public FxBankCard unFreezeBankCard(@PathVariable("bankCardId") String bankCardId) {
        return bankCardService.unFreezeBankCard(bankCardId);
    }

    @PutMapping("/password")
    public FxBankCard updatePassword(@RequestParam("bankCardId") String bankCardId, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        return bankCardService.updatePassword(bankCardId, oldPassword, newPassword);
    }

    @PutMapping("/reset/{bankCardId}")
    public String resetInitPassword(@PathVariable("bankCardId") String bankCardId){
        return bankCardService.resetInitPassword(bankCardId);
    }

    @DeleteMapping("/{bankCardId}")
    public FxBankCard deleteBankCard(@PathVariable("bankCardId") String bankCardId) {
        return bankCardService.deleteBankCard(bankCardId);
    }
}
