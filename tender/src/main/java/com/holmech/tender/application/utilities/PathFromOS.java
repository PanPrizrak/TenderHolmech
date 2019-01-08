package com.holmech.tender.application.utilities;

public final class PathFromOS {
    public static String getPath(){
        if(OsUtils.isWindows()){
            return new String("\\");
        } else {
            return new String("/");
        }
    }
}
