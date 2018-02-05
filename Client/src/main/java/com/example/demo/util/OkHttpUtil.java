package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.state.FinalJson;
import okhttp3.*;
import okio.BufferedSink;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017-04-11.
 */
public class OkHttpUtil {
    /**
     * 初始化网络请求
     */
    private static final  OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private static final OkHttpClient client = new OkHttpClient();
    static {
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10,TimeUnit.SECONDS);
        builder.readTimeout(30,TimeUnit.SECONDS);
    }

    /**
     * 同步请求get
     * @param url 请求的URL
     * @param sMap 请求参数
     * @return
     */
    public String SyncGet(String url, Map<String,String> sMap) throws Exception{
        String parameter = null;
        String URL = url;
        String resultSet = "";
        //设置参数
        StringBuffer sb = new StringBuffer();
        if(sMap!=null){
            for (Map.Entry<String, String> entry : sMap.entrySet()) {
                sb.append(entry.getKey()+"="+entry.getValue()+"&");
            }
            parameter = sb.deleteCharAt(sb.length()-1).toString();
            if (!parameter.equals("")) {
                URL = url
                        + "?"
                        + parameter;
            }
        }

        Request request = new Request.Builder()
                .url(URL)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            resultSet = response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return resultSet;
    }
    /**
     * 异步get
     * @param url 请求的URL
     * @param sMap 请求参数
     * @return
     */
    public void AsyncGet(String url, Map<String,String> sMap,Callback callback) throws Exception{
        String parameter = null;
        String URL = url;
        //设置参数
        StringBuffer sb = new StringBuffer();
        if(sMap!=null){
            for (Map.Entry<String, String> entry : sMap.entrySet()) {
                sb.append(entry.getKey()+"="+entry.getValue()+"&");
            }
            parameter = sb.deleteCharAt(sb.length()-1).toString();
            if (!parameter.equals("")) {
                URL = url
                        + "?"
                        + parameter;
            }
        }
        //发起请求
        Request request = new Request.Builder()
                .url(URL)
                .build();
        //判断请求
        client.newCall(request).enqueue(callback);
    }

    /**
     * Post提交字符串
     * @param url 请求的地址
     * @param sMap 请求的数据
     * @param httpMediaType 请求的格式
     * @return
     */
    public String PostString(String url, Map<String,String> sMap,FinalJson.HttpMediaType httpMediaType){
        String resultSet = "";
        String parameter = null;
        MediaType mediaType = null;
        switch (httpMediaType){
            case text:
                mediaType =  MediaType.parse("text/plain; charset=utf-8");
                if(sMap!=null){
                    StringBuffer sb = new StringBuffer();
                    for (Map.Entry<String, String> entry : sMap.entrySet()) {
                        sb.append(entry.getKey()+"="+entry.getValue()+"&");
                    }
                    parameter = sb.deleteCharAt(sb.length()-1).toString();
                }
                break;
            case json:
                mediaType =  MediaType.parse("application/json; charset=utf-8");
                parameter = JSON.toJSONString(sMap);
                break;
            default:
                mediaType =  MediaType.parse("text/plain; charset=utf-8");
                break;
        }

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, parameter))
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("服务器端错误: " + response);
            }
            resultSet = response.body().string();
            System.out.println(resultSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Post 提交字符串
     * @param url 提交的地址
     * @param sMap 提交的参数
     * @param httpMediaType 提交的类型
     * @return
     */
    public String PostString(String url, String sMap,FinalJson.HttpMediaType httpMediaType){
        String resultSet = "";
        String parameter = null;
        MediaType mediaType = null;
        switch (httpMediaType){
            case text:
                mediaType =  MediaType.parse("text/plain; charset=utf-8");
                break;
            case json:
                mediaType =  MediaType.parse("application/json; charset=utf-8");

                break;
            default:
                mediaType =  MediaType.parse("text/plain; charset=utf-8");
                break;
        }
        parameter = sMap;
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, parameter))
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("服务器端错误: " + response);
            }
            resultSet = response.body().string();
            System.out.println(resultSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Post提交From表单
     * @param url 请求的地址
     * @param sMap 请求的数据
     * @return
     * @throws Exception
     */
    public String PostFrom(String url, Map<String,String> sMap) throws Exception{
        String resultSet = "";

        FormBody.Builder builder = new FormBody.Builder();
        if(sMap!=null){
            for (Map.Entry<String, String> entry : sMap.entrySet()) {
                builder.add(entry.getKey(),entry.getValue());
            }
        }

        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        resultSet = response.body().string();
        System.out.println(resultSet);
        return resultSet;
    }

    /**
     * Post提交数据流
     * @param url 请求的地址
     * @param postBody 需要提交的数据
     * @param httpMediaType 提交的格式
     * @return
     * @throws Exception
     */
    public String PostStream(String url, String postBody,FinalJson.HttpMediaType httpMediaType) throws Exception{
        RequestBody requestBody =null;
        String resultSet = "";
        switch (httpMediaType){
            case text:
                MediaType mediaType =  MediaType.parse("text/plain");
                requestBody = new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return mediaType;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        sink.writeUtf8(postBody);
                    }

                    @Override
                    public long contentLength() throws IOException {
                        return postBody.length();
                    }
                };
                break;
            case json:
                MediaType mediaType1 =  MediaType.parse("application/json");
                requestBody = new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return mediaType1;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        sink.writeUtf8(postBody);
                    }

                    @Override
                    public long contentLength() throws IOException {
                        return postBody.length();
                    }
                };
                break;
            default:
                MediaType mediaType2 =  MediaType.parse("text/plain");
                requestBody = new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return mediaType2;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        sink.writeUtf8(postBody);
                    }

                    @Override
                    public long contentLength() throws IOException {
                        return postBody.length();
                    }
                };
                break;
        }

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }

        resultSet = response.body().string();
        return resultSet;
    }
}
