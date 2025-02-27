package cn.ivfzhou.reserve_platform.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import cn.ivfzhou.reserve_platform.entity.db.User;

@Slf4j
public class JwtUtil {

    /**
     * 密钥。
     */
    private static final String secret = "21laksdfiua42131giuwgelkwqjrlkjhadklsf21laksdfiua42131giuwgelkwqjrlkjhadklsf21laksdfiua42131giuwgelkwqjrlkjhadklsf21laksdfiua42131giuwgelkwqjrlkjhadklsf21laksdfiua42131giuwgelkwqjrlkjhadklsf21laksdfiua42131giuwgelkwqjrlkjhadklsf21laksdfiua42131giuwgelkwqjrlkjhadklsf21laksdfiua42131giuwgelkwqjrlkjhadklsf";

    private static final Integer timeout = 1000 * 60 * 60 * 24 * 7;

    /**
     * 生成JWT的令牌。
     */
    public static String createJwtToken(User user, String devId) {
        return Jwts.builder()
                .setId(user.getId() + "")
                .setSubject(user.getNickname())
                .setIssuedAt(new Date())
                // 添加自定义属性。
                .claim("uid", user.getId())
                .claim("username", user.getUsername())
                // 设置jwt的过期时间。
                .setExpiration(new Date(new Date().getTime() + timeout))
                .signWith(SignatureAlgorithm.HS256, secret + devId)
                .compact();
    }

    /**
     * 验签token的方法。
     */
    public static User isToken(String jwtToken, String devId) {
        try {
            Claims claim = Jwts.parser()
                    .setSigningKey(secret + devId)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            log.info("JWT验签通过 devId: {} jwtToken: {}", devId, jwtToken);
            // 解析出用户的自定义信息。
            Integer uid = (Integer) claim.get("uid");
            String username = (String) claim.get("username");
            return new User().setId(uid).setUsername(username);
        } catch (Exception e) {
            log.warn("验签未通过 devId: {} jwtToken: {}, exception: {}", devId, jwtToken, e);
        }

        return null;
    }

}
