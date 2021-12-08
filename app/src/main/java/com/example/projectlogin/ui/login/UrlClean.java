package com.example.projectlogin.ui.login;

public class UrlClean {
    public static String url(String link) {
        String replacedLink = link.replaceAll("(=).*","");
        return replacedLink;
    }
}
