package com.jxnu;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.sun.deploy.net.HttpRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * java发送http的get请求
 */
public class Main
{
    /**
     * 向指定URL发送GET方式的请求
     * @param url  发送请求的URL
     * @param param 请求参数
     * @return URL 代表远程资源的响应
     */
    public static String sendGet(String url, String param)
    {
        String result = "";
        String urlName = url + "?" + param;
        try
        {
            URL realUrl = new URL(urlName);
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立实际的连接
            conn.connect();
            //获取所有的响应头字段
            Map<String,List<String>> map = conn.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line;
            }
        }
        catch (Exception e)
        {
            System.out.println("发送GET请求出现异常" + e);
            e.printStackTrace();
        }
        return result;
    }

    //测试发送GET请求,获取天气信息
    public static void main(String[] args)
    {
        //发送GET请求
        String cityCode = "361128";
        String param = "city="+cityCode+"&key=170b926bc81f95457138c2d036a4b50a";
        String s = Main.sendGet("https://restapi.amap.com/v3/weather/weatherInfo",param);
        //System.out.println(s);
        Object province = JSONPath.read(s.toString(),"$.lives[0].province");
        Object city = JSONPath.read(s.toString(),"$.lives[0].city");
        Object adcode = JSONPath.read(s.toString(),"$.lives[0].adcode");
        Object weather = JSONPath.read(s.toString(),"$.lives[0].weather");
        Object temperature = JSONPath.read(s.toString(),"$.lives[0].temperature");
        Object winddirection = JSONPath.read(s.toString(),"$.lives[0].winddirection");
        Object windpower = JSONPath.read(s.toString(),"$.lives[0].windpower");
        Object humidity = JSONPath.read(s.toString(),"$.lives[0].humidity");
        Object reporttime = JSONPath.read(s.toString(),"$.lives[0].reporttime");
        System.out.println("省份："+province);
        System.out.println("城市："+city);
        System.out.println("地区代码："+cityCode);
        System.out.println("天气："+weather);
        System.out.println("温度："+temperature);
        System.out.println("风向："+winddirection);
        System.out.println("风力："+windpower);
        System.out.println("湿度："+humidity);
        System.out.print("时间："+reporttime);
    }
}