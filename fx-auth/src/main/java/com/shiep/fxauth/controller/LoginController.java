package com.shiep.fxauth.controller;

import com.shiep.fxauth.endpoint.IAccountService;
import com.shiep.fxauth.model.LoginVO;
import com.shiep.fxauth.model.RegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 14:01
 * @description: 登录控制层
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @SuppressWarnings("all")
    @Autowired
    private IAccountService accountService;

    @GetMapping
    public String toLoginPage(Model model){
        LoginVO loginVO=new LoginVO();
        model.addAttribute("loginVO",loginVO);
        return "loginPage";
    }

    @PostMapping("/validate")
    public ModelAndView loginValidate(HttpServletRequest request, @Validated LoginVO loginUser,
                                 Errors errors, RedirectAttributes ra){
        ModelAndView mv=new ModelAndView();
        // 如果Errors对象有Field错误的时候，重新跳回注册页面，否则正常提交
        if (errors.hasFieldErrors()){
            mv.setViewName("loginPage");
            return mv;
        }
        String captcha = request.getSession().getAttribute("captcha").toString();
        if (!captcha.equals(loginUser.getUserCaptcha())) {
            mv.setViewName("loginPage");
            return mv;
        }
        // ra.addFlashAttribute()传递参数到过滤器中丢失
        ra.addAttribute("name",loginUser.getAccountName());
        ra.addAttribute("password",loginUser.getPassword());
        ra.addAttribute("rememberMe",loginUser.getRememberMe());
        mv.setViewName("redirect:/login/auth");
        return mv;
    }

    @GetMapping("/success")
    public String toLoginSuccessPage(@RequestParam("token") String token, HttpServletResponse response) {
        response.setHeader("token", token);
        return "loginSuccessPage";
    }

    @GetMapping("/captcha")
    public void returnCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 在内存中创建图像
        int width = 100, height = 52;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 创建随机数对象
        Random random = new Random();
        // 设定背景色
        g.setColor(new Color(255, 245, 247));
        g.fillRect(0, 0, width, height);
        // 设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        // 随机产生干扰线，使图像中的认证码不易被其他程序探测到
        for (int i = 0; i < 30; i++) {
            // x坐标开始
            int xs = random.nextInt(width);
            // y坐标开始
            int ys = random.nextInt(height);
            // x坐标结束
            int xe = xs + random.nextInt(width / 8);
            // y坐标结束
            int ye = ys + random.nextInt(height / 8);
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }
        // 取随机产生的认证码（4位数字）
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图像中
            g.setColor(new Color(30 + random.nextInt(160), 40 + random.nextInt(170), 40 + random.nextInt(180)));
            g.drawString(rand, 20 * i + 15, 34);
        }
        // 将认证码存入session
        request.getSession().setAttribute("captcha", sRand);
        g.dispose();
        // 输出图像到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }
}
