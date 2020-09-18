package com.kakaobank.location.util;

public class Constants {
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_URL = "/auth/login";
    public static final String REFRESH_TOKEN_URL = "/auth/token";
    public static final String PASSWORD_INIT_URL = "/auth/init";
    public static final String API_ROOT = "/api";
    public static final String API_ROOT_URL = API_ROOT + "/**";
    public static final String APP_API_ROOT = API_ROOT + "/app";
    public static final String ADMIN_API_ROOT = API_ROOT + "/admin";
    public static final String COMMON_API_ROOT = API_ROOT + "/common";
    public static final String DATE_REGEX = "^([1-9][0-9][0-9][0-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
    public static  String IMAGE_PATTERN = "(.*/)*.+\\.(png|jpg|bmp|jpeg|PNG|JPG|BMP|JPEG)$";
    public static int veresionSeq = 1;
}
