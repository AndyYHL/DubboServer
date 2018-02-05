package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AjaxOutUtil {
	
	private static Logger logger = LogManager.getLogger(AjaxOutUtil.class);
	
	public static void responseText(HttpServletResponse response, Object obj) {
//		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		String json = "";
//		try {
//			json = ow.writeValueAsString(obj);
//		} catch (JsonProcessingException e) {
//			logger.error(e);
//		}
		String json = JSON.toJSONString(obj);
		responseText(response, json);
	}
	
	/**
	 * AJAX输出页面
	 * @param response
	 * @param s
	 */
	public static void responseText(HttpServletResponse response, String s) {
		// 指定内容类型
		response.setContentType("text/html;charset=utf-8");
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			PrintWriter out = response.getWriter();
			out.print(s);
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 返回JSON
	 * @param response
	 * @param obj
	 */
	public static void responseJson(HttpServletResponse response, Object obj) {
		// 指定内容类型
		response.setContentType("application/json;charset=UTF-8");
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			PrintWriter out = response.getWriter();
			out.print(JSON.toJSONString(obj));
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
