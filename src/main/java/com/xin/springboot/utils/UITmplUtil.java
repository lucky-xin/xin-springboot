package com.xin.springboot.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suntek.eap.jdbc.PageQueryResult;
import com.suntek.eap.log.ServiceLog;
import com.suntek.eap.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取ui模板数据工具类
 *
 * @author Linsj
 * @version 2016-10-16
 */
public class UITmplUtil {

    private final static String eapHome = System.getProperty("EAP_HOME");

    public final static String JSON_DIRCTORY = "/conf/json";

    /**
     * 是否使用ui模板
     *
     * @return
     * @throws Exception
     */
    public static boolean isUseTmpl() {
        Element serverRoot = null;

        File serverFile = new File(eapHome + "/conf/server.xml");

        try {
            serverRoot = parseFile(serverFile).getRootElement();
        } catch (Exception e) {
            ServiceLog.error("获取server.xml文件内容失败" + e.getMessage(), e);
        }

        return "true".equals(serverRoot.attributeValue("useTmpl"));
    }

    /**
     * 获取指定服务的json文件
     *
     * @param service
     * @return
     */
    public static File getJsonFileByService(String service) {
        String fileName = service.replaceAll("/", "_");

        File file = new File(eapHome + JSON_DIRCTORY + "/" + fileName + ".json");

        return file;
    }

    /**
     * 获取ui模板数据根节点
     *
     * @return no use
     */
    public static Element getUiTmplRootElement() {
        Element root = null;

        File file = new File(eapHome + "/conf/ui-tmpl.xml");

        if (file.exists()) {
            try {
                Document contextDoc = parseFile(file);

                root = contextDoc.getRootElement();

            } catch (Exception e) {
                ServiceLog.error("读取前端模板数据出错" + e.getMessage(), e);
            }
        }

        return root;
    }

    /**
     * 格式化ui模板数据
     */
    public static Object getUiTmplObjectByNode(Element node, Map<String, Object> map) {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

        boolean paging = "true".equals(node.attributeValue("paging"));

        int repeats = null == node.attributeValue("repeats") ? 1 : Integer.valueOf(node.attributeValue("repeats"));

        String value = node.attributeValue("value");

        if (map.containsKey(value)) {
            value = StringUtil.toString(map.get(value));
        }

        for (int i = 0; i < repeats; i++) {
            ret.add(JSONObject.parseObject(value));
        }

        if (paging) {
            return new PageQueryResult(25, ret);
        }

        return ret;
    }

    /**
     * 格式化ui模板数据
     *
     * @return
     */
    public static Object getUiTmplObjectByJson(File file) {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

        String jsonStr = getJsonString(file);

        JSONObject json = JSONObject.parseObject(jsonStr);

        int repeats = 1;

        if (json.containsKey("repeats")) {
            repeats = json.getInteger("repeats");
        }

        String data = json.getString("data");

        JSONArray ja = JSONArray.parseArray(data);

        for (int i = 0; i < repeats; i++) {
            ret.add((Map<String, Object>) ja);
        }

        if (json.containsKey("paging") && "true".equals(json.getString("paging"))) {
            int total = repeats * ja.size();
            return new PageQueryResult(total, ret);
        }

        return ret;
    }

    /**
     * 获取指定节点
     *
     * @param root
     * @param service
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Element> getUiTmplNodes(Element root, String service) {
        String nodeName = service.replaceAll("/", "_");

        return root.selectNodes("//" + nodeName);
    }

    /**
     * 解析xml文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static Document parseFile(File file) throws Exception {
        SAXReader reader = new SAXReader();
        reader.setEncoding("utf-8");
        return reader.read(file);
    }

    /**
     * 获取文件内容
     *
     * @param file
     * @return
     */
    private static String getJsonString(File file) {
        StringBuffer sb = new StringBuffer();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            String tempString = null;

            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }

            reader.close();
        } catch (IOException e) {
            ServiceLog.error("获取json文件内容失败" + e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("EAP_HOME", "D://work//tomcat");

        String eapHome = System.getProperty("EAP_HOME");

        File serverFile = new File(eapHome + JSON_DIRCTORY + "/ui_tmpl_test.json");

        System.out.println(getJsonString(serverFile));
		
		/*
		 File file = new File(eapHome + "/conf/ui-tmpl.xml");

		if (!file.exists()) return;
		
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		
		Document contextDoc = null;
		
		try {
			contextDoc = parseFile(file);
			
			Element root = contextDoc.getRootElement();
			
			if ("true".equals(root.attributeValue("useTmpl"))) {
				String url = "ui/test/case";
				
				String nodeName = url.replaceAll("/", "_");
				
				System.out.println(nodeName);
				
				List<Element> elements = root.selectNodes("//" + nodeName);
				
				Element service = elements.get(0);
				
				boolean paging = "true".equals(service.attributeValue("paging"));
				
				int repeats = null == service.attributeValue("repeats") ? 1 : Integer.valueOf(service.attributeValue("repeats"));
				
				for (int i=0; i<repeats; i++) {
					ret.add(JSONObject.fromObject(service.attributeValue("value")));
				}
				
				if (paging) {
					PageQueryResult pqr = new PageQueryResult(25, ret);
				}
				
			}
			
			System.out.println(ret);
			System.out.println(((Map<String,Object>)ret.get(0)).get("type"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}*/
    }
}
