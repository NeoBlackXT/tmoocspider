package com.neoblack.video.common;

import org.jsoup.Connection;

/**
 * Created by Neo on 2017/5/20.
 */
public class ConnectionUtils {
    private ConnectionUtils(){};

    public static void init(Connection connection){
        connection.header("Accept", "*/*");
        connection.header("Accept-Encoding", "gzip, deflate, sdch");
        connection.header("Accept-Language", "zh-CN,zh;q=0.8");
        connection.header("Cache-Control", "no-cache");
        connection.header("Connection", "keep-alive");
        connection.header("Host", "videotts.it211.com.cn");
        connection.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        connection.header("X-Requested-With", "ShockwaveFlash/25.0.0.171");
        connection.referrer("http://tts8.tmooc.cn/video/ttsVideo");
        connection.ignoreContentType(true);
    }
}
