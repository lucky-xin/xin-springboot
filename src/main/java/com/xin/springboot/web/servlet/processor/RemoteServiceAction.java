package com.xin.springboot.web.servlet.processor;

import com.suntek.eap.log.ServiceLog;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * Remote 服务代理，提供给第三方应用使用，使用之前需调用 RemoteLoginProxy 登录
 *
 * @author zt
 * @version 2015-1-14
 * @Copyright (C)2015 , Suntektech
 * @since 6.0
 */
class RemoteServiceAction {

    static String invokeMethod(int port, String token, String service, String queryString) throws Exception {
        String sessionId = token.split(",")[0];

        CloseableHttpResponse response = null;

        CloseableHttpClient client = HttpClients.createDefault();

        String ret;

        try {
            String url = "http://localhost:" + port + "/portal/rest/v6/" + service;

            if (!queryString.equals("")) {
                url = url + "?" + queryString;
            }

            ServiceLog.debug("Get: " + url);

            HttpGet getMethod = new HttpGet(url);

            getMethod.addHeader("Cookie", "JSESSIONID=" + sessionId + "; Path=/portal/; webapp=portal;");
            getMethod.addHeader("Host", "localhost:" + port);
            getMethod.addHeader("User-Agent", "Mozilla/5.0");

            response = HttpClientHelper.invoke(client, getMethod, false);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                ret = EntityUtils.toString(entity);
            } else {
                ret = "{}";
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                response.close();
            } catch (Exception e) {
            }
            try {
                client.close();
            } catch (Exception e) {
            }
        }
        return ret;
    }

}
