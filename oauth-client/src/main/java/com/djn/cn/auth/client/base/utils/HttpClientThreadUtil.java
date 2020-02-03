package com.djn.cn.auth.client.base.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 
 * <b>类 名：</b>HttpClientThreadUtil<br/>
 * <b>类描述：</b>多线程处理HttpClient请求<br/>
 * <b>创建人：</b>BigJia-Perfect<br/>
 * <b>创建时间：</b>2018年3月10日 下午11:33:36<br/>
 * <b>修改人：</b>BigJia-Perfect<br/>
 * <b>修改时间：</b>2018年3月10日 下午11:33:36<br/>
 * <b>修改备注：</b><br/>
 *
 * @version 4.0<br/>
 *
 */
public class HttpClientThreadUtil
{
    private static Log log = LogFactory.getLog(HttpClientThreadUtil.class);
    /**
     * 最大请求数
     */
    private final static Integer REQUEST_MAX = 100;
    private final static Integer requestTimeOut = 60000;
    private final static Integer connectTimeOut = 60000;
    private final static Integer socketTimeOut = 60000;
    private final static String ENCODE = "UTF-8";

    /**
     * reqList中Map的格式{url:'请求地址',params:{请求参数}}
     * 
     * @param reqList
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String[] threadPost(List<Map<String, Object>> reqList)
    {
        String[] results = new String[reqList.size()];
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 设置线程数最大100,如果超过100为请求个数
        cm.setMaxTotal(reqList.size() > REQUEST_MAX ? reqList.size() : REQUEST_MAX);
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        try
        {
            PostThread[] postThreads = new PostThread[reqList.size()];
            for (int i = 0; i < reqList.size(); i++)
            {
                Map<String, Object> req = reqList.get(i);
                HttpPost post = new HttpPost((String) req.get("url"));
                postThreads[i] = new PostThread(httpclient, post, (Map<String, Object>) req.get("params"), ENCODE, i + 1);
            }
            // 执行线程
            for (PostThread pt : postThreads)
            {
                pt.start();
            }
            // 设置所有线程执行完毕之后再执行后续代码
            for (PostThread pt : postThreads)
            {
                pt.join();
            }
            for (int i = 0; i < reqList.size(); i++)
            {
                results[i] = postThreads[i].call();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.debug("多线程post请求参数：" + reqList.toString());
            log.debug("多线程post方法异常：" + e.getMessage());
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * 
     * singlePost(单个post请求)
     * 
     * @param url 请求URL
     * @param params 参数
     * @return
     * @exception @since 4.0
     * @author canwang2
     */
    public static String singlePost(String url, Map<String, String> params)
    {
        List<Map<String, Object>> reqList = new ArrayList<Map<String, Object>>();
        Map<String, Object> req = new HashMap<>();
        req.put("url", url);
        req.put("params", params);
        reqList.add(req);

        String[] result = HttpClientThreadUtil.threadPost(reqList);
        if (result.length > 0)
        {
            return result[0];
        }
        return null;
    }

    /**
     * reqList中Map的格式{url:'请求地址',params:{请求参数},encode:'编码'}
     * 
     * @param reqList
     * @return
     */
    public static String[] threadGet(List<Map<String, Object>> reqList)
    {
        System.out.println("get共执行" + reqList.size() + "个请求");
        String[] results = new String[reqList.size()];
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // 设置线程数最大100,如果超过100为请求个数
        cm.setMaxTotal(reqList.size() > REQUEST_MAX ? reqList.size() : REQUEST_MAX);
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        try
        {
            GetThread[] getThreads = new GetThread[reqList.size()];
            for (int i = 0; i < reqList.size(); i++)
            {
                Map<String, Object> req = reqList.get(i);
                HttpGet get = new HttpGet((String) req.get("url"));
                getThreads[i] = new GetThread(httpclient, get, i + 1);
            }
            // 执行线程
            for (GetThread gt : getThreads)
            {
                gt.start();
            }
            // 设置所有线程执行完毕之后再执行后续代码
            for (GetThread gt : getThreads)
            {
                gt.join();
            }
            for (int i = 0; i < reqList.size(); i++)
            {
                results[i] = getThreads[i].call();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.debug("多线程get方法异常：" + e.getMessage());
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * 实现Callable回调接口
     */
    static class PostThread extends Thread implements Callable<String>
    {

        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpPost httppost;
        private final int id;
        private String result = null;

        public PostThread(CloseableHttpClient httpClient, HttpPost httppost, Map<String, Object> params, String encode, int id)
                throws UnsupportedEncodingException
        {
            // 设置超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(requestTimeOut).setConnectTimeout(connectTimeOut)
                    .setSocketTimeout(socketTimeOut).build();
            httppost.setConfig(requestConfig);
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty())
            {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, Object> entry : params.entrySet())
                {
                    Object value = entry.getValue();
                    if (value != null)
                    {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value.toString()));
                    }
                }
            }
            if (pairs != null && pairs.size() > 0)
            {
                encode = encode == null ? "UTF-8" : encode;
                httppost.setEntity(new UrlEncodedFormEntity(pairs, encode));
            }
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httppost = httppost;
            this.id = id;
        }

        @Override
        public void run()
        {
            try
            {
                CloseableHttpResponse response = httpClient.execute(httppost, context);
                try
                {
                    // get the response body as an array of bytes
                    HttpEntity entity = response.getEntity();
                    if (entity != null)
                    {
                        result = EntityUtils.toString(entity);
                    }
                }
                finally
                {
                    response.close();
                }
            }
            catch (Exception e)
            {
                log.error(id + " - error: " + e);
            }
        }

        @Override
        public String call() throws Exception
        {
            return result;
        }
    }

    /**
     * A thread that performs a GET.
     */
    static class GetThread extends Thread implements Callable<String>
    {

        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;
        private final int id;
        private String result = null;

        public GetThread(CloseableHttpClient httpClient, HttpGet httpget, int id)
        {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httpget = httpget;
            this.id = id;
        }

        /**
         * Executes the GetMethod and prints some status information.
         */
        @Override
        public void run()
        {
            try
            {
                System.out.println(id + " - about to get something from " + httpget.getURI());
                CloseableHttpResponse response = httpClient.execute(httpget, context);
                try
                {
                    System.out.println(id + " - get executed");
                    // get the response body as an array of bytes
                    HttpEntity entity = response.getEntity();
                    if (entity != null)
                    {
                        result = EntityUtils.toString(entity);
                    }
                }
                finally
                {
                    response.close();
                }
            }
            catch (Exception e)
            {
                System.out.println(id + " - error: " + e);
            }
        }

        @Override
        public String call() throws Exception
        {
            return result;
        }
    }

    public static void main(String[] args)
    {
    }

}
