package dataStruct;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;


public class client{
	Socket client;
	DataOutputStream output;
	DataInputStream input;
	public client() throws UnknownHostException, IOException{
		client = new Socket("localhost", 8080);
		OutputStream outputstream = client.getOutputStream();
		output = new DataOutputStream(outputstream);
		InputStream inputstream = client.getInputStream();
		input = new DataInputStream(inputstream);
	}
//	public void run() {
//		Scanner con_in = new Scanner(System.in);
//		while (true)
//		{
//			System.out.println("上传：0\n下载：1");
//			String ask = con_in.nextLine();
//			int length;
//			try {
//				output.writeUTF(ask);
//				File f = new File("D:\\java\\Client\\" + ask.substring(2));
//				byte [] arr;
//				switch(ask.charAt(0))
//				{
//				case '0':
//					FileInputStream fread = new FileInputStream(f);
//					arr = new byte[(int)f.length()];
//					fread.read(arr);
//					output.writeInt((int)f.length());
////					System.out.println(arr.toString());
//					output.write(arr);
////					System.out.println("finish write");
//					break;
//				case '1':
//					FileOutputStream fwrite = new FileOutputStream(f);
//					length = input.readInt();
//					arr = new byte[length];
//					input.read(arr);
//					fwrite.write(arr);
//					break;
//				}
//				System.out.println(input.readUTF());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//System.out.println("bad");
//		}
//	}
	public boolean upload(String path, String file_name) throws IOException
	{
		output.writeInt(0);
		output.writeUTF(file_name);
		File f = new File(path);
		FileInputStream fread = new FileInputStream(f);
		byte [] arr;
		arr = new byte[(int)f.length()];
		fread.read(arr);
		output.writeInt((int)f.length());
		output.write(arr);
		fread.close();
		return true;
	}
	public Vector<String> get_down_load_list() throws IOException
	{
		output.writeInt(1);
		Vector<String> file_list = new Vector<String>();
		int length = input.readInt();
		String file_name;
		for(int i = 0; i < length; ++i)
		{
			file_name = input.readUTF();
			file_list.add(file_name);
		}
		return file_list;
	}
	public boolean download(String file_name) throws IOException
	{
		if (!file_name.equals("下载列表")) {
			output.writeInt(2);
			String path = "C:\\Users\\24749\\Desktop\\ziliao\\java程序设计\\大作业\\本地仓库\\";
			File f = new File(path + file_name);
			FileOutputStream fwrite = new FileOutputStream(f);
			output.writeUTF(file_name);
			int length = input.readInt();
			byte [] arr = new byte[length];
			input.read(arr);
			fwrite.write(arr);
			fwrite.close();
			
		}
		return true;
		
	}
}
