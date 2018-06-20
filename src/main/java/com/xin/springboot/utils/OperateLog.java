package com.xin.springboot.utils;

import com.suntek.eap.EAP;
import com.suntek.eap.web.SessionContext;
import com.xin.springboot.web.model.LogInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;


public class OperateLog {
    JdbcTemplate jdbc;
    private static String sql = "insert into OPERATELOG(ID, USERNAME, OPERATIME, OPERAKIND, OPERATYPE, OPERARESULT, " +
            " OPERACONTENT, IP, OPERAMODE, MAC_ADDRESS) values(?,?,?,?,?,?,?,?,?,?)";


    /**
     * @param userCode    用户编码
     * @param operDetail  操作详情
     * @param OperaMode   日志类型1：系统日志 2 用户操作日志
     * @param code
     * @param operateType 操作类型
     */
    public static void log(String userCode, String operDetail, int OperaMode, String operateType, String code) {
        int id = EAP.keyTool.getInteger("OPERATELOG");

        Date current = new Date();

        String ip = SessionContext.getContext().getRemoteAddr();

//		String macAddress = MacAddressUtil.getMacAddress(ip);

        String operationCh = toChineseByOperateType(operateType);

        String operateResult = getResultByCode(code);

//		EAP.jdbc.getTemplate().update(sql, new Object[]{ id, userCode, current, "一般", operationCh, operateResult, operDetail, ip, OperaMode, macAddress });
    }

    private static String getResultByCode(String code) {
        if ("0" .equals(code)) {
            return "成功";
        }
        return "失败";
    }

    private static String toChineseByOperateType(String operateType) {
        if ("insert" .equals(operateType)) {
            return "增加";
        }
        if ("update" .equals(operateType)) {
            return "修改";
        }
        if ("delete" .equals(operateType)) {
            return "删除";
        }
        if ("select" .equals(operateType)) {
            return "查询";
        }
        return "";
    }

    public static void log(LogInfo logInfo) {

    }
}
