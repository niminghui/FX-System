package com.shiep.fxauth.controller;

import com.shiep.fxauth.common.CurrencyEnum;
import com.shiep.fxauth.common.TransactionTypeEnum;
import com.shiep.fxauth.endpoint.ICurrencyService;
import com.shiep.fxauth.endpoint.ITransactionRecordService;
import com.shiep.fxauth.utils.CookieUtils;
import com.shiep.fxauth.utils.JwtTokenUtils;
import com.shiep.fxauth.utils.RedisUtils;
import com.shiep.fxauth.utils.TimestampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author: 倪明辉
 * @date: 2019/4/22 16:24
 * @description: 存款业务控制层
 */
@Controller
@RequestMapping("/deposit")
public class DepositController {
    @Autowired
    private ICurrencyService currencyService;
    @Autowired
    private ITransactionRecordService transactionRecordService;

    @GetMapping("/rate")
    public ModelAndView showDepositRate() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("rate", currencyService.getAll());
        mv.setViewName("depositRate");
        return mv;
    }

    @GetMapping("/transaction/record")
    public ModelAndView toTransactionRecordPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("transactionRecord");
        mv.addObject("currencyList", CurrencyEnum.values());
        mv.addObject("typeList", TransactionTypeEnum.values());
        return mv;
    }

    @GetMapping("/transaction/record/query")
    public ModelAndView queryTransactionRecord(@RequestParam("beginTime") String beginTimeStr,
                                               @RequestParam("endTime") String endTimeStr,
                                               @RequestParam("currency") String currencyStr,
                                               @RequestParam("type") String typeStr,
                                               HttpServletRequest request) {
        Integer type;
        if (currencyStr.isEmpty()) {
            currencyStr = null;
        }
        if (typeStr.isEmpty()) {
            type = null;
        } else {
            type = Integer.parseInt(typeStr);
        }
        Timestamp beginTime = TimestampUtils.parse(beginTimeStr);
        Timestamp endTime = TimestampUtils.parse(endTimeStr);
        String tokenHeader = URLDecoder.decode(CookieUtils.getCookie(request, "token").getValue());
        String userToken = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String bankcardID = (String) RedisUtils.hGet("bankcardID", JwtTokenUtils.getUsername(userToken));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("transactionRecord");
        mv.addObject("currencyList", CurrencyEnum.values());
        mv.addObject("typeList", TransactionTypeEnum.values());
        mv.addObject("show", true);
        mv.addObject("recordList", transactionRecordService.query(bankcardID, currencyStr, type, beginTime, endTime));
        return mv;
    }
}
