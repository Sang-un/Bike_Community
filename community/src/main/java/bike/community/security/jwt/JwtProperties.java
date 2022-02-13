package bike.community.security.jwt;

public interface JwtProperties {
    public static String SECRET = "secretkeyhohoho";
    public static String ISSUER = "LEETOKEN";
    public static long ACCESS_EXPIRED_TIME = (10*60*60*10*10)/60L;//1시간
    public static long REFRESH_EXPIRED_TIME = 10*60*60*10*10*24*14L;//2주
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "Bearer ";
    public static String ACCESS_NAME = "ACCESS_TOKEN";
    public static String REFRESH_NAME = "REFRESH_TOKEN";

    public static final String SPACE =" ";
    public static final String REDIS_RT ="-rt";
    public static final String REDIS_AT ="-at";
}
