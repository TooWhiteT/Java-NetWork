package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class T {
	private ServerSocket serversocket;
	private Socket socket;
	private ArrayList list = new ArrayList();
	public void go(){
		try {
			serversocket = new ServerSocket(6677);
			System.out.println("等待连接");
			
			new get(list).start();
			while(true){
			socket = serversocket.accept();
			System.out.println("有客户端连接");
			list.add(socket);
			new set(socket).start();
			//new get(socket).start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		T t = new T();
		t.go();

	}

}

//接收数据set线程
class set extends Thread{
	private Socket socket;
	public set(Socket socket){
		this.socket = socket;
	}
	public void run(){
		try{
		InputStream in = socket.getInputStream();
		InputStreamReader ir = new InputStreamReader(in);
		BufferedReader bf = new BufferedReader(ir);
		String b = "";			
			while(true){
				b = bf.readLine();
				if(b == null||"".equals(b))
					break;
				System.out.println("客户端："+b);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

//发送数据get线程
class get extends Thread{
	private Socket socket;
	ArrayList list = null;
	public get(ArrayList list){
		this.list = list;
	}
	public void run(){
		try {               
			Scanner sc = new Scanner(System.in);			
			while(true){
				
				String a = sc.nextLine();
				for(int i = 0;i<list.size();i++){
					socket = (Socket)list.get(i);
					OutputStream o = socket.getOutputStream();
					OutputStreamWriter w = new OutputStreamWriter(o);
					BufferedWriter buff = new BufferedWriter(w);
				if(a==null||"".equals(a))
					break;
				buff.write(a);
				buff.newLine();
				buff.flush();
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}