package com.shiep.fxauth.handler;

import com.shiep.fxauth.common.HttpStatusEnum;
import com.shiep.fxauth.vo.ResultVo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/7 15:18
 * @description: 用户未登录时返回给前端的数据
 */
public class UnAuthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        Map<String, Object> map = ResultVo.result(HttpStatusEnum.USER_NEED_AUTHORITIES, false);
        request.setAttribute("code",map.get("code"));
        request.setAttribute("msg",map.get("messageCN"));
        request.getRequestDispatcher("/error").forward(request,response);
    }
}
