package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodeTest {
    @Test
    public void testPasswordEncode() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "gameover0310";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
        boolean checkMatch =encoder.matches(rawPassword, encodedPassword);
        System.out.println(checkMatch);
    }
}

