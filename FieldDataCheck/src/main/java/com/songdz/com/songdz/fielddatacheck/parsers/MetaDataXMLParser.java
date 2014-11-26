package com.songdz.com.songdz.fielddatacheck.parsers;

import com.songdz.com.songdz.fielddatacheck.data.MetaData;
import com.songdz.com.songdz.fielddatacheck.data.structure.MetaDataMap;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by SongDz on 2014/11/24.
 */
public class MetaDataXMLParser extends DefaultHandler {

    private MetaDataMap metaDataMap = null;

    private MetaData metaData = null;

//    private String preTag;

    private boolean outTag = true;

    private StringBuffer sb = new StringBuffer();

    public MetaDataMap getMetaDataMap(String xmlContent) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlReader = parser.getXMLReader();
        xmlReader.setContentHandler(this);
        xmlReader.setErrorHandler(this);
        xmlReader.parse(new InputSource(new StringReader(xmlContent)));

        return metaDataMap;
    }

    public MetaDataMap getMetaDataMap(File fileName) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlReader = parser.getXMLReader();
        xmlReader.setContentHandler(this);
        xmlReader.setErrorHandler(this);
        xmlReader.parse(new InputSource(new FileReader(fileName)));

        return metaDataMap;
    }

    @Override
    public void startDocument() throws SAXException {
        metaDataMap = MetaDataMap.getInstance();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        sb.setLength(0);
        if("元数据".equals(qName)) {
            outTag = true;
        }  else if("传感器元数据".equals(qName)) {
            metaData = new MetaData();
        }
//System.out.println(preTag);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("更新时间".equals(qName)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date updateTime = simpleDateFormat.parse(sb.toString());
                if (outTag) {
                    outTag = false;
                    metaDataMap.setUpdateTime(updateTime.getTime());
                } else {
                    metaData.setDate(updateTime.getTime());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if("总个数".equals(qName)) {
            metaDataMap.setMetaDataCount(Integer.valueOf(sb.toString()));
        } else if("设备编号".equals(qName)) {
            metaData.setId(Integer.valueOf(sb.toString()));
        } else if("文件名称".equals(qName)) {
            metaData.setFileName(sb.toString());
        } else if("传感器元数据".equals(qName)) {
System.out.println("传感器元数据" + metaData);
            metaDataMap.addMetaData(metaData.getId(), metaData);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);
    }
}
