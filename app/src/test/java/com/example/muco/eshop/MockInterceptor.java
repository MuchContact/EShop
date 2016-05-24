package com.example.muco.eshop;

import com.example.muco.eshop.util.FileUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockInterceptor implements Interceptor {
    private final Response.Builder builder;
    private final String responseFile;
    private String responseJsonPath;

    public MockInterceptor(String jsonFullPath, Response.Builder builder, String responseFile) {
        this.responseJsonPath = jsonFullPath;
        this.builder = builder;
        this.responseFile = responseFile;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String responseString = createResponseBody();

        return builder
                .request(chain.request())
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .build();
    }

    /**
     * 读文件获取json字符串，生成ResponseBody
     *
     * @return
     */
    private String createResponseBody() {
        return getResponseString(responseFile);
    }

    private String getResponseString(String fileName) {
        return FileUtil.readFile(responseJsonPath + fileName, "UTF-8").toString();
    }
}
