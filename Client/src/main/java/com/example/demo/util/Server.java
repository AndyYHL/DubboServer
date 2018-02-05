package com.example.demo.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	public static ServerSocket server;
	public final static int port = 10001;
	public static void main(String[] args) throws IOException {
		
		server = new ServerSocket(port);
		System.out.println("--- Server已经启动 ---");
		
		new Thread(){
			public void run() {
				try {
					while(true){
						Socket client = server.accept();
						new Thread(new SocketAction(client)).start();
						System.out.println("--- Client连接成功！ ---");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		new Thread(){
			public void run() {
				while (true) {

					try {
						Thread.sleep(10000);
						System.out.println("当前 传输件数：" + SocketAction.count + " 系统时间：" + new Date(System.currentTimeMillis()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			};
		}.start();
	}
}

class SocketAction implements Runnable{
	Socket client;
	public static long count;
	public static long errorCount;
	public static long leapCount;
	public final static int HEAD_LENGTH = 12;
	File file = new File("D:\\socketSendTime.txt");
	public SocketAction(Socket client) {
		this.client = client;
	}
	
	public void run() {
		SocketMessageUtil msgUtil = new SocketMessageUtil();
		while(true) {
			try {
				InputStream in = client.getInputStream();
				if(in.available() > HEAD_LENGTH){
					byte[] header = new byte[HEAD_LENGTH];
					in.read(header, 0, header.length);
					char msgType = msgUtil.bytesTochar(header[6], header[7]);
					int msgBodyLength = msgUtil.bytesTochar(header[8], header[9]);
					char wMsgSeq = msgUtil.bytesTochar(header[10], header[11]);
//					System.out.print("seq ：\t"+(int)wMsgSeq+"\t");
					
					while (in.available() < msgBodyLength) {
						System.out.println("WAIT 1MS!!!");
						Thread.sleep(1);
					}
					
					if (msgType == SocketMessageUtil.SEND_MSG) {
						
						byte[] body = new byte[msgBodyLength];
						in.read(body, 0, msgBodyLength);
						System.out.println("接收：\t"+msgUtil.getMsgBody(body));
						msgUtil.setwMsgType(SocketMessageUtil.SEND_RES);
						msgUtil.setwMsgSeq(wMsgSeq);
						OutputStream os = client.getOutputStream();
						os.write(msgUtil.createMsg(SocketMessageUtil.SEND_RES));
						os.flush();
						
						count++;
//						if (count % 10000 == 0) {
//							FileWriter writer = null;
//							PrintWriter out = null;
//							try {
//								writer = new FileWriter(file, true);
//								out = new PrintWriter(writer); 
//								out.println("当前 传输件数：" + count + "系统时间：" + System.currentTimeMillis());
//								out.flush();
//							} catch (IOException e) {
//								e.printStackTrace();
//								out.close();
//							}
//							System.out.println("当前 传输件数：" + count + "系统时间：" + new Date(System.currentTimeMillis()));
//						}
					} else if (msgType == SocketMessageUtil.LEAP_MSG) {
						msgUtil.setwMsgType(SocketMessageUtil.LEAP_RES);
						msgUtil.setwMsgSeq(wMsgSeq);
						OutputStream os = client.getOutputStream();
						os.write(msgUtil.createMsg(SocketMessageUtil.LEAP_RES));
						os.flush();
						leapCount++;
						System.out.println("--- 心跳测试 ---" + System.currentTimeMillis() + " count:" + leapCount);
					} else if (msgType == SocketMessageUtil.CONNECT_CREATE_RES_MSG) {
						byte[] body = new byte[msgBodyLength];
						in.read(body, 0, msgBodyLength);
						System.out.println("接收：\t" + msgBodyLength);
						System.out.println("--- itdp 准备数据传输！ ---");
					} else {
						errorCount++;
						if (errorCount % 1000 == 0) {
							System.out.println("error msgType:" + msgType);
						}
					}
				} else {
					Thread.sleep(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}


