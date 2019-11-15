/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.weixin4j.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.geyl.util.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geyl.exception.MyException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 请求输出流
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class Response {

    private HttpsURLConnection httpsUrlConnection;
    private int status;
    private InputStream inputStream;
    private String responseAsString = null;
    private boolean streamConsumed = false;

    public Response() {
    }

    public Response(HttpsURLConnection httpsUrlConnection) throws IOException {
        this.httpsUrlConnection = httpsUrlConnection;
        this.status = httpsUrlConnection.getResponseCode();
        if (null == (inputStream = httpsUrlConnection.getErrorStream())) {
            inputStream = httpsUrlConnection.getInputStream();
        }
    }

    /**
     * 转换为输出流
     *
     * @return 输出流
     */
    public InputStream asStream() {
        if (streamConsumed) {
            throw new IllegalStateException("Stream has already been consumed.");
        }
        return inputStream;
    }

    /**
     * 将输出流转换为String字符串
     *
     * @return 输出内容
     */
    public String asString() throws MyException {
        if (null == responseAsString) {
            BufferedReader br;
            try {
                InputStream stream = asStream();
                if (null == stream) {
                    return null;
                }
                br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuilder bufffer = new StringBuilder();
                String line;
                while (null != (line = br.readLine())) {
                    bufffer.append(line).append("\n");
                }
                this.responseAsString = bufffer.toString();
                stream.close();
                //输出流读取完毕，关闭连接
                if (httpsUrlConnection != null) {
                    httpsUrlConnection.disconnect();
                }
                streamConsumed = true;
            } catch (NullPointerException npe) {
                // don't remember in which case npe can be thrown
                throw new MyException(npe.getMessage(), npe);
            } catch (IOException ioe) {
                throw new MyException(ioe.getMessage(), ioe);
            }
        }
        return responseAsString;
    }

    /**
     * 将输出流转换为JSON对象
     *
     * @return JSONObject对象
     */
    public JSONObject asJSONObject() throws MyException {
        return JSONObject.parseObject(asString());
    }

    /**
     * 将输出流转换为JSON对象
     *
     * @return JSONArray对象
     */
    public JSONArray asJSONArray() throws MyException {
        return JSONArray.parseArray(asString());
    }

    /**
     * 获取响应状态
     *
     * @return 响应状态
     */
    public int getStatus() {
        return status;
    }
}
