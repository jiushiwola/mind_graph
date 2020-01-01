
package dataStruct;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

class client_answer extends Thread{
	Socket client;
	DataOutputStream output;
	DataInputStream input;
	Vector<String> file_list;
	String path;
	public client_answer(Socket socket, Vector<String> file) throws IOException
	{
		this.client = socket;
		this.file_list = file;
		this.path = "C:\\Users\\24749\\Desktop\\ziliao\\java程序设计\\大作业\\服务器仓库\\";
		OutputStream outputstream = client.getOutputStream();
		output = new DataOutputStream(outputstream);
		InputStream inputstream = client.getInputStream();
		input = new DataInputStream(inputstream);
	}
	public void run() {
		while (true)
		{
			int command = 4;
			String file_name; 
			int length;
			byte [] arr;
			File f;
			try {
				command = input.readInt();
				switch(command)
				{
				case 0:
					file_name = input.readUTF();
					this.file_list.add(file_name);
					length = input.readInt();
					arr = new byte[length];
					input.read(arr);
					f = new File(path + file_name);
					FileOutputStream fwrite = new FileOutputStream(f);
					fwrite.write(arr);
					fwrite.close();
					break;
				case 1:
					output.writeInt(file_list.size());
					for (int i = 0; i < file_list.size(); ++i)
					{
						output.writeUTF(file_list.get(i));
					}
					break;
				case 2:
					file_name = input.readUTF();
					f = new File(path + file_name);
					FileInputStream fread = new FileInputStream(f);
					length = (int)f.length();
					output.writeInt(length);
					arr = new byte[length];
					fread.read(arr);
					fread.close();
					output.write(arr);
					break;
				default:
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("normal");
//			try {
//				String message = input.readUTF();
//				int length;
//				if (message != null) {
//					File f = new File("D:\\java\\Server\\" + message.substring(2));
//					byte [] arr;
//					switch(message.charAt(0)) {
//					case '0':
////						System.out.println("上传：");
//						message = message.substring(2);
////						System.out.println("上传：" + message);
//						file_list.add(message);
////						System.out.println("D:\\java\\" + message);
//						FileOutputStream fwrite = new FileOutputStream(f);
//						length = input.readInt();
//						arr = new byte[length];
////						System.out.println(length);
//						input.read(arr);
////						System.out.println(arr.toString());
//						fwrite.write(arr);
////						System.out.println("上传成功");
//						output.writeUTF("上传成功");
//						break;
//					case '1':
//						System.out.println("下载：");
//						message = message.substring(2);
//						System.out.println("下载：" + message);
//						if (file_list.contains(message)) 
//						{
//							System.out.println("good");
//							FileInputStream fread = new FileInputStream(f);
//							arr = new byte[(int)f.length()];
//							fread.read(arr);
//							output.writeInt((int)f.length());
//							output.write(arr);
//						}
//						output.writeUTF("下载成功");
//						break;
//					}
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
}

public class server {
	public static void main(String args[]) throws IOException
	{
		ServerSocket server = new ServerSocket(8080);
		System.out.println("server listen at: 8080");
		Vector<String> file_list = new Vector<String>();
		File f = new File("C:\\Users\\24749\\Desktop\\ziliao\\java程序设计\\大作业\\服务器仓库\\");
		File [] file_arr = f.listFiles();
		file_list.add("下载列表");
		for (int i = 0; i < file_arr.length; ++i)
		{
			file_list.add(file_arr[i].getName());
		}
//		for (int i = 0; i < file_list.size(); ++i)
//		{
//			System.out.println(file_list.get(i));
//		}
		while (true)
		{
			Socket new_socket = server.accept(); 
			System.out.println("get connect!");
			new client_answer(new_socket, file_list).start();
			System.out.println("new connect!");
		}
	}
}
