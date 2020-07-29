package horseracing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//�� ���� Ŭ���̾�Ʈ�� ����ϵ��� ���ִ� Ŭ���̾�Ʈ Ŭ����
public class Client {
  String Id;
  Socket socket;
  public static int start; //  Ŭ���̾�Ʈ ��
  public static int startcheck=0; // Ŭ���̾�Ʈ���� �غ� �Ǻ� ����, Ŭ���̾�Ʈ ���� ������ ��ȣ�� ���� ������ ���۽�Ų��.
  public static long rand=0; // ���� �Լ� ���尪
  
  public Client(Socket socket)
  {
	  this.socket = socket;
	  this.start = GameServer.clients.size();
	  
	  receive();
  }
  
//�ݺ������� Ŭ���̾�Ʈ�κ��� �޽����� �޴� �޼ҵ�
public void receive() {
	  Runnable thread = new Runnable() {
		
		@Override
		public void run() {
			boolean bl=true;
			try
			{
				while(bl) {
					start = GameServer.clients.size(); //�ڷḦ ���� ������ Ŭ���̾�Ʈ �� Ȯ��
					System.out.println("Ŭ���̾�Ʈ �� : "+GameServer.clients.size());
					InputStream in = socket.getInputStream();
					byte[] buffer = new byte[512];
					
					int length = in.read(buffer);
					if(length==-1) throw new IOException();
					System.out.println("[�޽��� ���� ����]"
							+socket.getRemoteSocketAddress()
							+": "+Thread.currentThread().getName());
					String message = new String(buffer,0,length,"UTF-8");
					if(message.contains(":")==false) startcheck++; // ä���� �ƴ϶��(�غ� ��ư�� �����ٸ�) üũ ������ 1������Ų��.
					System.out.println("��ŸƮ üũ"+startcheck);
					rand = (long)(Math.random()*1000000); //���� ����
					
					for(int i=0; i<GameServer.clients.size(); i++)
					{
						GameServer.clients.get(i).send(message);

					}
					/*
					for(Client client : GameServer.clients) {
						client.send(message); //��� Ŭ���̾�Ʈ�� �޽��� ����
					}
					*/
					
				}
				
			}
			catch(Exception e) {
			try {
				System.out.println("[�޽��� ���� ����]"
						+socket.getRemoteSocketAddress()
				+": "+Thread.currentThread().getName());
			GameServer.clients.remove(Client.this);
			socket.close();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			}
			
		}//run
	};
	GameServer.threadPool.submit(thread);
  }
  
  // �ش� Ŭ���̾�Ʈ���� �޽����� �����ϴ� �޼ҵ�
  public void send(String message)
  {
	  Runnable thread = new Runnable() {
		
		@Override
		public void run() {
try {

	OutputStream out = socket.getOutputStream();
	System.out.println("��ŸƮ "+start);
	System.out.println("��ŸƮüũ "+startcheck);
	if(message.contains(":")) // ���� ������ ä���̸�
	{
		System.out.println("ä�ü���");
		byte[] buffer = message.getBytes("UTF-8");
		out.write(buffer);
		out.flush();
		
	}
	
	
	if(startcheck==start) // ��� �÷��̾���� �غ� �Ͽ��ٸ�
	{
		System.out.println("�׽���");
		byte[] buffer = new String(".s."+rand).getBytes("UTF-8"); //.s.�� ä�� �޽����� ������ ���´�.
		out.write(buffer);
		out.flush();
		startcheck=0;
	}
	else // ���� ��� �÷��̾ �غ� �� ������ ��� .d.�� ������ ���� ������ �������� �ʵ��� �Ѵ�
	{
		System.out.println("�����..");
		byte[] buffer = new String(".d."+rand).getBytes("UTF-8");
		out.write(buffer);
		out.flush();
		
	}
   
	
	
	
}
catch(Exception e) {
	try {
		System.out.println("[�޽��� �۽� ����]"
				+socket.getRemoteSocketAddress()
				+": "+Thread.currentThread().getName());
		GameServer.clients.remove(Client.this);
		socket.close();
		
	}
	catch(Exception e2) {
		e2.printStackTrace();
	}
}
			
		}//run
	};
	
		GameServer.threadPool.submit(thread);	
  }//send
  
  
  
}
