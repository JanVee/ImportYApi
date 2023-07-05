package com.janvee.importyapi.tcf;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.janvee.importyapi.constant.YApiConstants;
import com.janvee.importyapi.content.YApiProjectProperty;
import com.janvee.importyapi.model.SwaggerRequest;
import com.janvee.importyapi.model.SwaggerResponse;
import com.janvee.importyapi.model.YApiResponse;
import com.janvee.importyapi.util.HttpClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.util.List;
import java.util.Map;

public class Swagger {
    private final Gson gson = new Gson();

    public YApiResponse getSwaggerJson(YApiProjectProperty property)
    {
        String yApiUrl = property.getUrl();
        String localUrl = property.getLocalUrl();
        String token = property.getToken();
        String response;
        try {
            response = HttpClientUtils.ObjectToString(HttpClientUtils.getHttpclient().execute(
                            this.getHttpGet(localUrl + YApiConstants.yApiJsons)),
                    "utf-8");
            SwaggerResponse retApi = gson.fromJson(response, SwaggerResponse.class);
            if (retApi.getPaths() != null && !retApi.getPaths().isEmpty()) {
                retApi.getPaths().values().stream()
                        .flatMap(path -> ((Map<String, Object>) path).entrySet().stream())
                        .forEach(row -> {
                            String method = row.getKey();
                            if ("get".equals(method)) {
                                Map<String, Object> row1 = (Map<String, Object>) row.getValue();
                                Object parametersObj = row1.get("parameters");
                                if (parametersObj instanceof List) {
                                    List<Map<String, Object>> row2 = (List<Map<String, Object>>) parametersObj;
                                    if (!row2.isEmpty()) {
                                        row2.forEach(v -> {
                                            Map<String, Object> schema = (Map<String, Object>) v.get("schema");
                                            v.put("description", "{" + String.valueOf(schema.get("format")) + "}" + String.valueOf(v.get("description")));
                                        });
                                        row1.put("parameters", row2);
                                    }
                                }
                            }
                        });

                // 将 retApi 对象转换为 JSON 字符串
                String dataJsonStr = gson.toJson(retApi);
                SwaggerRequest swaggerRequest = new SwaggerRequest();
                swaggerRequest.setType("swagger");
                swaggerRequest.setMerge("mergin");
                swaggerRequest.setToken(token);
                swaggerRequest.setJson(dataJsonStr);

                String resp = HttpClientUtils
                        .ObjectToString(HttpClientUtils.getHttpclient().execute(
                                this.getHttpPost(yApiUrl + YApiConstants.yApiImportData,
                                        gson.toJson(swaggerRequest))), "utf-8");
                YApiResponse yapiResponse = gson.fromJson(resp, YApiResponse.class);

                String respJsonStr = gson.toJson(yapiResponse);
                System.out.println("===============");
                System.out.println(respJsonStr);

                return yapiResponse;
            }
            return new YApiResponse(0, "获取项目接口Json数据异常");
        } catch (Exception e) {
            e.printStackTrace();
            return new YApiResponse(0, e.toString());
        }
    }

    public int getProjectId(YApiProjectProperty property)
    {
        String yApiUrl = property.getUrl();
        String localUrl = property.getLocalUrl();
        String token = property.getToken();
        String response;

        try {
            response = HttpClientUtils.ObjectToString(HttpClientUtils.getHttpclient().execute(
                            this.getHttpGet(yApiUrl + YApiConstants.yApiProject + "?token=" + token)),
                    "utf-8");
            YApiResponse yapiResponse = gson.fromJson(response, YApiResponse.class);
            if (yapiResponse.getErrcode() == 0) {
                Object data = yapiResponse.getData();

                String dataString = gson.toJson(data);
                JsonElement jsonElement = JsonParser.parseString(dataString);
                JsonObject dataObject = jsonElement.getAsJsonObject();
                return dataObject.get("_id").getAsInt();
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private HttpGet getHttpGet(String url) {
        return HttpClientUtils
                .getHttpGet(url, "application/json", "application/json; charset=utf-8");
    }

    private HttpPost getHttpPost(String url, String body) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json;charset=utf-8");
        HttpEntity reqEntity = new StringEntity(body == null ? "" : body, "UTF-8");
        httpPost.setEntity(reqEntity);
        return httpPost;
    }
}
