package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxUser;
import com.shiep.fxbankcard.service.IFxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/15 10:46
 * @description: 用户信息控制层
 */
@RestController
@RequestMapping(path = "/user", produces = "application/json;charset=utf-8")
public class FxUserController {
    private Logger logger = LoggerFactory.getLogger(FxUserController.class);

    @Autowired
    private IFxUserService userService;

    @GetMapping("/chineseName/{chineseName}")
    public List<FxUser> getByChineseName(@PathVariable("chineseName") String chineseName) {
        logger.info("query FxUser by chineseName " + chineseName);
        return userService.getByChineseName(chineseName);
    }

    @GetMapping("/idNumber/{idNumber}")
    public FxUser getByIdCardNum(@PathVariable("idNumber") String idNumber) {
        logger.info("query FxUser by idNumber " + idNumber);
        return userService.getByIdCardNum(idNumber);
    }

    @GetMapping("/email/{email}")
    public FxUser getByEmail(@PathVariable("email") String email){
        logger.info("query FxUser by email " + email);
        return userService.getByEmail(email);
    }

    @GetMapping
    public List<FxUser> getAll() {
        logger.info("query all FxUser");
        return userService.getAll();
    }

    @PostMapping
    public FxUser createFxUser(@RequestBody FxUser user) {
        logger.info("create FxUser");
        return userService.insert(user);
    }
}
