package com.shiep.fxauth.controller;

import com.shiep.fxauth.common.CurrencyEnum;
import com.shiep.fxauth.common.TransactionRecordPage;
import com.shiep.fxauth.common.TransactionTypeEnum;
import com.shiep.fxauth.endpoint.*;
import com.shiep.fxauth.model.FxAsset;
import com.shiep.fxauth.model.FxBankCard;
import com.shiep.fxauth.model.FxTransactionRecord;
import com.shiep.fxauth.utils.CookieUtils;
import com.shiep.fxauth.utils.JwtTokenUtils;
import com.shiep.fxauth.utils.RedisUtils;
import com.shiep.fxauth.utils.TimestampUtils;
import com.shiep.fxauth.vo.TransactionRecordQueryVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/4/22 16:24
 * @description: 存款业务控制层
 */
@Controller
@RequestMapping("/deposit")
public class DepositController {
    private static final Logger logger = LoggerFactory.getLogger(DepositController.class);

    @Autowired
    private ICurrencyService currencyService;
    @Autowired
    private ITransactionRecordService transactionRecordService;
    @Autowired
    private IAssetService assetService;
    @Autowired
    private ITransactionService transactionService;
    @SuppressWarnings("all")
    @Autowired
    private IBankCardService bankCardService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    @GetMapping("/currentInterest")
    public ModelAndView toCurrentInterestPage(HttpServletRequest request) {
        String tokenHeader = URLDecoder.decode(CookieUtils.getCookie(request, "token").getValue());
        String userToken = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String bankcardID = (String) RedisUtils.hGet("bankcardID", JwtTokenUtils.getUsername(userToken));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("currentInterest");
        mv.addObject("currencyList", CurrencyEnum.values());
        TransactionRecordPage transactionRecordPage = transactionRecordService.query(bankcardID, null,
                TransactionTypeEnum.DEPOSIT_INTEREST_INTO.getCode(), null, null, 1, 5);
        if (transactionRecordPage == null) {
            mv.addObject("show", false);
        } else {
            mv.addObject("show", true);
            mv.addObject("recordPage", transactionRecordPage);
        }
        return mv;
    }

    @PostMapping("/currentInterest/settlement")
    public ModelAndView settlementCurrentInterest(@RequestParam("currency") String currency, HttpServletRequest request) {
        String tokenHeader = URLDecoder.decode(CookieUtils.getCookie(request, "token").getValue());
        String userToken = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String bankcardID = (String) RedisUtils.hGet("bankcardID", JwtTokenUtils.getUsername(userToken));
        // 结算全部币种的活期利息
        if (StringUtils.isEmpty(currency)) {
            List<FxAsset> assetList = assetService.getAll(bankcardID);
            for (FxAsset asset : assetList) {
                FxTransactionRecord record = transactionService.calculateCurrentInterest(bankcardID, asset.getCurrencyCode());
                if (record != null) {
                    logger.info(record.toString());
                }
            }
        } else {
            FxTransactionRecord record = transactionService.calculateCurrentInterest(bankcardID, currency);
            if (record != null) {
                logger.info(record.toString());
            }
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("currentInterest");
        mv.addObject("currencyList", CurrencyEnum.values());
        TransactionRecordPage transactionRecordPage = transactionRecordService.query(bankcardID, null,
                TransactionTypeEnum.DEPOSIT_INTEREST_INTO.getCode(), null, null, 1, 5);
        if (transactionRecordPage == null) {
            mv.addObject("show", false);
        } else {
            mv.addObject("show", true);
            mv.addObject("recordPage", transactionRecordPage);
        }
        return mv;
    }

    @GetMapping
    public ModelAndView toDepositPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("currencyList", CurrencyEnum.values());
        mv.setViewName("deposit");
        return mv;
    }

    @PostMapping("/check/{bankcardID}/{password}")
    @ResponseBody
    public String checkBankcardIdAndPassword(@PathVariable("bankcardID") String bankcardID,
                                             @PathVariable("password") String password) {
        FxBankCard bankCard = bankCardService.findByBankCardId(bankcardID);
        if (bankCard == null) {
            return "该银行卡不存在";
        }
        if (!passwordEncoder.matches(password, bankCard.getPassword())) {
            return "请检查银行卡密码是否输入正确";
        }
        return "true";
    }

    @PostMapping("/record")
    public ModelAndView fxDeposit(@RequestParam("bankcardID") String bankcardID,
                                  @RequestParam("currency") String currency,
                                  @RequestParam("money") String money,
                                  @RequestParam("password") String password) {
        ModelAndView mv = new ModelAndView();
        FxTransactionRecord record = transactionService.deposit(bankcardID, currency, new BigDecimal(money));
        mv.setViewName("transactionRecord");
        mv.addObject("currencyList", CurrencyEnum.values());
        mv.addObject("typeList", TransactionTypeEnum.values());
        mv.addObject("queryVo", new TransactionRecordQueryVo());
        if (record == null) {
            mv.addObject("show", false);
        } else {
            TransactionRecordPage transactionRecordPage = transactionRecordService.query(record.getBankcardId(), record.getCurrencyCode(),
                    record.getType(), record.getTransactionTime(), record.getTransactionTime(), 1, 5);
            mv.addObject("recordPage", transactionRecordPage);
            mv.addObject("show", true);
        }
        return mv;
    }

    @GetMapping("/transfer")
    public ModelAndView toTransferPage(HttpServletRequest request) {
        String tokenHeader = URLDecoder.decode(CookieUtils.getCookie(request, "token").getValue());
        String userToken = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String bankcardID = (String) RedisUtils.hGet("bankcardID", JwtTokenUtils.getUsername(userToken));
        ModelAndView mv = new ModelAndView();
        mv.addObject("bankcardID", bankcardID);
        mv.addObject("currencyList", CurrencyEnum.values());
        mv.setViewName("transfer");
        return mv;
    }

    @PostMapping("/check/{bankcardID}/{password}/{otherBankcardID}")
    @ResponseBody
    public String transferCheck(@PathVariable("bankcardID") String bankcardID,
                                @PathVariable("password") String password,
                                @PathVariable("otherBankcardID") String otherBankcardID) {
        if (bankCardService.findByBankCardId(otherBankcardID) == null) {
            return "要转入的银行卡不存在";
        }
        FxBankCard bankCard = bankCardService.findByBankCardId(bankcardID);
        if (!passwordEncoder.matches(password, bankCard.getPassword())) {
            return "请检查银行卡密码是否输入正确";
        }
        return "true";
    }

    @PostMapping("/query/asset/{bankcardID}/{currency}")
    @ResponseBody
    public String queryAssetBalance(@PathVariable("bankcardID") String bankcardID,
                                    @PathVariable("currency") String currency) {
        FxAsset asset = assetService.get(bankcardID, currency);
        if (asset == null) {
            return "0";
        }
        return asset.getBalance().toString();
    }

    @PostMapping("/transfer/record")
    public ModelAndView transfer(@RequestParam("bankcardID") String bankcardID,
                                 @RequestParam("otherBankcardID") String otherBankcardID,
                                 @RequestParam("currency") String currency,
                                 @RequestParam("money") String money) {
        ModelAndView mv = new ModelAndView();
        FxTransactionRecord record = transactionService.transfer(bankcardID, currency, new BigDecimal(money), otherBankcardID);
        mv.setViewName("transactionRecord");
        mv.addObject("currencyList", CurrencyEnum.values());
        mv.addObject("typeList", TransactionTypeEnum.values());
        mv.addObject("queryVo", new TransactionRecordQueryVo());
        if (record == null) {
            mv.addObject("show", false);
        }
        // 转账成功后跳转到查询交易记录界面，并显示当前的转账记录
        else {
            TransactionRecordPage transactionRecordPage = transactionRecordService.query(record.getBankcardId(), record.getCurrencyCode(),
                    record.getType(), record.getTransactionTime(), record.getTransactionTime(), 1, 5);
            mv.addObject("recordPage", transactionRecordPage);
            mv.addObject("show", true);
        }
        return mv;
    }

    @GetMapping("/asset")
    public ModelAndView toAssetQueryPage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("currencyList", CurrencyEnum.values());
        mv.setViewName("assetQuery");
        return mv;
    }

    @GetMapping("/asset/query")
    public ModelAndView queryAsset(@RequestParam("currency") String currency, HttpServletRequest request) {
        String tokenHeader = URLDecoder.decode(CookieUtils.getCookie(request, "token").getValue());
        String userToken = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String bankcardID = (String) RedisUtils.hGet("bankcardID", JwtTokenUtils.getUsername(userToken));
        ModelAndView mv = new ModelAndView();
        List<FxAsset> assetList;
        if (StringUtils.isEmpty(currency)) {
            assetList = assetService.getAll(bankcardID);
        } else {
            assetList = new ArrayList<>();
            assetList.add(assetService.get(bankcardID, currency));
        }
        mv.addObject("assetList", assetList);
        mv.addObject("currencyList", CurrencyEnum.values());
        mv.setViewName("assetQuery");
        return mv;
    }
}
