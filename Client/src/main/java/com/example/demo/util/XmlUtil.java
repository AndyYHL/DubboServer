package com.example.demo.util;


import com.google.common.collect.Maps;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-08-17.
 */
public class XmlUtil {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        String xmlStr = "<xml><charset>UTF-8</charset><device_info></device_info><err_code>01</err_code><err_msg>ok</err_msg><mch_id>13062</mch_id><message>成功</message><nonce_str>1502960140</nonce_str><pay_info>weixin://wxpay/bizpayurl?pr=ikm1oFh</pay_info><result_code>0</result_code><sign>1F3EB6BB3AABDB45A1DB2F09DF438FA0</sign><sign_type>MD5</sign_type><status>0</status><token_id>https://vip.iyibank.com/qrcode?uuid=weixin://wxpay/bizpayurl?pr=ikm1oFh</token_id><version>1.0</version></xml>";
        try {
            InputStream stream = new ByteArrayInputStream(xmlStr.getBytes());
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            readNode(root,"");
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void readNode(Element root, String prefix) {
        if (root == null) return;
        // 获取他的子节点
        List<Element> childNodes = root.elements();
        for (Element element : childNodes){
            System.out.println(element.getName()+"----"+element.getText());
        }
    }

    /**
     * 解析
     * @param xmlStr
     * @return
     */
    public static Map readXml(String xmlStr){
        Map map = Maps.newHashMap();
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        try {
            InputStream stream = new ByteArrayInputStream(xmlStr.getBytes());
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            if(root == null) return map;
            // 获取他的子节点
            List<Element> childNodes = root.elements();
            for (Element element : childNodes){
                System.out.println(element.getName()+"----"+element.getText());
                map.put(element.getName(),element.getText());
            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  map;
    }
}
