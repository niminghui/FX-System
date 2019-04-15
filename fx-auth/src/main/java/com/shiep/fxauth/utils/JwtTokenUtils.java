package com.shiep.fxauth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author: 倪明辉
 * @date: 2019/3/21 13:48
 * @description: JWT Token 工具类
 */
public class JwtTokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    /**
     * description: request header 请求头
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * description: token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * description: 密钥，用于signature（签名）部分解密
     */
    private static final String PRIMARY_KEY = "foreign exchange";

    /**
     * description: 签发者
     */
    private static final String ISS = "Gent.Ni";

    /**
     * description: 添加角色的key
     */
    private static final String ROLE_CLAIMS = "role";

    /**
     * description: 过期时间是3600秒，既是1个小时(默认)
     */
    private static final long EXPIRATION = 3600L;

    /**
     * description: 选择了记住我之后的过期时间为7天
     */
    private static final long EXPIRATION_REMEMBER = 604800L;

    /**
     * description: 创建Token
     *
     * @param username 用户名
     * @param isRememberMe 是否记住我
     * @return java.lang.String
     */
    public static String createToken(String username, List<String> roles, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>(16);
        map.put(ROLE_CLAIMS, roles);
        return Jwts.builder()
                //采用HS512算法对JWT进行的签名,PRIMARY_KEY是我们的密钥
                .signWith(SignatureAlgorithm.HS512, PRIMARY_KEY)
                //设置角色名
                .setClaims(map)
                //设置发证人
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * description: 从token中获取用户名
     *
     * @param token JWT token
     * @return java.lang.String
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * description: 获取用户角色信息
     *
     * @param token JWT token
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getUserRole(String token){
        return (List<String>) getTokenBody(token).get(ROLE_CLAIMS);
    }

    /**
     * description: 判断Token是否过期
     *
     * @param token JWT token
     * @return boolean
     */
    public static boolean isExpiration(String token){
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            logger.warn("[" + token + "]" + "is expired.");
            return true;
        }
    }

    /**
     * description:　获取Token body
     *
     * @param token JWT token
     * @return io.jsonwebtoken.Claims
     */
    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(PRIMARY_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
