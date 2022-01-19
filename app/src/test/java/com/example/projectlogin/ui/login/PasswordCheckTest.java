package com.example.projectlogin.ui.login;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordCheckTest {

    @Test
    public void password() {
        String password = "123password";
        Boolean expected = true;
        Boolean actual = PasswordCheck.password(password);
        assertEquals(actual,expected);
    }

    @Test
    public void is_Numeric() {
        char ch = '1';
        Boolean expected = true;
        Boolean actual = PasswordCheck.is_Numeric(ch);
        assertEquals(actual,expected);
    }

    @Test
    public void is_Letter() {
        char ch = 'a';
        Boolean expected = true;
        Boolean actual = PasswordCheck.is_Letter(ch);
        assertEquals(actual,expected);
    }
}