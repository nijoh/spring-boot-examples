package com.nijo.example.mq.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nijo.example.mq.elasticsearch.entity.User;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * 文档数据新增、更新、删除
 */
public class Doc {
    private static Logger log = LoggerFactory.getLogger(Doc.class);

    //创建文档
    public static void CreateDoc() {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            IndexRequest request = new IndexRequest();
            //自定义文档数据ID
            request.index("users").id("1");
            User user = new User("test", 11, "男");
            //文档数据保存必须JSON
            ObjectMapper mapper = new ObjectMapper();
            String userJson = mapper.writeValueAsString(user);
            request.source(userJson, XContentType.JSON);
            //请求创建
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            //响应
            DocWriteResponse.Result result = response.getResult();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    /**
     * 批量创建
     * */
    public static void CreateBatchDoc() {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            //封装批量
            BulkRequest request = new BulkRequest();
            request.add(new IndexRequest("users").id("1").source(new ObjectMapper().writeValueAsString(new User("test", 11, "男")),XContentType.JSON));
            request.add(new IndexRequest("users").id("2").source(new ObjectMapper().writeValueAsString(new User("2", 22, "女")),XContentType.JSON));
            request.add(new IndexRequest("users").id("3").source(new ObjectMapper().writeValueAsString(new User("我是3", 20, "女")),XContentType.JSON));
            //请求
            BulkResponse response=client.bulk(request,RequestOptions.DEFAULT);
            //响应
            System.out.println(response.getIngestTook());
            System.out.println(response.status());
            System.out.println(response.getItems());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //局部更新
    public static void updateDoc() {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            UpdateRequest request = new UpdateRequest();
            //自定义文档数据ID
            request.index("users").id("1");
            request.doc(XContentType.JSON,"sex","男","name","花花");
            //请求创建
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            //响应
            DocWriteResponse.Result result = response.getResult();
            System.out.println(result);
            GetResult getResult = response.getGetResult();
            System.out.println(getResult);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //全量更新
    public static void updateAllDoc() {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            UpdateRequest request = new UpdateRequest();
            //自定义文档数据ID
            request.index("users").id("1");
            User user = new User("小小", 20, "女");
            ObjectMapper mapper = new ObjectMapper();
            String userJson = mapper.writeValueAsString(user);
            request.doc(userJson,XContentType.JSON);

            //请求创建
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            //响应
            DocWriteResponse.Result result = response.getResult();
            System.out.println(result);
            GetResult getResult = response.getGetResult();
            System.out.println(getResult);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //删除文档
    public static void deleteDoc() {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            DeleteRequest request = new DeleteRequest();
            //自定义文档数据ID users 索引 下文档ID为1
            request.index("users").id("1");
            //请求创建
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            //响应
            DocWriteResponse.Result result = response.getResult( );
            System.out.println(result);
            RestStatus status = response.status();
            System.out.println(status);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    /**
     * 批量删除
     * */
    public static void deleteBatchDoc() {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            //封装批量
            BulkRequest request = new BulkRequest();
            request.add(new DeleteRequest("users").id("1"));
            request.add(new DeleteRequest("users").id("2"));
            request.add(new DeleteRequest("users").id("3"));
            //请求
            BulkResponse response=client.bulk(request,RequestOptions.DEFAULT);
            //响应
            System.out.println(response.getIngestTook());
            System.out.println(response.status());
            System.out.println(response.getItems());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    public static void main(String[] args) {
        //Doc.CreateDoc();
        //Doc.updateDoc();
        //Doc.updateAllDoc();
        //Doc.deleteDoc();
        //Doc.CreateBatchDoc();
        //Doc.deleteBatchDoc();
    }
}
