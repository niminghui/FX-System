package com.shiep.fxbankcard.controller;

import com.shiep.fxbankcard.entity.FxUser;
import com.shiep.fxbankcard.service.IFxUserService;
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
    @Autowired
    private IFxUserService userService;

    @GetMapping("/chineseName/{chineseName}")
    public List<FxUser> getByChineseName(@PathVariable("chineseName") String chineseName) {
        return userService.getByChineseName(chineseName);
    }

    @GetMapping("/idNumber/{idNumber}")
    public FxUser getByIdCardNum(@PathVariable("idNumber") String idNumber) {
        return userService.getByIdCardNum(idNumber);
    }

    @GetMapping
    public List<FxUser> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public FxUser createFxUser(@RequestBody FxUser user) {
        return userService.insert(user);
    }
}
