package com.example.blt.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.utils.FastJsonDiff;
import com.example.blt.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * @program: central-console
 * @description: 防止表单重复提交拦截器-基于Redis缓
 * @author: Mr.Ma
 * @create: 2019-12-17 14:08
 **/
//@Component
public class ProtectSameCommitInterceptor extends HandlerInterceptorAdapter{

    @Resource
    private RedisUtil redisUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${samecommit.time-out-time}")
    private long timeOutTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //Object value = null;
//        if (request instanceof RequestFacade){
            //RequestFacade requestFacade = (RequestFacade) request;
            //获取请求类型
            String method = request.getMethod();
            //获取请求地址
            String requestURI = request.getRequestURI();
            //获取ip地址
            //String ipAddress = IpAddressUtil.getRealIpAddress(request);
            String ipAddress  = request.getRemoteAddr();
//            System.out.println(ipAddress);
            if("post".equalsIgnoreCase(method)){
                //获取post提交的内容
                String data = getRequestData(request);
                StringBuilder key = new StringBuilder(requestURI);
                key.append(ipAddress).append(data);
                //value =  redisUtil.get(key.toString());
                if(redisUtil.get(key.toString()) != null){
                    logger.info("发现重复记录："+key,toString());
                    //重置response
                    //response.reset();
                    //设置编码格式
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter pw = response.getWriter();
                    pw.write("fail");
                    pw.flush();
                    pw.close();
                    return false;
                    //throw new Exception(timeOutTime+"秒内不能重复点击");
                }else {
                    redisUtil.set(key.toString(),key.toString(),timeOutTime);
                    return true;
                }
            }else {
                return true;
            }
//        }
//        else {
//            return true;
//        }
    }

    private String getRequestData(HttpServletRequest httpServletRequest) {
        HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(httpServletRequest);
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        InputStreamReader inputStreamReader=null;
        ServletInputStream servletInputStream =null;
        try {
            servletInputStream = httpServletRequestWrapper.getInputStream();
            inputStreamReader=new InputStreamReader (servletInputStream, Charset.forName("UTF-8"));
            reader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            return "";
        }finally {
            try {
                if(servletInputStream!=null){
                    servletInputStream.close();
                }
                if(inputStreamReader!=null){
                    inputStreamReader.close();
                }
                if(reader!=null){
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        StringBuffer allKey = FastJsonDiff.getAllKey(jsonObject);
        return allKey.toString();
    }
}
