package com.example.blt.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-12-17 17:01
 **/
@Slf4j
public class HttpRequestUtil {

    public static String getRequestBody(HttpServletRequest request) {

        StringBuffer stringBuffer = new StringBuffer();
        try (ServletInputStream servletInputStream = request.getInputStream()){
            String line = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(servletInputStream));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

        } catch (IOException e) {
            log.error(">>>> error occurred while get request inputStream, error message={} <<<<",e.getMessage());
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
