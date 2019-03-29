package com.shiep.fxauth.handler;

import com.alibaba.fastjson.JSON;
import com.shiep.fxauth.common.HttpStatusEnum;
import com.shiep.fxauth.common.ResultVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/8 9:44
 * @description: 用户登出成功时返回给前端的数据
 */
public class FxLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> map = ResultVO.result(HttpStatusEnum.USER_LOGOUT_SUCCESS,false);
        request.setAttribute("code",map.get("code"));
        request.setAttribute("msg",map.get("messageCN"));
        request.getRequestDispatcher("/error").forward(request,response);
    }

}