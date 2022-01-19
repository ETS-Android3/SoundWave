package com.example.projectlogin.ui.login;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PhoneNumberCheckTest {
    @Test
    public void phoneNumber() {
        String phone = "9999999999";
        Boolean expected = false;
        Boolean actual = PhoneNumberCheck.phoneNumber(phone);
        assertEquals(expected,actual);
    }
}