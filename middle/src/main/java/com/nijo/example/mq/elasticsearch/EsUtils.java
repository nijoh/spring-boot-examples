package com.nijo.example.mq.elasticsearch;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsUtils {
    /**
     * 获取客户端连接
     * */
    public static  RestHighLevelClient createES(){
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
        return esClient;
    }

    /**
     * 关闭连接
     * */
    public static void closeES(RestHighLevelClient client){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
