package com.example.projectlogin.ui.login;

public class UserNameCheck {
    public static boolean username(String username){

        final int USERNAME_LENGTH = 8;
        int charCount = 0;

        if (username.length() < USERNAME_LENGTH) return false;

        for (int i = 0; i < username.length(); i++){
            char ch = username.charAt(i);

            if (is_Letter(ch)) charCount++;
            else return false;
        }

        return (charCount >=4 );

    }

    public  static boolean is_Letter(char ch){
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }
}
