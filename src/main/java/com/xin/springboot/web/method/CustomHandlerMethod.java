package com.xin.springboot.web.method;

import org.springframework.web.method.HandlerMethod;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 自定义HandlerMethod以适配SpringMVC
 * @date 2018-05-17 17:17
 * @Copyright (C)2018 , Luchaoxin
 */
public class CustomHandlerMethod {

    private HandlerMethod handler;

    private String id;

    private boolean isRemote;

    private String author;

    private String description;

    private String since;

    private boolean isLog;

    public HandlerMethod getHandler() {
        return handler;
    }

    public void setHandler(HandlerMethod handler) {
        this.handler = handler;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsRemote() {
        return isRemote;
    }

    public void setIsRemote(boolean remote) {
        isRemote = remote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public boolean getIsLog() {
        return isLog;
    }

    public void setIsLog(boolean log) {
        isLog = log;
    }

    @Override
    public String toString() {
        return "CustomHandlerMethod{" +
                "handler=" + handler +
                ", id='" + id + '\'' +
                ", isRemote=" + isRemote +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", since='" + since + '\'' +
                ", isLog=" + isLog +
                '}';
    }
}
