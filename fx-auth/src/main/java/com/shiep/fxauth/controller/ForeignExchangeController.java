package com.shiep.fxauth.controller;

import com.shiep.fxauth.common.HeadlinesTypeEnum;
import com.shiep.fxauth.endpoint.IApiService;
import com.shiep.fxauth.endpoint.IBankCardService;
import com.shiep.fxauth.endpoint.ITransactionService;
import com.shiep.fxauth.model.FxBankCard;
import com.shiep.fxauth.utils.CookieUtils;
import com.shiep.fxauth.utils.JwtTokenUtils;
import com.shiep.fxauth.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLDecoder;

/**
 * @author: 倪明辉
 * @date: 2019/4/25 10:18
 * @description: 外汇兑换业务控制层
 */
@Controller
@RequestMapping("/fx")
public class ForeignExchangeController {
    @Autowired
    private IApiService apiService;
    @SuppressWarnings("all")
    @Autowired
    private IBankCardService bankCardService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ITransactionService transactionService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/headlines")
    public ModelAndView showHeadlines() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("types", HeadlinesTypeEnum.values());
        // 默认是国际新闻
        mv.addObject("headlines", apiService.getHeadlinesPageable("guoji", 3));
        mv.setViewName("headlines");
        return mv;
    }

    @GetMapping("/headlines/{type}")
    public ModelAndView showHeadlinesWithType(@PathVariable("type") String type) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("types", HeadlinesTypeEnum.values());
        // 默认是国际新闻
        mv.addObject("headlines", apiService.getHeadlinesPageable(type, 3));
        mv.setViewName("headlines");
        return mv;
    }

    @GetMapping("/transaction")
    public ModelAndView toFxTransactionPage() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String bankcardID = (String) RedisUtils.hGet("bankcardID", username);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("foreignExchange");
        mv.addObject("fxRate", apiService.getFxRate());
        mv.addObject("bankcardID", bankcardID);
        return mv;
    }

    @PostMapping("/transaction/{bankcardID}/{currencyPairs}/{money}/{type}/{password}/{rate}")
    @ResponseBody
    public String fxTransactionSettlement(@PathVariable String bankcardID,
                                          @PathVariable String currencyPairs,
                                          @PathVariable String money,
                                          @PathVariable String type,
                                          @PathVariable String password,
                                          @PathVariable String rate) {
        FxBankCard bankCard = bankCardService.findByBankCardId(bankcardID);
        if (!passwordEncoder.matches(password, bankCard.getPassword())) {
            return "密码错误";
        }
        String basicCurrency = currencyPairs.substring(0, 3);
        String secondaryCurrency = currencyPairs.substring(3);
        Boolean result = transactionService.foreignExchangeTrading(bankcardID, basicCurrency, secondaryCurrency,
                new BigDecimal(money), new BigDecimal(rate), Boolean.parseBoolean(type));
        if (result) {
            return "交易成功";
        }
        return "交易失败";
    }

    @GetMapping("/cny")
    public ModelAndView toCNYPage() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String bankcardID = (String) RedisUtils.hGet("bankcardID", username);
        ModelAndView mv = new ModelAndView();
        mv.addObject("rmbRate", apiService.getRmbRate());
        mv.addObject("bankcardID", bankcardID);
        mv.setViewName("cnyTransaction");
        return mv;
    }

    @PostMapping("/cny/transaction/{bankcardID}/{currency}/{money}/{type}/{password}/{rate}")
    @ResponseBody
    public String cnyTransactionSettlement(@PathVariable String bankcardID,
                                           @PathVariable String currency,
                                           @PathVariable String money,
                                           @PathVariable String type,
                                           @PathVariable String password,
                                           @PathVariable String rate) {
        FxBankCard bankCard = bankCardService.findByBankCardId(bankcardID);
        if (!passwordEncoder.matches(password, bankCard.getPassword())) {
            return "密码错误";
        }
        Boolean buy = Boolean.parseBoolean(type);
        Boolean result;
        // 结汇（外汇转换成本币，人民币）
        if (buy) {
            result = transactionService.foreignExchangeSettlement(bankcardID, currency, new BigDecimal(money), new BigDecimal(rate));
        } else {
            result = transactionService.foreignExchangePurchase(bankcardID, currency, new BigDecimal(money), new BigDecimal(rate));
        }
        if (result) {
            return "交易成功";
        }
        return "交易失败";
    }

    /**
     * description: 每隔5分钟，使用websocket发送外汇牌价到前端
     *
     * @param
     * @return void
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void sendFxRate() {
        simpMessagingTemplate.convertAndSend("/stomp/sub/fxRate", apiService.getFxRate());
    }

    /**
     * description: 每隔5分钟，使用websocket发送人民币牌价到前端
     *
     * @param
     * @return void
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void sendCNYRate() {
        simpMessagingTemplate.convertAndSend("/stomp/sub/cnyRate", apiService.getRmbRate());
    }
}
