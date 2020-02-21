package com.peng.blog.utils;

import java.util.UUID;

public class PhotoNameUtils {
    public static String GetId() {
        return UUID.randomUUID().toString();
    }
}
