package com.xin.springboot.web.model;

import java.io.Serializable;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: log模板类
 * @date 2018-05-19 20:28
 * @Copyright (C)2018 , Luchaoxin
 */
public class LogInfo implements Serializable {
    private Long logId;
    private String sessionId;
    private String description;
    private String param;
    private String type;
    //成功访问编码
    private int code = 0;
    private String moduleName;
    private String visitType;
    private String message;
    private String userCode;
    private String userName;
    private String host;
    private String operateType;

    public long getLogId() {
        return logId;
    }

    public void setLogId(long logId) {
        this.logId = logId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public LogInfo() {
    }

    @Override
    public String toString() {
        return "LogInfo{" +
                "logId=" + logId +
                ", sessionId='" + sessionId + '\'' +
                ", description='" + description + '\'' +
                ", param='" + param + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", visitType='" + visitType + '\'' +
                ", message='" + message + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
