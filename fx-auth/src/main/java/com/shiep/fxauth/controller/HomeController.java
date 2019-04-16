package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.IBankCardService;
import com.shiep.fxauth.model.FxBankCard;
import com.shiep.fxauth.model.FxUser;
import com.shiep.fxauth.service.IMailService;
import com.shiep.fxauth.utils.IpUtils;
import com.shiep.fxauth.vo.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private IMailService mailService;

    @GetMapping
    public ModelAndView toHomePage() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("fxRate", bankCardService.getFxRate());
        mv.addObject("rmbRate", bankCardService.getRmbRate());
        mv.addObject("headlines", bankCardService.getHeadlinesPageable("guoji", 3));
        mv.setViewName("homePage");
        return mv;
    }

    @GetMapping("/bankCard/create")
    public String toCreateBankCardPage(Model model) {
        UserInfoVo userInfoVo = new UserInfoVo();
        model.addAttribute("userInfo", userInfoVo);
        model.addAttribute("step", "1");
        return "createBankcard";
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
            mv.addObject("step", "1");
            mv.setViewName("createBankcard");
            return mv;
        }
        Map<String, Object> result = bankCardService.verify(userInfo.getIdNumber());
        // 请求身份证号码认证识别时，返回失败信息
        if (!result.get("code").equals(200)) {
            mv.addObject("step", "1");
            mv.addObject("userInfo", userInfo);
            mv.addObject("code", result.get("code"));
            mv.addObject("message", result.get("message"));
            mv.setViewName("createBankcard");
            return mv;
        }
        Map<String, String> data = (Map<String, String>) result.get("data");
        // 通过比较出生地和性别判断身份证号码是否是本人
        // 实际上应该通过实名认证（比较姓名和身份证号码），但是这个api收费太高了……所以采用这种方式模拟(┬＿┬)
        if (userInfo.getGender().equals(data.get("sex")) && userInfo.getProvince().equals(data.get("prov"))
                && userInfo.getCity().equals(data.get("city")) && userInfo.getCountry().equals(data.get("region"))) {
            // 个人信息认证成功后，将用户信息写入数据库
            FxUser user = bankCardService.createFxUser(new FxUser(userInfo));
            logger.info("create user info:" + user);
            String ip = IpUtils.getIpAddress(request);
            FxBankCard bankCard = bankCardService.createInitBankCard(ip, user.getId());
            logger.info("create init bankcard:" + bankCard);
            Map<String, Object> value = new HashMap<>(2);
            value.put("name", userInfo.getChineseName());
            value.put("password", bankCard.getPassword());
            // 发送初始密码到用户邮箱
            mailService.sendThymeleafMail(userInfo.getEmail(), "FX-System：激活银行卡", "mailActive", value);
            // 用户在界面输入初始密码，然后修改密码完成银行卡激活
            mv.addObject("step", "2");
            mv.setViewName("createBankcard");
            return mv;
        }
        // 个人信息认证失败，返回错误信息
        mv.addObject("step", "1");
        mv.addObject("userInfo", userInfo);
        mv.addObject("code", 102);
        mv.addObject("message", "错误的个人信息，校验失败，请检查信息是否正确。");
        mv.setViewName("createBankcard");
        return mv;
    }
}
