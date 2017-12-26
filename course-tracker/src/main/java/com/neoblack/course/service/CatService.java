package com.neoblack.course.service;

import com.neoblack.course.common.ConnectionUtils;
import com.neoblack.course.mapper.CatMapper;
import com.neoblack.course.pojo.Cat;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Neo on 2017/5/17.
 */
@Service
public class CatService {
    @Resource
    private CatMapper catMapper;
    public void saveAllCat(){
        for (Cat cat : CatJsoup.fetchAllCat()) {
            catMapper.saveCat(cat);
        }
    }

    static class CatJsoup{
        static List<Cat> fetchAllCat() {
            Connection connection = Jsoup.connect("http://tts8.tmooc.cn/studentCenter/toMyttsPage?isCenter=yes");

            ConnectionUtils.init(connection);

            Map cookies = new HashMap();
            //Hm_lvt_9712e8cf4f76a1de06d6580a95348b4f=1494311892,1494382310,1495014574,1495196636;
            //Hm_lpvt_9712e8cf4f76a1de06d6580a95348b4f=1495196654;
            //sessionid=374ca5d937464736a9820c62b8b170c7%7C964985821%40qq.com
//            cookies.put("Hm_lvt_9712e8cf4f76a1de06d6580a95348b4f", "1494311892,1494382310,1495014574,1495196636");
//            cookies.put("Hm_lpvt_9712e8cf4f76a1de06d6580a95348b4f", "1495196654");
            cookies.put("sessionid", "374ca5d937464736a9820c62b8b170c7%7C964985821%40qq.com");
            connection.cookies(cookies);

            connection.referrer("http://tmooc.cn/web/index_new.html?tedu");

            Document document = null;
            try {
                document = connection.get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements elements = document.select("h3");
            elements = elements.select(".t").select(".t1");


            List<Cat> cats = new ArrayList<>();
            for (Element element : elements) {
                Cat cat = new Cat();
                cat.setcCatname(element.text());
                cats.add(cat);
            }

            return cats;
        }

    }

}
