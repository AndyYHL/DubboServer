package com.example.demo.util;

import com.example.demo.state.FinalJson;
import com.example.demo.util.json.Info;
import com.example.demo.util.json.JsonUtil;
import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017-06-28.
 */
public class CheckCodeUtil {
    /**
     * 比对CheckCode是否正确
     * @param jsonUtil
     * @return
     */
    public static JsonUtil globalCheckCode(JsonUtil jsonUtil) throws Exception{
        Map map = (Map)jsonUtil.getData();
        if(getCheckCode(map).equals(jsonUtil.getInfo().getCheckcode())){
            jsonUtil.getInfo().setStatus(FinalJson.STATUS_CHECKCODE);
        }else{
            jsonUtil.getInfo().setStatus(FinalJson.STATUS_INVALIDREQUEST);
            jsonUtil.getInfo().setMessage("CheckCode检验不正确");
        }
        return jsonUtil;
    }
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
            String encryptStr = a + "key=" +FinalJson.private_key;
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
        JsonUtil jsonUtil = new JsonUtil();
        Map dataMap = Maps.newHashMap();
        dataMap.put("kfc", "kfc");
        dataMap.put("wnba", "wnba");
        dataMap.put("nba", "nba");
        dataMap.put("cba", "cba");
        jsonUtil.setData(dataMap);
        String a = getCheckCode(dataMap); //生成
        Info info = new Info();
        info.setCheckcode(a);
        jsonUtil.setInfo(info);
        System.out.println("CheckCode:"+a);
        //进行比对
        System.out.println(globalCheckCode(jsonUtil).getInfo().getMessage());

    }
}
