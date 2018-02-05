package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Created by Administrator on 2017-11-01.
 */
public class ChatServerUtil {
    boolean started = false;
    ServerSocket ss = null;
    Map<String,Client> clients = Maps.newHashMap();
    int port = 5001;

    public static void main(String[] args) {
        new ChatServerUtil().start();
    }

    public void start() {
        try {
            ss = new ServerSocket(port);
            started = true;
            System.out.println("端口已开启,占用"+port+"端口号....");
        } catch (BindException e) {
            System.out.println("端口使用中....");
            System.out.println("请关掉相关程序并重新运行服务器！");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (started) {
                Socket s = ss.accept();

                Client c = new Client(s);
                System.out.println("a client connected!");
                new Thread(c).start();
                clients.put(s.getInetAddress().toString(),c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable {
        private Socket s;
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private boolean bConnected = false;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String str) {
            //发送中转
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                clients.remove(s.getInetAddress().toString());
                System.out.println("重新链接"+s.getInetAddress().toString());
            }
        }

        public void run() {
            try {
                Map sjson = Maps.newHashMap();
                sjson.put("wMagic",0x5AA5);
                sjson.put("wPad",0x0000);
                sjson.put("wVer",0x0001);
                sjson.put("wMsgType","BYTE");
                sjson.put("wMsgLen",8);
                sjson.put("wMsgSeq",0x2001);
                send(JSON.toJSONString(sjson));
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println("------------来自"+s.getInetAddress().toString()+"服务器:" + str);
                    for (String k: clients.keySet()) {
                        Client c = clients.get(k);
                        c.send(str);
                    }
                }
            } catch (EOFException e) {
                System.out.println("Client closed!监听异常"+e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (dis != null)
                        dis.close();
                    if (dos != null)
                        dos.close();
                    if (s != null) {
                        s.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
