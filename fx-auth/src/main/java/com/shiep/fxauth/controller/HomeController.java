package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.IApiService;
import com.shiep.fxauth.vo.UserInfoVo;
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

import java.util.Map;


/**
 * @author: 倪明辉
 * @date: 2019/3/25 14:22
 * @description: 用户主页控制器
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @SuppressWarnings("all")
    @Autowired
    private IApiService apiService;

    @GetMapping
    public ModelAndView toHomePage() {
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
        model.addAttribute("step", "1");
        return "createBankcard";
    }

    @PostMapping("/userInfo/check")
    public ModelAndView checkUserInfo(@Validated UserInfoVo userInfo, BindingResult errors) {
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
        Map<String, Object> result = apiService.verify(userInfo.getIdNumber());
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
            // 个人信息认证成功后，将生成银行卡号，与默认密码
            // 发送密码到用户邮箱，用户在界面输入初始密码，然后修改密码完成银行卡激活
            mv.addObject("step", "2");
            mv.setViewName("createBankcard");
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
