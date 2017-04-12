package moive.sus.com.moivexun.api;

import moive.sus.com.moivexun.BuildConfig;

/**
 * Created by linksus on 4/12 0012.
 * <p>
 * Api 文档:https://developers.themoviedb.org/3/movies
 * 需要申请API_KEY
 */

public class Api {
    private static final String TMDB_API_KEY = BuildConfig.TMDB_API_KEY;
    public static final String BASE_URL = BuildConfig.BASE_URL;
    private Api()
    {
        // hide implicit public constructor
    }
}
