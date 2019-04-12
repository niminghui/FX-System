package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.IAccountService;
import com.shiep.fxauth.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 倪明辉
 * @date: 2019/4/9 16:47
 * @description: 注册控制层
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    @SuppressWarnings("all")
    @Autowired
    private IAccountService accountService;

    @RequestMapping
    public String toRegisterPage(Model model) {
        RegisterVo registerVo = new RegisterVo();
        model.addAttribute("registerVo", registerVo);
        return "registerPage";
    }

    @PostMapping("/validate")
    public ModelAndView registerValidate(HttpServletRequest request, RegisterVo registerVo) {
        System.out.println(registerVo);
        ModelAndView mv = new ModelAndView();
        String captcha = request.getSession().getAttribute("captcha").toString();
        if (!captcha.equals(registerVo.getUserCaptcha()) || !registerVo.getPassword().equals(registerVo.getConfirmPassword())) {
            mv.addObject("captchaError", "验证码错误");
            mv.setViewName("registerPage");
            return mv;
        }
        accountService.createAccount(registerVo.getAccountName(), registerVo.getPassword());
        mv.setViewName("redirect:/login");
        return mv;
    }

    @GetMapping("/check/{accountName}")
    @ResponseBody
    public Boolean checkAccountNameExist(@PathVariable("accountName") String accountName) {
        return accountService.getAccountVo(accountName) != null;
    }

}
