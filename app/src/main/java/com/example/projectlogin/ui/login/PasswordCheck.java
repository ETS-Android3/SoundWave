package com.example.projectlogin.ui.login;

public class PasswordCheck {
    public static boolean password(String password){

        final int PASSWORD_LENGTH = 8;
        int charCount = 0;
        int numCount = 0;

        if (password.length() < PASSWORD_LENGTH) return false;

        for (int i = 0; i < password.length(); i++){
            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }

        return (charCount >=2 && numCount >=1);

    }

    public  static boolean is_Numeric(char ch){
        return (ch >= '0' && ch <= '9');
    }

    public  static boolean is_Letter(char ch){
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }
}
