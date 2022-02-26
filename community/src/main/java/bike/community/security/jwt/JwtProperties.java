package bike.community.security.jwt;

public interface JwtProperties {
    public static String SECRET = "THIS_IS_OUR_SECRET";
    // 1000 * 60 = 1분.
    // 1000*60L*60*24 = 1일.
    public static long ACCESS_EXPIRED_TIME = 1000*60L*30L; // 20분
    public static long REFRESH_EXPIRED_TIME = (1000*60L*60*24)*7L; // 7일
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "Bearer ";
    public static String ACCESS_NAME = "ACCESS_TOKEN";
    public static String REFRESH_NAME = "REFRESH_TOKEN";

    public static final String SPACE =" ";
    public static final String REDIS_RT ="-rt";
    public static final String REDIS_AT ="-at";
}
