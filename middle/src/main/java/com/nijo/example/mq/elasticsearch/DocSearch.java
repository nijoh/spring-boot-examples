package com.nijo.example.mq.elasticsearch;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DocSearch {
    private static Logger log = LoggerFactory.getLogger(DocSearch.class);

    //查询索引下某个文档数据
    public static void findDocById(String index, String Id) {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            GetRequest request = new GetRequest();
            //封装索引 和文档ID
            request.index(index).id(Id);
            //请求
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            //返回JSON字符串
            log.info(response.getSourceAsString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //索引文档数据--全量查询
    public static void findAllDocByIndex(String index) {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            //封装请求参数
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder builder = new SearchSourceBuilder();
            //全量查询 等于body match_all：{}
            builder.query(QueryBuilders.matchAllQuery());
            //indices可以多个索引
            request.indices(index).source(builder);
            //请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            //响应
            SearchHits hits = response.getHits();
            System.out.println("总共的查询条数:" + hits.getTotalHits());
            System.out.println("查询时间:" + response.getTook());
            System.out.println("详细数据；");
            for (SearchHit hit : hits) {
                // 输出每条查询的结果信息
                System.out.println(hit.getSourceAsString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //条件查询、结果过滤、排序、分页
    public static void queryDoc(String index) {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            //封装请求参数
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder builder = new SearchSourceBuilder();
//            //条件查询
//            builder.query(QueryBuilders.termQuery("age", 22));
            //条件多值查询
            // builder.query(QueryBuilders.termsQuery("age", 22,11));
//            //结果过滤
//            String[] includes={"name"};//包含
//            String[] excludes={};//排除
//            builder.fetchSource(includes,excludes);
            //排序
//            builder.query(QueryBuilders.matchAllQuery());
//            builder.sort("age", SortOrder.DESC);
            //分页
            builder.query(QueryBuilders.matchAllQuery());
            builder.from(0);//页数，计算方式(页码-1)*size 第二页 （2-1）*2
            builder.size(2);//每页多少条
            //indices可以多个索引
            request.indices(index).source(builder);
            //请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            //响应
            SearchHits hits = response.getHits();
            System.out.println("总共的查询条数:" + hits.getTotalHits());
            System.out.println("查询时间:" + response.getTook());
            System.out.println("详细数据；");
            for (SearchHit hit : hits) {
                // 输出每条查询的结果信息
                System.out.println(hit.getSourceAsString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //多条件组合查询
    public static void queryCombinedDoc(String index) {
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            //封装请求参数
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder builder = new SearchSourceBuilder();
            //must 多条件同时满足返回 与and 语法一致
//            BoolQueryBuilder must = QueryBuilders.boolQuery()
//                    .must(QueryBuilders.matchQuery("name", "test"))
//                     .must(QueryBuilders.matchQuery("age", 22));
            //一定不包含
//            QueryBuilders.boolQuery().mustNot()
            //should 多条满足任意一条都返回 与or 一致
            BoolQueryBuilder must = QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("name", "test"))
                    .should(QueryBuilders.matchQuery("age", 22));

            builder.query(must);
            //indices可以多个索引
            request.indices(index).source(builder);
            //请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            //响应
            SearchHits hits = response.getHits();
            System.out.println("总共的查询条数:" + hits.getTotalHits());
            System.out.println("查询时间:" + response.getTook());
            System.out.println("详细数据；");
            for (SearchHit hit : hits) {
                // 输出每条查询的结果信息
                System.out.println(hit.getSourceAsString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //范围查询
    public static void queryRangeDoc(String index){
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            //封装请求参数
            SearchRequest request = new SearchRequest();
            //范围查询大于11 小于22区间
            SearchSourceBuilder builder = new SearchSourceBuilder();
            RangeQueryBuilder range = QueryBuilders.rangeQuery("age").gte(11).lte(22);

            builder.query(range);
            //indices可以多个索引
            request.indices(index).source(builder);
            //请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            //响应
            SearchHits hits = response.getHits();
            System.out.println("总共的查询条数:" + hits.getTotalHits());
            System.out.println("查询时间:" + response.getTook());
            System.out.println("详细数据；");
            for (SearchHit hit : hits) {
                // 输出每条查询的结果信息
                System.out.println(hit.getSourceAsString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //模糊查询
    public static void queryLikeDoc(String index){
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            //封装请求参数
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder builder = new SearchSourceBuilder();
            //模糊查询 Fuzziness.ONE允许跨度一个词不匹配
            FuzzyQueryBuilder like = QueryBuilders.fuzzyQuery("name", "我是").fuzziness(Fuzziness.ONE);

            builder.query(like);
            //indices可以多个索引
            request.indices(index).source(builder);
            //请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            //响应
            SearchHits hits = response.getHits();
            System.out.println("总共的查询条数:" + hits.getTotalHits());
            System.out.println("查询时间:" + response.getTook());
            System.out.println("详细数据；");
            for (SearchHit hit : hits) {
                // 输出每条查询的结果信息
                System.out.println(hit.getSourceAsString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //聚合查询
    public static void queryPolyDoc(String index){
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            //封装请求参数
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder builder = new SearchSourceBuilder();
//            //最大值 maxAage自行取 field查询字段
//            MaxAggregationBuilder max = AggregationBuilders.max("maxAage").field("age");
//            //size(0) 不显示元数据 看"aggregations":{"max#maxAage":{"value":22.0}}}"
//            builder.aggregation(max).size(0);

            //分组
            TermsAggregationBuilder group = AggregationBuilders.terms("age_group").field("age");
            builder.aggregation(group);
            //indices可以多个索引
            request.indices(index).source(builder);
            //请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            //响应
            SearchHits hits = response.getHits();
            System.out.println("总共的查询条数:" + hits.getTotalHits());
            System.out.println("查询时间:" + response.getTook());
            System.out.println("详细数据；");
            for (SearchHit hit : hits) {
                // 输出每条查询的结果信息
                System.out.println(hit.getSourceAsString());
            }
            System.out.println("聚合查询："+response.getAggregations());


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }

    //高亮查询
    public static void queryHitsHighlightDoc(String index){
        //创建客户端
        RestHighLevelClient client = EsUtils.createES();
        try {
            //封装请求参数
            SearchRequest request = new SearchRequest();
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.termsQuery("sex", "女"));
            //高亮封装 "highlight":{"sex":["<font color='red'>[0xffffffe5][0xffffffa5]
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.preTags("<font color='red'>");
            highlightBuilder.postTags("</font>");
            //需要增亮的字段
            highlightBuilder.field("sex");
            builder.highlighter(highlightBuilder);


            //indices可以多个索引
            request.indices(index).source(builder);
            //请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            //响应
            SearchHits hits = response.getHits();
            System.out.println("总共的查询条数:" + hits.getTotalHits());
            System.out.println("查询时间:" + response.getTook());
            System.out.println("详细数据；");
            for (SearchHit hit : hits) {
                // 输出每条查询的结果信息
                System.out.println(hit.getSourceAsString());
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EsUtils.closeES(client);
        }
    }


    public static void main(String[] args) {
        //DocSearch.findDocById("users","1");
        //DocSearch.findAllDocByIndex("users");
        //DocSearch.queryDoc("users");
       // DocSearch.queryCombinedDoc("users");
       // DocSearch.queryRangeDoc("users");
        //DocSearch.queryLikeDoc("users");
//        DocSearch.queryPolyDoc("users");
       // DocSearch.queryHitsHighlightDoc("users");
    }
}
