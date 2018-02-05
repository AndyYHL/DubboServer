package com.example.demo.util;

import java.io.UnsupportedEncodingException;

public class SocketMessageUtil {

	// 序列
//	private static char sequence=0x0000;
	// 建链应答消息
	public final static char CONNECT_CREATE_RES_MSG = 0x2001;
	// 心跳消息
	public final static char LEAP_MSG = 0x040D;
	// 心跳响应
	public final static char LEAP_RES = 0x040E;
	// 车辆通行记录上传消息
	public final static char SEND_MSG = 0x0001;
	// 车辆通行记录上传响应
	public final static char SEND_RES = 0x0002;
	
	/** 消息头属性  **/
	// 魔术字
	private char wMagic = 0x5AA5;
	
	// 保留字
	private char wPad = 0x0000;
	
	// 协议版本号，当前版本为0x0001
	private char wVer = 0x0001;
	
	// 消息类型
	private char wMsgType;
	
	public char getwMsgType() {
		return wMsgType;
	}

	public void setwMsgType(char wMsgType) {
		this.wMsgType = wMsgType;
	}

	private char wMsgSeq;
	

	public char getwMsgSeq() {
		return wMsgSeq;
	}

	public void setwMsgSeq(char wMsgSeq) {
		this.wMsgSeq = wMsgSeq;
	}

	/** 消息头  **/
	private byte[] wMsgHeader;
	
	/** 消息体  **/
	private byte[] wMsgBody = new byte[0];
	
	/** 消息  **/
	private byte[] wMsg;
	
	public byte[] createMsg(char msgType) throws UnsupportedEncodingException {
		this.wMsgType = msgType;
		if (this.wMsgType == SEND_RES) {
			createSendMsgBody();
		}
		createHeader();
		combineMessage();
		return wMsg;
	}
	
	public char bytesTochar(byte high, byte low) {
		return (char) ((high << 8) + (0x00ff & low));
	}
	
	public String getMsgBody(byte[] bytes) {
		String s;
		try {
			s = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			s = new String(bytes);
		}
		return s;
	}
	
	private void createHeader() {
		char[] charheader = new char[6];
		charheader[0] = this.wMagic;
		charheader[1] = this.wPad;
		charheader[2] = this.wVer;
		charheader[3] = this.wMsgType;
		charheader[4] = (char) (this.wMsgBody.length & 0xffff);
		charheader[5] = this.wMsgSeq;
		wMsgHeader = charsToBytes(charheader);
	}
	
	private byte[] charsToBytes(char[] chars) {
		byte[] bytes = new byte[chars.length * 2];
		for (int i=0; i<chars.length; i++) {
			bytes[2*i] = (byte) (chars[i] >> 8);
			bytes[2*i + 1] = (byte) (chars[i] & 0x00ff);
		}
		return bytes;
	}
	
	private byte[] createSendMsgBody() {
		char[] chars = new char[2];
		chars[0] = 0x0000;
		chars[1] = 0x0000;
		this.wMsgBody = charsToBytes(chars);
		return wMsgBody;
	}
	
//	private byte[] createConnectMsgBody() {
//		char[] chars = new char[2];
//		chars[0] = 0xffff;
//		chars[1] = 0x0000;
//		this.wMsgBody = charsToBytes(chars);
//		return wMsgBody;
//	}
	
	private void combineMessage() {
		wMsg = new byte[wMsgHeader.length + wMsgBody.length];
		for (int i=0; i<wMsgHeader.length; i++) {
			wMsg[i] = wMsgHeader[i];
		}
		int j = 0;
		for (int i=wMsgHeader.length; i<(wMsgHeader.length + wMsgBody.length); i++) {
			wMsg[i] = wMsgBody[j];
			j++;
		}
	}
	
//	private char getSequence() {
//		if (sequence == 0xffff) {
//			sequence = 0x0000;
//		}
//		sequence = (char) (sequence + 0x0001);
//		return sequence;
//	}
}
