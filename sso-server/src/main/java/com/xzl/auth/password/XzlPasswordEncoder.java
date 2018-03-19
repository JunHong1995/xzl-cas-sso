package com.xzl.auth.password;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ChenJunhong
 * @date 2018/03/19
 * Description:
 * 使用云梯的加密解密方法编写的密码encoder
 */
public class XzlPasswordEncoder implements PasswordEncoder{
    @Override
    public String encode(CharSequence rawPassword) {
        Encrypt encrypt = new Encrypt();
        return encrypt.encoder(rawPassword.toString());
    }
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        Encrypt encrypt = new Encrypt();
        String decodePassword = encrypt.decoder(encodedPassword);
        if (StringUtils.equals(decodePassword,rawPassword)){
            return true;
        }
        return false;
    }
}
