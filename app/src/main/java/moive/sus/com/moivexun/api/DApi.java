package moive.sus.com.moivexun.api;

import moive.sus.com.moivexun.BuildConfig;

/**
 * Created by linksus on 4/17 0017.
 * 采用豆瓣API
 */

public class DApi {
    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String NOW_MOVIE_URL = "v2/movie/in_theaters";//正在上映
    public static final String COMING_SOON_URL = BASE_URL + "v2/movie/coming_soon";//即将上映
    public static final String SUBJECT_URL = BASE_URL + "v2/movie/subject/:id";//电影条目信息
    public static final String SUBJECT_PHOTOS_URL = BASE_URL + "v2/movie/subject/:id/photos";//电影条目剧照
    public static final String SUBJECT_COMMENTS_URL = BASE_URL + "v2/movie/subject/:id/comments";//电影条目短评
    public static final String SUBJECT_REVIEWS_URL = BASE_URL + "v2/movie/subject/:id/reviews";//电影条目长评
    public static final String CELEBBRITY_URL = BASE_URL + "v2/movie/celebrity/:id";//影人条目信息
    public static final String CELEBBRITY_PHOTOS_URL = BASE_URL + "v2/movie/celebrity/:id/photos";//影人剧照
    public static final String CELEBBRITY_WORKS_URL = BASE_URL + "v2/movie/celebrity/:id/works";//影人作品
    public static final String SERACH_URL = BASE_URL + "v2/movie/search?q={text}";//搜索/v2/movie/search?q=张艺谋 GET /v2/movie/search?tag=喜剧
    public static final String NEW_MOVIE_URL = BASE_URL + "v2/movie/new_movies";//新片榜
    public static final String WEEKLY_MOVIE_URL = BASE_URL + "v2/movie/weekly";//口碑榜
    public static final String BOX_MOVIE_URL = BASE_URL + "v2/movie/us_box";//票房榜
    public static final String TOP_MOVIE_URL = BASE_URL + "v2/movie/top250";//排名榜
}
