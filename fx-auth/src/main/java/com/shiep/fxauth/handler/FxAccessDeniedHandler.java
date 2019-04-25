package com.shiep.fxauth.handler;

import com.shiep.fxauth.common.HttpStatusEnum;
import com.shiep.fxauth.vo.ResultVo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author: 倪明辉
 * @date: 2019/3/7 17:38
 * @description: 用户无权访问时返回给前端的数据
 */
public class FxAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        Map<String, Object> map = ResultVo.result(HttpStatusEnum.USER_NO_ACCESS, false);
        request.setAttribute("code",map.get("code"));
        request.setAttribute("msg",map.get("messageCN"));
        request.getRequestDispatcher("/error").forward(request,response);
    }
}
