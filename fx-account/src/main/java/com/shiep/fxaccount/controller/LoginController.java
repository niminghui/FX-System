package com.shiep.fxaccount.controller;

import com.shiep.fxaccount.vo.LoginVO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author: 倪明辉
 * @date: 2019/3/18 9:43
 * @description: 登录Controller
 */
@Controller
public class LoginController {
    private Logger logger = Logger.getLogger(LoginController.class);

    @GetMapping("/login")
    public String toLoginPage(Model model){
        LoginVO loginVO=new LoginVO();
        model.addAttribute("loginVO",loginVO);
        return "loginPage";
    }

    @GetMapping("/test")
    public String toTest(){
        return "test";
    }

    @PostMapping("/login/validate")
    public String validate(HttpServletRequest request, @Validated LoginVO loginUser,
                           Errors errors){
        // 如果Errors对象有Field错误的时候，重新跳回注册页面，否则正常提交
        if (errors.hasFieldErrors())
            return "loginPage";
        return loginUser.toString();
//        String yzm = request.getSession().getAttribute("yzm").toString();
        // 数据校验错误，返回校验信息
//        if (bindingResult.hasErrors()) {
//            List<ObjectError> allErrors = bindingResult.getAllErrors();
//            for (ObjectError objectError : allErrors) {
//                logger.warn(objectError.getDefaultMessage());
//            }
//            request.getSession().setAttribute("errors", allErrors);
//            return "redirect:index.jsp";
//        }
//        // 验证码不匹配，写入message（应该自定义验证码校验，进行统一校验）
//        if (!yzm.equals(loginInfo.getUyzm())) {
//            request.getSession().setAttribute("message", CommonData.STRING_YZMERROR);
//            return "redirect:index.jsp";
//        }
//        // 登录认证
//        String info = accountService.validateAccount(loginInfo.getAccount_name(), loginInfo.getAccount_pwd());
//        if (info.equals(CommonData.STRING_SUCCESS)) {
//            String account_id = accountService.getAccountID(loginInfo.getAccount_name());
//            // 将该用户的AccountID和AccountName放入session
//            request.getSession().setAttribute("account_id", account_id);
//            request.getSession().setAttribute("account_name", loginInfo.getAccount_name());
//            // 将该用户的功能菜单放入session
//            // Menu功能未做好，待续
//            return "user";
//        } else {
//            request.getSession().setAttribute("message", info);
//            return "redirect:index.jsp";
//        }
    }

    @GetMapping("/yzm")
    public void returnYZM(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        request.getSession().setAttribute("yzm", sRand);
        g.dispose();
        // 输出图像到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
        request.getSession().setAttribute("yzm", sRand);
    }

//    @PostMapping("/login")
//    public String loginValidate(HttpServletRequest request, @Validated LoginInfo loginInfo,
//                                BindingResult bindingResult) {
//        String yzm = request.getSession().getAttribute("yzm").toString();
//        // 数据校验错误，返回校验信息
//        if (bindingResult.hasErrors()) {
//            List<ObjectError> allErrors = bindingResult.getAllErrors();
//            for (ObjectError objectError : allErrors) {
//                logger.warn(objectError.getDefaultMessage());
//            }
//            request.getSession().setAttribute("errors", allErrors);
//            return "redirect:index.jsp";
//        }
//        // 验证码不匹配，写入message（应该自定义验证码校验，进行统一校验）
//        if (!yzm.equals(loginInfo.getUyzm())) {
//            request.getSession().setAttribute("message", CommonData.STRING_YZMERROR);
//            return "redirect:index.jsp";
//        }
//        // 登录认证
//        String info = accountService.validateAccount(loginInfo.getAccount_name(), loginInfo.getAccount_pwd());
//        if (info.equals(CommonData.STRING_SUCCESS)) {
//            String account_id = accountService.getAccountID(loginInfo.getAccount_name());
//            // 将该用户的AccountID和AccountName放入session
//            request.getSession().setAttribute("account_id", account_id);
//            request.getSession().setAttribute("account_name", loginInfo.getAccount_name());
//            // 将该用户的功能菜单放入session
//            // Menu功能未做好，待续
//            return "user";
//        } else {
//            request.getSession().setAttribute("message", info);
//            return "redirect:index.jsp";
//        }
//    }
}
