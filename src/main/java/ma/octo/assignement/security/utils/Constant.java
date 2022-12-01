package ma.octo.assignement.security.utils;

public class Constant {

    public static final String KEY = "assignement_2023_OCTO";
    public static final String BEARER = "Bearer ";
    public static final Long EXPIRE_TIME_ACCESS_TOKEN = 24*60*60* 1000L; // 1 jour
    public static final Long EXPIRE_TIME_REFRESH_TOKEN = 365*24*60*60* 1000L; // 1 ans

}
