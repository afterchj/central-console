//package com.example.blt.utils;
//
//import javax.servlet.ReadListener;
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
///**
// * @program: central-console
// * @description:
// * @author: Mr.Ma
// * @create: 2019-12-17 17:00
// **/
//public class CustomRequestWrapper extends HttpServletRequestWrapper {
//
//    private byte[] requestBody;
//
//    public CustomRequestWrapper(HttpServletRequest request) {
//        super(request);
//        requestBody = HttpRequestUtil.getRequestBody(request).getBytes();
//    }
//
//    public byte[] getRequestBody() {
//        return requestBody;
//    }
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);
//        ServletInputStream servletInputStream = new ServletInputStream() {
//            @Override
//            public boolean isFinished() {
//                return false;
//            }
//            @Override
//            public boolean isReady() {
//                return false;
//            }
//            @Override
//            public void setReadListener(ReadListener readListener) {
//            }
//
//            @Override
//            public int read() throws IOException {
//                return byteArrayInputStream.read();
//            }
//        };
//        return servletInputStream;
//    }
//
//    @Override
//    public BufferedReader getReader() throws IOException {
//        return new BufferedReader(new InputStreamReader(getInputStream()));
//    }
//}
//
