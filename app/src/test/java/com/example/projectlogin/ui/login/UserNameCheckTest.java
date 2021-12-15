package com.example.projectlogin.ui.login;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserNameCheckTest {

    @Test
    public void username() {
        String username = "password123";
        Boolean expected = false;
        Boolean actual = UserNameCheck.username(username);
        assertEquals(expected,actual);
    }

    @Test
    public void username_fail(){
        String failUsername = "123";
        Boolean expectedFail = false;
        Boolean actualFail = UserNameCheck.username(failUsername);
        assertEquals(expectedFail,actualFail);
    }

    @Test
    public void is_Letter() {
        char ch = 'a';
        Boolean expected = true;
        Boolean actual = PasswordCheck.is_Letter(ch);
        assertEquals(expected,actual);
    }
}