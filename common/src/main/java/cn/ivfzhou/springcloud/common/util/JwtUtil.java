package cn.ivfzhou.springcloud.common.util;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.ivfzhou.springcloud.common.constant.Consts;
import cn.ivfzhou.springcloud.entity.db.User;

@Slf4j
@Component
public class JwtUtil {

    @Value("${data.jwt.secret}")
    private String secret;

    @Value("${data.jwt.timeout}")
    private Integer timeout;

    /**
     * 生成 JWT 的令牌。
     */
    public String createJwtToken(User user) {
        return Jwts.builder()
                .id(user.getId() + "")
                .subject(user.getNickname())
                .issuedAt(new Date())
                .claim(Consts.UID_KEY, user.getId())
                .claim(Consts.USERNAME_KEY, user.getUsername())
                .expiration(new Date(new Date().getTime() + timeout))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 验签 token 的方法。
     */
    public User validateToken(String token) {
        try {
            Jws<Claims> jws = Jwts
                    .parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token);
            if (!"HS256".equals(jws.getHeader().getAlgorithm())) {
                return null;
            }
            // 解析出用户的自定义信息。
            Claims claims = jws.getPayload();
            Integer uid = (Integer) claims.get(Consts.UID_KEY);
            String username = (String) claims.get(Consts.USERNAME_KEY);
            return new User().setId(uid).setUsername(username);
        } catch (Exception e) {
            log.warn("验签未通过 token: {}, exception: {}", token, e);
        }

        return null;
    }

}
