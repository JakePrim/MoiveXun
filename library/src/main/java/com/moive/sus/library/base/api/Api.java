package com.moive.sus.library.base.api;


import com.moive.sus.library.BuildConfig;

/**
 * Created by linksus on 4/12 0012.
 * <p>
 * Api 文档:https://developers.themoviedb.org/3/movies
 * 需要申请API_KEY
 *
 * 暂时放弃 先不用 因是国外ＡＰＩ
 * */

public class Api {
//    private static final String TMDB_API_KEY = BuildConfig.TMDB_API_KEY;
    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w342";
    public static final String BACKDROP_PATH = "http://image.tmdb.org/t/p/w780";
    public static final String GET_POPULAR_MOVIES = "http://api.themoviedb.org/3/discover/movie?language=zh&sort_by=popularity.desc&api_key=";// 受欢迎的程度
    public static final String GET_HIGHEST_RATED_MOVIES = "http://api.themoviedb.org/3/discover/movie?vote_count.gte=500&language=zh&sort_by=vote_average.desc&api_key=";
    static final String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%1$s";
    static final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%1$s/0.jpg";
    //https://api.themoviedb.org/3/movie/{movie_id}/lists?api_key=<<api_key>>&language=en-US&page=1 获取电影列表
    //https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US //最新的
    //https://api.themoviedb.org/3/movie/now_playing?api_key=<<api_key>>&language=en-US&page=1 //正在上映的
    //https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1 //流行的
    //https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1 //评分排行榜
    //https://api.themoviedb.org/3/movie/upcoming?api_key=<<api_key>>&language=en-US&page=1 //预告
    //https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key=<<api_key>>&language=en-US&page=1 //评论
    //https://api.themoviedb.org/3/review/{review_id}?api_key=<<api_key>> // 评论详情界面
    //https://api.themoviedb.org/3/movie/{movie_id}/similar?api_key=<<api_key>>&language=en-US&page=1 //类似
    //https://api.themoviedb.org/3/movie/{movie_id}/recommendations?api_key=<<api_key>>&language=en-US&page=1 //建议
    //https://api.themoviedb.org/3/movie/{movie_id}/translations?api_key=<<api_key>> //翻译
    //https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=<<api_key>>&language=en-US //视频
    //https://api.themoviedb.org/3/movie/{movie_id}/release_dates?api_key=<<api_key>> //发布时间
    //https://api.themoviedb.org/3/movie/{movie_id}?api_key=<<api_key>>&language=en-US //电影的详情页
    //https://api.themoviedb.org/3/movie/{movie_id}/images?api_key=<<api_key>>&language=en-US //图片
    //https://api.themoviedb.org/3/movie/{movie_id}/keywords?api_key=<<api_key>> //关键词
    //https://api.themoviedb.org/3/movie/{movie_id}/changes?api_key=<<api_key>>&page=1
    //https://api.themoviedb.org/3/keyword/{keyword_id}?api_key=<<api_key>>
    //https://api.themoviedb.org/3/keyword/{keyword_id}/movies?api_key=<<api_key>>&language=en-US&include_adult=false
    //https://api.themoviedb.org/3/genre/movie/list?api_key=<<api_key>>&language=en-US 电影类型列表
    //https://api.themoviedb.org/3/genre/{genre_id}/movies?api_key=<<api_key>>&language=en-US&include_adult=false&sort_by=created_at.asc  获取具体某个电影的类型

    private Api()
    {
        // hide implicit public constructor
    }
}
