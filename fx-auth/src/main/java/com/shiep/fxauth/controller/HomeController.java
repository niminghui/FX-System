package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.IAccountService;
import com.shiep.fxauth.endpoint.IApiService;
import com.shiep.fxauth.endpoint.IBankCardService;
import com.shiep.fxauth.endpoint.IUserInfoService;
import com.shiep.fxauth.model.FxBankCard;
import com.shiep.fxauth.model.FxUser;
import com.shiep.fxauth.service.IMailService;
import com.shiep.fxauth.utils.CookieUtils;
import com.shiep.fxauth.utils.IpUtils;
import com.shiep.fxauth.utils.JwtTokenUtils;
import com.shiep.fxauth.utils.RedisUtils;
import com.shiep.fxauth.vo.UserInfoVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/25 14:22
 * @description: 用户主页控制器
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @SuppressWarnings("all")
    @Autowired
    private IBankCardService bankCardService;
    @SuppressWarnings("all")
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IApiService apiService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IMailService mailService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public ModelAndView toHomePage() {
        logger.info("toHomePage:" + SecurityContextHolder.getContext().getAuthentication().getName());
        ModelAndView mv = new ModelAndView();
        mv.addObject("fxRate", apiService.getFxRate());
        mv.addObject("rmbRate", apiService.getRmbRate());
        mv.addObject("headlines", apiService.getHeadlinesPageable("guoji", 3));
        mv.setViewName("homePage");
        return mv;
    }

    @GetMapping("/bankCard/create")
    public String toCreateBankCardPage(Model model) {
        UserInfoVo userInfoVo = new UserInfoVo();
        model.addAttribute("userInfo", userInfoVo);
        return "createBankcard";
    }

    @GetMapping("/bankcard/active")
    public ModelAndView activeBankCard() {
        ModelAndView mv = new ModelAndView();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String bankcardID = (String) RedisUtils.hGet("bankcardID", username);
        String email = (String) RedisUtils.get(username);
        // 如果bankcardID和email为空，表示当前用户还未进行身份认证
        if (StringUtils.isEmpty(bankcardID) || StringUtils.isEmpty(email)) {
            mv.addObject("error", 2);
        }
        mv.addObject("bankcardID", bankcardID);
        mv.addObject("email", email);
        mv.setViewName("activeBankcard");
        return mv;
    }

    @GetMapping("/bankcard/bind")
    public ModelAndView bindBankCard() {
        ModelAndView mv = new ModelAndView();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String bankcardID = (String) RedisUtils.hGet("bankcardID", username);
        String email = (String) RedisUtils.get(username);
        // 如果bankcardID和email为空，表示当前用户还未进行身份认证
        if (StringUtils.isEmpty(bankcardID) || StringUtils.isEmpty(email)) {
            mv.addObject("error", 1);
            mv.setViewName("bindBankcard");
            return mv;
        }
        FxBankCard bankCard = bankCardService.findByBankCardId(bankcardID);
        // 如果银行卡状态为未激活
        if (bankCard.getStatus().equals(FxBankCard.INACTIVE)) {
            mv.addObject("error", 2);
            mv.setViewName("bindBankcard");
            return mv;
        }
        mv.setViewName("bindBankcard");
        return mv;
    }

    @GetMapping("/myBankCard")
    public ModelAndView toMyBankCardPage() {
        ModelAndView mv = new ModelAndView();
        String accountName = SecurityContextHolder.getContext().getAuthentication().getName();
        String bankcardID = (String) RedisUtils.hGet("bankcardID", accountName);
        // 如果银行卡号码为空，表示还未办理银行卡
        if (StringUtils.isEmpty(bankcardID)) {
            mv.addObject("status", 1);
            mv.setViewName("myBankcard");
            return mv;
        }
        FxBankCard bankCard = bankCardService.findByBankCardId(bankcardID);
        // 如果银行卡未激活
        if (bankCard.getStatus().equals(FxBankCard.INACTIVE)) {
            mv.addObject("status", 2);
            mv.setViewName("myBankcard");
            return mv;
        }
        // 如果银行卡未绑定到账户
        if (StringUtils.isEmpty(accountService.getAccountVo(accountName).getBankcardId())) {
            mv.addObject("status", 3);
            mv.setViewName("myBankcard");
            return mv;
        }
        mv.addObject("status", 4);
        mv.addObject("bankcard", bankCard);
        mv.setViewName("myBankcard");
        return mv;
    }

    @GetMapping("/showUserInfo")
    @ResponseBody
    public FxUser getUserInfo() {
        String accountName = SecurityContextHolder.getContext().getAuthentication().getName();
        String email = (String) RedisUtils.get(accountName);
        return userInfoService.getByEmail(email);
    }

    @PostMapping("/bankcard/updatePwd/{bankcardID}")
    @ResponseBody
    public Boolean updateBankCardPassword(@PathVariable("bankcardID") String bankcardID, @RequestParam String oldPassword,
                                          @RequestParam String newPassword, @RequestParam String status) {
        String unActive = "0";
        if (bankCardService.updatePassword(bankcardID, oldPassword, newPassword) != null) {
            // 如果银行卡未激活，修改密码后将激活
            if (unActive.equals(status)) {
                bankCardService.activeBankCard(bankcardID);
            }
            return true;
        }
        return false;
    }

    @PostMapping("/userInfo/check")
    public ModelAndView checkUserInfo(@Validated UserInfoVo userInfo, BindingResult errors, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // 校验失败返回错误信息
        if (errors.hasFieldErrors()) {
            mv.addObject("error", "true");
            for (FieldError error : errors.getFieldErrors()) {
                mv.addObject("err_" + error.getField(), error.getDefaultMessage());
            }
            mv.addObject("userInfo", userInfo);
            mv.setViewName("createBankcard");
            return mv;
        }
        Map<String, Object> result = apiService.verify(userInfo.getIdNumber());
        // 请求身份证号码认证识别时，返回失败信息
        if (!result.get("code").equals(200)) {
            mv.addObject("userInfo", userInfo);
            mv.addObject("code", result.get("code"));
            mv.addObject("message", result.get("message"));
            mv.setViewName("createBankcard");
            return mv;
        }
        Map<String, String> data = (Map<String, String>) result.get("data");
        logger.info(data.toString());
        // 通过比较出生地和性别判断身份证号码是否是本人
        // 实际上应该通过实名认证（比较姓名和身份证号码），但是这个api收费太高了……所以采用这种方式模拟(┬＿┬)
        if (userInfo.getGender().equals(data.get("sex")) && userInfo.getProvince().equals(data.get("prov"))
                && userInfo.getCity().equals(data.get("city")) && userInfo.getCountry().equals(data.get("region"))) {
            // 个人信息认证成功后，将用户信息写入数据库
            FxUser user = userInfoService.createFxUser(new FxUser(userInfo));
            logger.info("create user info:" + user);
            String ip = IpUtils.getIpAddress(request);
            FxBankCard bankCard = bankCardService.createInitBankCard(ip, user.getId());
            logger.info("create init bankcard:" + bankCard);
            Map<String, Object> value = new HashMap<>(2);
            value.put("name", userInfo.getChineseName());
            value.put("password", bankCard.getPassword());
            // 发送初始密码到用户邮箱
            mailService.sendThymeleafMail(userInfo.getEmail(), "FX-System：激活银行卡", "mailActive", value);
            // 将银行卡号码存入redis
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            RedisUtils.hPut("bankcardID", username, bankCard.getId());
            RedisUtils.set(username, userInfo.getEmail());
            // 跳转到激活银行卡界面
            mv.addObject("bankcardID", bankCard.getId());
            mv.addObject("email", userInfo.getEmail());
            mv.setViewName("activeBankcard");
            return mv;
        }
        // 个人信息认证失败，返回错误信息
        mv.addObject("userInfo", userInfo);
        mv.addObject("code", 102);
        mv.addObject("message", "错误的个人信息，校验失败，请检查信息是否正确。");
        mv.setViewName("createBankcard");
        return mv;
    }

    @PostMapping("/bankcard/active")
    public ModelAndView activeBankCard(@RequestParam("email") String email,
                                       @RequestParam("bankcardID") String bankcardID,
                                       @RequestParam("initPassword") String initPassword,
                                       @RequestParam("password") String newPassword) {
        ModelAndView mv = new ModelAndView();
        FxBankCard bankCard = bankCardService.findByBankCardId(bankcardID);
        // 如果用户初始密码匹配
        if (passwordEncoder.matches(initPassword, bankCard.getPassword())) {
            // 激活银行卡
            bankCardService.activeBankCard(bankcardID);
            // 修改密码
            bankCardService.updatePassword(bankcardID, initPassword, newPassword);
            // 当激活银行卡后，为用户授予“USER”角色==》可进行银行卡业务操作
            String accountName = SecurityContextHolder.getContext().getAuthentication().getName();
            accountService.authorization(accountName, "ROLE_USER");
            mv.setViewName("bindBankcard");
            return mv;
        }
        // 如果用户输入错误的初始密码，将重置初始密码，并发送到用户邮箱
        String password = bankCardService.resetInitPassword(bankcardID);
        Map<String, Object> value = new HashMap<>(2);
        value.put("password", password);
        mailService.sendThymeleafMail(email, "FX-System：激活银行卡", "mailResetInitPwd", value);
        mv.addObject("error", 1);
        mv.addObject("bankcardID", bankCard.getId());
        mv.addObject("email", email);
        mv.setViewName("activeBankcard");
        return mv;
    }

    @PostMapping("/bankcard/bind")
    public ModelAndView bindBankCard(String bankcardID, String reservedMail) {
        ModelAndView mv = new ModelAndView();
        FxUser user = userInfoService.getByEmail(reservedMail);
        if (user != null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            // 将银行卡绑定到账户==>先判断该银行卡是否存在
            if ((bankCardService.findByBankCardId(bankcardID) != null) &&
                    (accountService.bindBankCard(username, bankcardID))) {
                mv.setViewName("redirect:/home");
                return mv;
            }
        }
        mv.addObject("error", 3);
        mv.setViewName("bindBankcard");
        return mv;
    }
}
