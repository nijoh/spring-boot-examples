package com.nijo.example.mq.elasticsearch;

import com.fasterxml.jackson.core.JsonParser;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 索引相关操作
 * */

public class Index {
    private static Logger log = LoggerFactory.getLogger(Index.class);
    /**
     * 创建索引
     * */
    public static void createIndex() {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            CreateIndexRequest request=new CreateIndexRequest("users");
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            //返回状态
            boolean ack = response.isAcknowledged();
            if(ack){
                log.info("创建索引成功");
            }else {
                log.info("创建索引失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭客户端
            EsUtils.closeES(client);
        }
    }

    /**
     * 查询某个索引
     * */
    public static void findIndexByName(String indexName){
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            GetIndexRequest request=new GetIndexRequest(indexName);
            GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
            //响应信息
            //当前索引文档映射格式
            Map<String, MappingMetadata> mappings = response.getMappings();
            System.out.println(mappings);
            Map<String, Settings> settings = response.getSettings();
            System.out.println(settings);
            Map<String, List<AliasMetadata>> aliases = response.getAliases();
            System.out.println(aliases);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    /**
     * 删除某个索引
     * */
    public static void deleteIndexByName(String indexName){
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            DeleteIndexRequest request=new DeleteIndexRequest(indexName);
            AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
            //响应信息
            //返回状态
            boolean ack = response.isAcknowledged();
            if(ack){
                log.info("删除索引成功");
            }else {
                log.info("删除索引失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }
    public static void main(String[] args) {
        Index.createIndex();
        //Index.findIndexByName("shopping");
        //Index.deleteIndexByName("users");
    }
}
