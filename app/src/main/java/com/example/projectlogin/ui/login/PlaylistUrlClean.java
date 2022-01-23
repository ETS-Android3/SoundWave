package com.example.projectlogin.ui.login;

public class PlaylistUrlClean {
    public static String url(String link) {
        String finalLink = link.replace("https://music.youtube.com/browse/","");
//        String[] replacedLink= link.split("(https:\\/\\/music\\.youtube\\.com\\/browse\\/).*");
//        String finalLink = TextUtils.join("",replacedLink);
        //String replacedLink = link.replaceAll("(https:\\/\\/music\\.youtube\\.com\\/browse\\/).*", "");
        return finalLink;
    }
}
