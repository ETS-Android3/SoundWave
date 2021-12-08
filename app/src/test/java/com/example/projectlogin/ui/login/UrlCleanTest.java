package com.example.projectlogin.ui.login;

import static org.junit.Assert.*;

import org.junit.Test;

public class UrlCleanTest {
    @Test
    public void testUrlClean(){
        String actual = UrlClean.url("https://lh3.googleusercontent.com/jFaQSwiqG6yIWfOuscZTJsDZSe_mNsHCjHcoXU9MH9hZogX953RTOK8KeilxStWjYti9LZpl__ymOft4=w60-h60-l90-rj");
        String expected = "https://lh3.googleusercontent.com/jFaQSwiqG6yIWfOuscZTJsDZSe_mNsHCjHcoXU9MH9hZogX953RTOK8KeilxStWjYti9LZpl__ymOft4";
        assertEquals(actual,expected);
    }
}