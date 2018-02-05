package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.json.JsonUtil;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017-06-28.
 */
public class CheckCodeUtil12 {
    public static final String private_key = "WHOXLL3HCIPY4AE2WJBLRP87E70NT7XF"; //私密钥匙
    /**
     * 获取APP端校验值
     * @return
     */
    public static String getCheckCode(Map map){
        Map<String,Object> sMap =  sortMapByKeyObject(map);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : sMap.entrySet()) {
            sb.append(entry.getKey()+"="+String.valueOf(entry.getValue())+"&");
        }
        //String a = sb.deleteCharAt(sb.length()-1).toString();
        String a = sb.toString();
        //对排序的数据进行加密
        try {
            String encryptStr = a + "key=" +private_key;
            String acecssToken = EncryptCoder.MD5Encrypt(encryptStr).toUpperCase();
            return  acecssToken;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKeyObject(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }

    public static void main(String[] args) throws Exception {
        String a ="{\n" +
                "    \"data\":{\n" +
                "        \"uid\":24,\n" +
                "        \"pay_type\":\"2\",\n" +
                "        \"id\":66,\n" +
                "        \"timestamp\":\"1234521451123\"\n" +
                "    },\n" +
                "    \"info\":{\n" +
                "        \"checkcode\":\"11E1C4754BCC67B7AD36237960072063\",\n" +
                "        \"timestamp\":\"1234521451123\",\n" +
                "        \"usource\":\"3\"\n" +
                "    }\n" +
                "}";
        JsonUtil jsonUtil = JSON.parseObject(a,JsonUtil.class);
        String a1 = getCheckCode((Map) jsonUtil.getData()); //生成
        jsonUtil.getInfo().setCheckcode(a1);
        System.out.println("JSON:"+JSON.toJSONString(jsonUtil));
        System.out.println("CheckCode:"+a1);
    }
}
