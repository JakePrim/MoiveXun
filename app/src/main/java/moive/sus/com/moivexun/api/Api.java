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
    //https://api.themoviedb.org/3/movie/{movie_id}/lists?api_key=<<api_key>>&language=en-US&page=1 获取电影列表
    //https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US
    //https://api.themoviedb.org/3/movie/now_playing?api_key=<<api_key>>&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/upcoming?api_key=<<api_key>>&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key=<<api_key>>&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/{movie_id}/similar?api_key=<<api_key>>&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/{movie_id}/recommendations?api_key=<<api_key>>&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/{movie_id}/translations?api_key=<<api_key>>
    //https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=<<api_key>>&language=en-US
    //https://api.themoviedb.org/3/movie/{movie_id}/release_dates?api_key=<<api_key>>
    //https://api.themoviedb.org/3/movie/{movie_id}?api_key=<<api_key>>&language=en-US
    //https://api.themoviedb.org/3/movie/{movie_id}/images?api_key=<<api_key>>&language=en-US
    //https://api.themoviedb.org/3/movie/{movie_id}/keywords?api_key=<<api_key>>
    //https://api.themoviedb.org/3/movie/{movie_id}/credits?api_key=<<api_key>>
    //https://api.themoviedb.org/3/movie/{movie_id}/changes?api_key=<<api_key>>&page=1
    //https://api.themoviedb.org/3/keyword/{keyword_id}?api_key=<<api_key>>
    //https://api.themoviedb.org/3/keyword/{keyword_id}/movies?api_key=<<api_key>>&language=en-US&include_adult=false

    private Api()
    {
        // hide implicit public constructor
    }
}
