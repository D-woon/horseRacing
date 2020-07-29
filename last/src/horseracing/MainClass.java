package horseracing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class MainClass extends Application{
	Socket socket;
	Controller ctrler;
	TextArea textArea;
	LoginInfo info;
	
	// Ŭ���̾�Ʈ ���α׷��� �۵��� �����ϴ� �޼ҵ�
		public void startClient(String IP,int port)
		{
			Thread thread = new Thread() {
				public void run() {
					try {
					socket = new Socket(IP,port);
					receive();
					}
					catch(Exception e) {
						if(!socket.isClosed()) {
							stopClient();
							System.out.println("[���� ���� ����]");
							Platform.exit();
						}
					}
				}
			};
			thread.start();
		}
		
		//Ŭ���̾�Ʈ ���α׷��� �۵��� �����ϴ� �޼ҵ�
		public void stopClient()
		{
			try {
			if(socket != null && !socket.isClosed())
				socket.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		//�����κ��� �޽����� ���޹޴� �޼ҵ�
		public void receive()
		{
		  while(true) {
			  try {
		InputStream in = socket.getInputStream();
		byte[] buffer = new byte[512];
		int length = in.read(buffer);
		if(length==-1)throw new IOException();
		String message = new String(buffer,0,length,"UTF-8");
		System.out.println(message);
		Platform.runLater(()->{ // .s.�� ���Ե� �޽����� �����κ��� ������ ������ �����Ѵ�.
			if(message.contains(".s."))
			{
			  String randd = message.substring(3);
			  long rand = Long.parseLong(randd);
				ctrler.startProc(rand,1); // 1�� ���� ���� �����Ѵ�
			}
			else if(message.contains(":")) // :�� ���Ե� �޽����� �����κ��� ������ �޽����� ä��â�� �Էµȴ�.
			{
				textArea.appendText(message);
			}
			else 
			{
				String randd = message.substring(3);
				  long rand = Long.parseLong(randd);
					ctrler.startProc(rand,0); //0�� ���� ����Ѵ�.
			}
			
		});
			  }
			  catch(Exception e) {
				  stopClient();
				  break;
			  }
		  }
		}
		
		//������ �޽����� �����ϴ� �޼ҵ�
		public void send(String message)
		{
			Thread thread = new Thread() {
				public void run()
				{
					try {
						OutputStream out = socket.getOutputStream();
						byte[] buffer = message.getBytes("UTF-8");
						out.write(buffer);
						out.flush();
						}
						catch(Exception e) {
							stopClient();
						}
				}
				
			};
		thread.start();
			
		
		}
		
		
	public void start(Stage primaryStage, LoginInfo info) throws IOException 
	{	
		
		startClient("192.168.0.9",9875); //������ ���� �����ǿ� ��Ʈ��ȣ �Է�
		FXMLLoader loader = 
				new FXMLLoader(getClass().getResource("horse.fxml"));
		Parent root = loader.load();
		
		//�غ� ��ư Ŭ���� ������ ����
		Button btn = 
				(Button)root.lookup("#startbtn");
		btn.setOnAction(event->{
			btn.setDisable(true);
			textArea.appendText(info.getLoginid()+"�� �غ� �Ϸ�. �ٸ� �÷��̾ ��ٸ��ϴ�\n");
			send("z");
		});
		
		//������ ��ư Ŭ���� ������ ����
				Button btn2 = 
						(Button)root.lookup("#exit");
				btn2.setOnAction(event->{
					System.out.println("exit");
					send("exit");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						this.socket.close();
						ctrler.exitProc();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
		
		
		//ä��â�� ���� �Է��ϰ� ���͸� ������
		TextField input = (TextField)root.lookup("#chat");
		input.setOnAction(event->{
			send(info.getLoginid()+" : "+input.getText()+"\n");
			input.setText("");
			input.requestFocus();
		});
		textArea = (TextArea)root.lookup("#chatview");
		
		Scene scene = new Scene(root);
		ctrler = loader.getController();
		ctrler.setRoot(root,this.socket, info);
		primaryStage.setTitle("�渶����");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	// ���α׷��� ������
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
