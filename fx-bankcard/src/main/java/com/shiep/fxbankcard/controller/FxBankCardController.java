package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxBankCard;
import com.shiep.fxbankcard.service.IFxBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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

    @PostMapping("/{createdPlace}/{userID}")
    public FxBankCard createInitBankCard(@PathVariable("createdPlace") String createdPlace, @PathVariable("userID") String userID) {
        return bankCardService.createInitBankCard(createdPlace, userID);
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

    @PutMapping("/{bankCardId}/{oldPassword}/{newPassword}")
    public FxBankCard updatePassword(@PathVariable("bankCardId") String bankCardId,
                                     @PathVariable("oldPassword") String oldPassword,
                                     @PathVariable("newPassword") String newPassword) {
        return bankCardService.updatePassword(bankCardId, oldPassword, newPassword);
    }

    @DeleteMapping("/{bankCardId}")
    public FxBankCard deleteBankCard(@PathVariable("bankCardId") String bankCardId) {
        return bankCardService.deleteBankCard(bankCardId);
    }
}
