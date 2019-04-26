package com.shiep.fxauth.controller;

import com.alibaba.fastjson.JSON;
import com.shiep.fxauth.common.CurrencyEnum;
import com.shiep.fxauth.common.TransactionRecordPage;
import com.shiep.fxauth.common.TransactionTypeEnum;
import com.shiep.fxauth.endpoint.ICurrencyService;
import com.shiep.fxauth.endpoint.ITransactionRecordService;
import com.shiep.fxauth.utils.CookieUtils;
import com.shiep.fxauth.utils.JwtTokenUtils;
import com.shiep.fxauth.utils.RedisUtils;
import com.shiep.fxauth.utils.TimestampUtils;
import com.shiep.fxauth.vo.TransactionRecordQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.sql.Timestamp;

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
        mv.addObject("queryVo", new TransactionRecordQueryVo());
        mv.addObject("show", false);
        return mv;
    }

    @GetMapping("/transaction/record/query")
    public ModelAndView queryTransactionRecord(TransactionRecordQueryVo queryVo, HttpServletRequest request) {
        Integer type = null;
        String currency = null;
        Integer page = 1;
        if (!queryVo.getType().isEmpty()) {
            type = Integer.parseInt(queryVo.getType());
        }
        if (!queryVo.getCurrency().isEmpty()) {
            currency = queryVo.getCurrency();
        }
        if (!queryVo.getPage().isEmpty()) {
            page = Integer.parseInt(queryVo.getPage());
        }
        Timestamp beginTime = TimestampUtils.parse(queryVo.getBeginTime());
        Timestamp endTime = TimestampUtils.parse(queryVo.getEndTime());
        String tokenHeader = URLDecoder.decode(CookieUtils.getCookie(request, "token").getValue());
        String userToken = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String bankcardID = (String) RedisUtils.hGet("bankcardID", JwtTokenUtils.getUsername(userToken));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("transactionRecord");
        mv.addObject("currencyList", CurrencyEnum.values());
        mv.addObject("typeList", TransactionTypeEnum.values());
        TransactionRecordPage transactionRecordPage = transactionRecordService.query(bankcardID, currency, type, beginTime, endTime, page, 5);
        if (transactionRecordPage.getPageSize() != 0) {
            mv.addObject("show", true);
            mv.addObject("recordPage", transactionRecordPage);
            mv.addObject("queryVo", queryVo);
            return mv;
        }
        mv.addObject("show", false);
        mv.addObject("queryVo", queryVo);
        return mv;
    }
}
