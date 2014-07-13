package com.songdz.fielddatacheck.app;

/**
 * Created by SongDz on 2014/6/13.
 */
public interface RequestCode {
    String CHECK_PASSWORD = "CheckPassword";
	String CHECK_DATA_LAST_TIME = "CheckDataForLastTime";
	String CHECK_DATA_LAST_DATA = "CheckDataForLastData";
    String querySql_getLastTime = "SELECT 项目内节点编号,MAX(紧缩型时间传感器_实时时间) FROM 水文监测仪lzipv6 GROUP BY 项目内节点编号";
    String querySql_getLastData = "SELECT 项目内节点编号,紧缩型时间传感器_实时时间,电池电压传感器_电压,太阳能电压传感器_电压,红外温度传感器_表面温度,风速风向传感器_风向,风速风向传感器_风速,空气温湿度传感器_空气温度,空气温湿度传感器_空气湿度,雨量传感器_降雨量,土壤传感器1_摄氏温度,土壤传感器1_含水量体积比,土壤传感器1_盐渍度,土壤传感器1_电导率,土壤传感器2_摄氏温度,土壤传感器2_含水量体积比,土壤传感器2_盐渍度,土壤传感器2_电导率,土壤传感器3_摄氏温度,土壤传感器3_含水量体积比,土壤传感器3_盐渍度,土壤传感器3_电导率 FROM 水文监测仪lzipv6 WHERE 项目内节点编号 = ? ORDER BY 数据记录编号 DESC LIMIT 0,1;";
    String httpUrl_checkData = "http://159.226.15.192:8080/FieldDataCheck/Check";
}
