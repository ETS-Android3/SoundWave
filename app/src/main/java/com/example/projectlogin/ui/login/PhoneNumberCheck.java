package com.example.projectlogin.ui.login;

import android.util.Patterns;

public class PhoneNumberCheck {
    public static boolean phoneNumber(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }
}
