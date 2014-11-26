package com.songdz.com.songdz.fielddatacheck.parsers;

import com.songdz.com.songdz.fielddatacheck.data.AirSensor;
import com.songdz.com.songdz.fielddatacheck.data.Anemometer;
import com.songdz.com.songdz.fielddatacheck.data.InfraredSensor;
import com.songdz.com.songdz.fielddatacheck.data.MetaDataInfo;
import com.songdz.com.songdz.fielddatacheck.data.RainGauge;
import com.songdz.com.songdz.fielddatacheck.data.SoilSensorInfo;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by SongDz on 2014/11/24.
 */
public class MetaDataInfoXMLParser extends DefaultHandler {

    private MetaDataInfo metaDataInfo = null;

//    private String preTag;

    private String objTag;

    private SoilSensorInfo soilSensorInfo1;
    private SoilSensorInfo soilSensorInfo2;
    private SoilSensorInfo soilSensorInfo3;
    private RainGauge rainGauge;
    private InfraredSensor infraredSensor;
    private AirSensor airSensor;
    private Anemometer anemometer;

    private StringBuffer sb = new StringBuffer();
    private Pattern pattern = Pattern.compile("[a-zA-Z]+");

    public MetaDataInfo getMetaDataInfo(String xmlContent) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlReader = parser.getXMLReader();
        xmlReader.setContentHandler(this);
        xmlReader.setErrorHandler(this);
        xmlReader.parse(new InputSource(new StringReader(xmlContent)));

        return metaDataInfo;
    }

    public MetaDataInfo getMetaDataInfo(File fileName) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlReader = parser.getXMLReader();
        xmlReader.setContentHandler(this);
        xmlReader.setErrorHandler(this);
        xmlReader.parse(new InputSource(new FileReader(fileName)));

        return metaDataInfo;
    }

    @Override
    public void startDocument() throws SAXException {
        metaDataInfo = new MetaDataInfo();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {;
        if("土壤1".equals(qName)) {
            soilSensorInfo1 = new SoilSensorInfo();
            objTag = qName;
        } else if("土壤2".equals(qName)) {
            soilSensorInfo2 = new SoilSensorInfo();
            objTag = qName;
        } else if("土壤3".equals(qName)) {
            soilSensorInfo3 = new SoilSensorInfo();
            objTag = qName;
        } else if("雨量筒".equals(qName)) {
            rainGauge = new RainGauge();
            objTag = qName;
        } else if("高精度红外温度探测仪".equals(qName)) {
            infraredSensor = new InfraredSensor();
            objTag = qName;
        } else if("温湿度".equals(qName)) {
            airSensor = new AirSensor();
            objTag = qName;
        } else if("风速风向".equals(qName)) {
            anemometer = new Anemometer();
            objTag = qName;
        }
        sb.setLength(0);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String content = sb.toString();
        if("设备编号".equals(qName)) {
            metaDataInfo.setSensorID(Integer.valueOf(content));
        } else if("安装位置".equals(qName)) {
            metaDataInfo.setInstallLocation(content);
        } else if("东经".equals(qName)) {
            metaDataInfo.setEastLongitude(content);
        } else if("北纬".equals(qName)) {
            metaDataInfo.setNorthLatitude(content);
        } else if("海拔".equals(qName)) {
            metaDataInfo.setAltitude(Double.valueOf(pattern.matcher(content).replaceAll("")));
        } else if("IPv6地址".equals(qName)) {
            metaDataInfo.setIpv6_address(qName);
        } else if("序列号".equals(qName)) {
            if("土壤1".equals(objTag)) {
                soilSensorInfo1.setSerialNum(content);
            } else if("土壤2".equals(objTag)) {
                soilSensorInfo2.setSerialNum(content);
            } else if("土壤3".equals(objTag)) {
                soilSensorInfo3.setSerialNum(content);
            } else if("雨量筒".equals(objTag)) {
                rainGauge.setSerialNum(content);
            } else if("高精度红外温度探测仪".equals(objTag)) {
                infraredSensor.setSerialNum(content);
            } else if("温湿度".equals(objTag)) {
                airSensor.setSerialNum(content);
            } else if("风速风向".equals(objTag)) {
                anemometer.setSerialNum(content);
            }
        } else if("掩埋深度".equals(qName)) {
            double depth = Double.valueOf(pattern.matcher(content).replaceAll(""));
            if("土壤1".equals(objTag)) {
                soilSensorInfo1.setBuriedDepth(depth);
            } else if("土壤2".equals(objTag)) {
                soilSensorInfo2.setBuriedDepth(depth);
            } else if("土壤3".equals(objTag)) {
                soilSensorInfo3.setBuriedDepth(depth);
            }
        } else if("安装高度".equals(qName)) {
            double height = Double.valueOf(pattern.matcher(content).replaceAll(""));
            if("雨量筒".equals(objTag)) {
                rainGauge.setInstallHeight(height);
            } else if("高精度红外温度探测仪".equals(objTag)) {
                infraredSensor.setInstallHeight(height);
            } else if("温湿度".equals(objTag)) {
                airSensor.setInstallHeight(height);
            } else if("风速风向".equals(objTag)) {
                anemometer.setInstallHeight(height);
            }
        } else if("m-c2".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setM_c2(para);
        } else if("m-c1".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setM_c1(para);
        } else if("m-c0".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setM_c0(para);
        } else if("b-c2".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setB_c2(para);
        } else if("b-c1".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setB_c1(para);
        } else if("b-c0".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setB_c0(para);
        } else if("mSB-c2".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setmSB_c2(para);
        } else if("mSB-c1".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setmSB_c1(para);
        } else if("mSB-c0".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setmSB_c0(para);
        } else if("bSB-c2".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setbSB_c2(para);
        } else if("bSB-c1".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setbSB_c1(para);
        } else if("bSB-c0".equals(qName)) {
            double para = Double.valueOf(content);
            infraredSensor.setbSB_c0(para);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);

    }
}
