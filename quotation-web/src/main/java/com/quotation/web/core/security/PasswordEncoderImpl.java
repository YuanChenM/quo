package com.quotation.web.core.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by yang_shoulai on 8/11/2017.
 */
public class PasswordEncoderImpl implements PasswordEncoder {

    private final static PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence charSequence) {
        return encoder.encode(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encoder.matches(charSequence, s);
    }
}
