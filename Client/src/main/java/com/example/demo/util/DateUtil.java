package com.example.demo.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static DateFormat YMDHMS_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
    private static DateFormat YMD_FORMAT = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);

	public static String ymdhmsFormat(Date date) {
		return YMDHMS_FORMAT.format(date);
	}
	public static String ymdFormat(Date date) {
		return YMD_FORMAT.format(date);
	}
	/**
	 * 得到当前系统日期,格式:yyyy-mm-dd hh:mm:ss
	 *
	 * @return
	 */
	public static String getCurrentDate(){
		return getFormatDate("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 返回年份
	 * @return
	 */
	public static String getCurrentYear(){

		return getFormatDate("yyyy");
	}
	/**
	 * 返回月份
	 */
	public static String getCurrentMonth(){
		return getFormatDate("MM");
	}
	/**
	 * 返回天数
	 */
	public static String getCurrentToDay(){
		return getFormatDate("dd");
	}
	/**
	 * 返回特定格式的日期
	 * 格式如下:
	 * yyyy-mm-dd
	 * @param formatString
	 * @return
	 */
	protected static String getFormatDate(String formatString){
		String currentDate="";
		SimpleDateFormat format1 = new SimpleDateFormat(formatString);
		currentDate = format1.format(new Date());
		return currentDate;
	}
	/**
	 * 返回时间格式样式
	 * @return
	 */
	public static String getFormateDateAll(){
		return getFormatDate("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 获取简化时间格式
	 * @return
	 */
	public static String getFormateDateSimple(){
		return getFormatDate("yyyyMMddHHmmss");
	}
	/**
	 * 获取几天后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfterDay(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	/**
	 * 返回几个小时后的时间
	 * @param d
	 * @param hour
	 * @return
	 */
	public static Date getDateAfterHour(Date d,int hour){
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);
		return now.getTime();
	}
	/**
	 * 返回几分钟后的某个时间
	 * @param d
	 * @param minutes
	 * @return
	 */
	public static Date getDateAfterMinute(Date d,int minutes){
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minutes);
		return now.getTime();
	}
	/**
	 * 比较两个日期的大小
	 * true date1比date2前
	 * false date1比date2后
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean dateCompare(Date date1,Date date2){
		boolean dateComPareFlag = true;
		if (date2.compareTo(date1) != 1) {
			dateComPareFlag = false;
		}
		return dateComPareFlag;
	}
	/**
	 * 返回两时间之差
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long dateMinus(Date startTime,Date endTime){
		return endTime.getTime()-startTime.getTime();
	}

	/**
	 * 计算两个string类的时间差
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static String time(String startTime, String endTime) throws ParseException {
		Date outdate = YMDHMS_FORMAT.parse(endTime);
		Date indate =  YMDHMS_FORMAT.parse(startTime);
		long totalhours = (outdate.getTime()-indate.getTime())/(1000*60*60);//时
		long totalminutes = (outdate.getTime()-indate.getTime())/(1000*60)-totalhours*60;//分
		long totalseconds = (outdate.getTime()-indate.getTime())/(1000)-totalminutes*60;//秒
		String total_time = totalhours+"时"+totalminutes+"分"+totalseconds+"秒";
		return total_time;
	}

	/**
	 * 计算考试的剩余时间
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static String examRemainTime(String startTime, String endTime) throws ParseException{
		Date outdate = YMDHMS_FORMAT.parse(endTime);
		Date indate =  YMDHMS_FORMAT.parse(startTime);
		long totalminutes = (outdate.getTime()-indate.getTime())/(1000*60);//分
		long totalseconds = (outdate.getTime()-indate.getTime())/(1000)-totalminutes*60;//秒
		String remainTime = totalminutes+ "#"+ totalseconds;
		return remainTime;
	}
	/**
	 * 将时间转换为时间戳
	 */
	public static String dateToStamp(String s){
		String res;
		try{
			Date date = YMDHMS_FORMAT.parse(s);
			long ts = date.getTime();
			res = String.valueOf(ts);
		}catch (Exception e){
			return getCurrentDate();
		}
		return res;
	}

	/**
	 * 时间戳转时间---- 以时间2017-04-26作为标准
	 * @param s
	 * @return
	 */
	public static String dateToStampDay(String s){
		String res;
		try{
			Date date = YMD_FORMAT.parse(s);
			long ts = date.getTime();
			res = String.valueOf(ts);
		}catch (Exception e){
			return getCurrentDate();
		}
		return res;
	}
	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public static Date strToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s){
		return YMDHMS_FORMAT.format(new Date(new Long(s)));
	}
	/**
	 * 将时间戳转换为时间
	 */
	public static String stampToDateDay(String s){
		return YMD_FORMAT.format(new Date(new Long(s)));
	}

	/**
	 * 字符串转换为java.util.Date<br>
	 * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>
	 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
	 * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
	 * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
	 * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
	 * @param time String 字符串<br>
	 * @return Date 日期<br>
	 */
	public static Date stringToDate(String time){
		SimpleDateFormat formatter;
		int tempPos=time.indexOf("AD") ;
		time=time.trim() ;
		formatter = new SimpleDateFormat ("yyyy.MM.dd G 'at' hh:mm:ss z");
		if(tempPos>-1){
			time=time.substring(0,tempPos)+
					"公元"+time.substring(tempPos+"AD".length());//china
			formatter = new SimpleDateFormat ("yyyy.MM.dd G 'at' hh:mm:ss z");
		}
		tempPos=time.indexOf("-");
		if(tempPos>-1&&(time.indexOf(" ")<0)){
			formatter = new SimpleDateFormat ("yyyyMMddHHmmssZ");
		}
		else if((time.indexOf("/")>-1) &&(time.indexOf(" ")>-1)){
			formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");
		}
		else if((time.indexOf("-")>-1) &&(time.indexOf(" ")>-1)){
			formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		}
		else if((time.indexOf("/")>-1) &&(time.indexOf("am")>-1) ||(time.indexOf("pm")>-1)){
			formatter = new SimpleDateFormat ("yyyy-MM-dd KK:mm:ss a");
		}
		else if((time.indexOf("-")>-1) &&(time.indexOf("am")>-1) ||(time.indexOf("pm")>-1)){
			formatter = new SimpleDateFormat ("yyyy-MM-dd KK:mm:ss a");
		}
		ParsePosition pos = new ParsePosition(0);
		Date ctime = formatter.parse(time, pos);

		return ctime;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(strToDate("20091225091010"));
		System.out.println(dateToStamp("2017-04-26 09:54:47"));
		System.out.println(stampToDate("1493171687000"));
		System.out.println(dateToStampDay("2017-04-26"));
		System.out.println(stampToDateDay("1493136000000"));
		System.out.println(YMD_FORMAT.parse("2017-04-26").toString());
		Timestamp ds = new Timestamp(YMD_FORMAT.parse("2017-04-26").getTime());
		System.out.println(ds.toString());
		Date date = ds;
		System.out.println(YMD_FORMAT.format(date).toString());


		Date now = new Date();
		Calendar cal = Calendar.getInstance();

		DateFormat d1 = DateFormat.getDateInstance(); //默认语言（汉语）下的默认风格（MEDIUM风格，比如：2008-6-16 20:54:53）
		String str1 = d1.format(now);
		DateFormat d2 = DateFormat.getDateTimeInstance();
		String str2 = d2.format(now);
		DateFormat d3 = DateFormat.getTimeInstance();
		String str3 = d3.format(now);
		DateFormat d4 = DateFormat.getInstance(); //使用SHORT风格显示日期和时间
		String str4 = d4.format(now);

		DateFormat d5 = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL); //显示日期，周，时间（精确到秒）
		String str5 = d5.format(now);
		DateFormat d6 = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG); //显示日期。时间（精确到秒）
		String str6 = d6.format(now);
		DateFormat d7 = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT); //显示日期，时间（精确到分）
		String str7 = d7.format(now);
		DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
		String str8 = d8.format(now);//与SHORT风格相比，这种方式最好用





		System.out.println("用Date方式显示时间: " + now);//此方法显示的结果和Calendar.getInstance().getTime()一样


		System.out.println("用DateFormat.getDateInstance()格式化时间后为：" + str1);
		System.out.println("用DateFormat.getDateTimeInstance()格式化时间后为：" + str2);
		System.out.println("用DateFormat.getTimeInstance()格式化时间后为：" + str3);
		System.out.println("用DateFormat.getInstance()格式化时间后为：" + str4);

		System.out.println("用DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL)格式化时间后为：" + str5);
		System.out.println("用DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG)格式化时间后为：" + str6);
		System.out.println("用DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)格式化时间后为：" + str7);
		System.out.println("用DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM)格式化时间后为：" + str8);
	}
}