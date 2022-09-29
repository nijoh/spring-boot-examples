package com.nijo.example.mq;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileTest {
    public static void main(String[] args) throws IOException, ParseException {
        File file =new File("/Users/h/Desktop/1.txt");
        FileWriter writer = new FileWriter(file);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://cache.video.iqiyi.com/dash?tvid=2037200441718700&bid=200&vid=86bde430cd57bbb2766e2d133cefdfc5&src=01080031010000000000&vt=0&rs=1&uid=&ori=pcw&ps=1&k_uid=edf993fdf22e66d78d4e82900aed54bf&pt=0&d=0&s=&lid=&cf=&ct=&authKey=9ac39af5ef522b9802d20bceff35f017&k_tag=1&dfp=a075cda21ad62e4efab1b8a49c56bf4170e3f6fe7b2288b887023ecd067e12f1eb&locale=zh_cn&prio=%7B%22ff%22%3A%22f4v%22%2C%22code%22%3A2%7D&pck=&k_err_retries=0&up=&sr=1&qd_v=2&tm=1663770458323&qdy=a&qds=0&k_ft1=706436220846084&k_ft4=1161084347621380&k_ft5=1&bop=%7B%22version%22%3A%2210.0%22%2C%22dfp%22%3A%22a075cda21ad62e4efab1b8a49c56bf4170e3f6fe7b2288b887023ecd067e12f1eb%22%7D&ut=0&vf=38dc79f068bb749ba0b60cadb62ace4e";
        HttpGet httpGet = new HttpGet(url);
// 调用 HttpClient 的 execute 方法执行请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
// 获取请求状态
        int code = response.getCode();
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        JSONObject jsonObject = JSON.parseObject(result).getJSONObject("data");
        JSONObject program = jsonObject.getJSONObject("program");
        JSONArray video = program.getJSONArray("video");
        video.stream().forEach(tmp->{
            if(((JSONObject) tmp).containsKey("m3u8")) {
                String m3u8 = ((JSONObject) tmp).getString("m3u8");
                System.out.println(m3u8);
                try {
                    writer.write(m3u8);
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

        });


    }
}
