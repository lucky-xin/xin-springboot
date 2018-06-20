//package com.xin.springboot;
//
//import org.junit.Test;
//
///**
// * @author Luchaoxin
// * @version V1.0
// * @Description: 测试类(用一句话描述该类做什么)
// * @date 2018-06-04 14:34
// * @Copyright (C)2017 , Luchaoxin
// */
//public class TestClass {
//
//    protected String filterTradeCode = "69,70,71,72,73,74,02,04,76";
//
//    @Test
//    public void contextLoads() {
//        String[] tradeCodes = filterTradeCode.split(",");
//
//        StringBuilder tradeCodeCondition = new StringBuilder("(");
//        int index = tradeCodes.length - 1;
//        for (int i = index; i >= 0; i--) {
//            tradeCodeCondition.append("'").append(tradeCodes[i]).append("'");
//            if (i != 0) {
//                tradeCodeCondition.append(",");
//            }
//        }
//        tradeCodeCondition.append(")");
//        System.out.println(tradeCodeCondition.toString());
//        StringBuilder sb = new StringBuilder("9999999999999999");
//        getStringBuilder(sb);
//        System.out.println(sb);
//    }
//
//    void getStringBuilder(StringBuilder sb) { sb.append("fdjfkdfklkl");
//    }
//}
