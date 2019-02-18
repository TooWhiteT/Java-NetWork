package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Z {
	private Socket socket;
	public void Client(){
		try {
			socket = new Socket("localhost",6677);
			System.out.println("客户端连接成功");
			new set(socket).start();
			new get(socket).start();
		} catch (Exception e) {
			System.out.println("客户端连接失败");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Z c = new Z();
		c.Client();
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
				System.out.println("服务端："+b);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
//发送数据get线程
class get extends Thread{
	private Socket socket;
	public get(Socket socket){
		this.socket = socket;
	}
	public void run(){
		try {
			OutputStream o = socket.getOutputStream();
			OutputStreamWriter w = new OutputStreamWriter(o);
			BufferedWriter buff = new BufferedWriter(w);
			Scanner sc = new Scanner(System.in);			
			while(true){
				String a = sc.nextLine();
				if(a==null||"".equals(a))
					break;
				buff.write(a);
				buff.newLine();
				buff.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}