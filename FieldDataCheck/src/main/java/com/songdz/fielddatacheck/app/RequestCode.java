package com.songdz.fielddatacheck.app;

/**
 * Created by SongDz on 2014/6/13.
 */
public interface RequestCode {
    String CHECK_PASSWORD = "CheckPassword";
    String CHECK_DATA = "CheckData";
    String querySql_checkData = "SELECT 项目内节点编号,MAX(紧缩型时间传感器_实时时间) FROM 水文监测仪lzipv6 GROUP BY 项目内节点编号";
    String httpUrl_checkData = "http://159.226.15.192:8080/FieldDataCheck/CheckData";
    String httpUrl_checkPassword = "http://159.226.15.192:8080/FieldDataCheck/CheckPassword";
}
