package horseracing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller implements Initializable{
	private Parent root;
	private Label lb1; // �� 1~5 = �� 5����
	private Label lb2;
	private Label lb3;
	private Label lb4;
	private Label lb5;
	private Label money; // ���ӿ� ���������� �ݾ��� ����� ��
    private TextArea ta; // ���� ������ ǥ���� ����
	private int sw=1; // ���� ���� ����ġ 1�̸� �����Ѵ�
	private int win=1; // 1��� ���ϱ� ���� ���� Ư�� ���� ������� ����ϸ� 0�� �ȴ�
	private int winner; // �̱� ���� ��ȣ�� �����ϱ� ���� ����
	private ArrayList<horse> arr; // 5���� ������ ���� �÷��� ��ü
	private user player1; // �÷��̾� ��ü
	Socket socket; // �ش� Ŭ���̾�Ʈ�� ���� 
	public static long randn;
	public static String ff;


	
	
	public void setRoot(Parent root,Socket socket) throws ClassNotFoundException, SQLException
	{
		user player1 = new user();
		this.root = root;
		this.socket = socket;
		lb1 = (Label)root.lookup("#horse1");
		lb2 = (Label)root.lookup("#horse2");
		lb3 = (Label)root.lookup("#horse3");
		lb4 = (Label)root.lookup("#horse4");
		lb5 = (Label)root.lookup("#horse5");
		ta =  (TextArea)root.lookup("#info");
		money =  (Label)root.lookup("#money");
		money.setText(player1.getID()+":"+(player1).getMoney()+"��");
		arr = new ArrayList<horse>();
		arr.add(new horse(5.53,3)); arr.add(new horse(5.52,4)); arr.add(new horse(5.55,1.5));
		arr.add(new horse(5,5)); arr.add(new horse(5.54,2));
		// ������ �ʱ�ȭ�� �տ��� �ӵ� �ڿ��� ����
	}
	

	 public void auto() { //��� ���� �޼ҵ�
		 winner=0; win=1;
		 int i=0; Random rand = new Random(randn);
		 ta.appendText("���ְ� ���۵˴ϴ� !\n");
	      Thread th = new Thread() { // �͸� ��ü
	         @Override
	         public void run() { // �����忡�� �� ��
	            // ������� ���� ���� �߻�(FX ��� �����常 �� ��) 
	        	 try {
					player1 = new user();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        while(sw==1) {
	            	
	               // UI �浹�� ����Ǵ� ���� 
	               Platform.runLater(new Runnable() { // ���ٽ����ε� ����� �����ϴ�. 
	             	
	            	   double x = lb1.getLayoutX();
	             	   double x2 = lb2.getLayoutX();
	             	  double x3 = lb3.getLayoutX();
	             	 double x4 = lb4.getLayoutX();
	             	double x5 = lb5.getLayoutX();
	             // ������ ���� x��ǥ�� �����´�
	            	   @Override
	                  public void run() {
	            		  
	            		   // randn�� ��ȭ�� ���� ������ �������� ��ȯ�Ѵ�.
	            		    
	            		    ff = ff.valueOf(rand.nextDouble());
	            		    
	            		    double f = Integer.parseInt(ff.substring(2, 3))/2.0;
	            			   if(f<0) f=-f; // f�� -�� ���
	            			  
	            			 
		            		   double f2 = Integer.parseInt(ff.substring(3, 4))/2.0;
		            		   if(f<0) f2=-f2;
		            		   
		            		   double f3 = Integer.parseInt(ff.substring(4, 5))/2.0;
		            		   if(f<0) f3=-f3;
		            		   
		            		   double f4 = Integer.parseInt(ff.substring(5, 6))/2.0;
		            		   if(f<0) f4=-f4;
		            		   
		            		   double f5 = Integer.parseInt(ff.substring(6, 7))/2.0;
		            		   if(f<0) f5=-f5;
	            		   
	            		   
	 		            	x += f+arr.get(0).getSpeed()/20;
	 		            	x2 += f2+arr.get(1).getSpeed()/20;
	 		            	x3 += f3+arr.get(2).getSpeed()/20;
	 		            	x4 += f4+arr.get(3).getSpeed()/20;
	 		            	x5 += f5+arr.get(4).getSpeed()/20;
	 		                 // �������� �ӵ��� �����Ͽ� �̵��� �Ÿ��� ����
	 		            	
	 		            	
	 		            	
	 		            	lb1.setLayoutX(x);
	 		            	lb2.setLayoutX(x2);
	 		            	lb3.setLayoutX(x3);
	 		            	lb4.setLayoutX(x4);
	 		            	lb5.setLayoutX(x5);
	 		                // �̵���Ŵ
	 		            	
	 		            	if(win==1)  // ��¼��� ����� ��� ���ڸ��� ����
	 		            	{
	 		            		
	 		            		if(x>915)
	 		            		{
	 		            			winner = 1;
	 		            			
	 		            			win=0;
	 		            		}
	 		            		else if(x2>915)
	 		            		{
	 		            			winner=2;
	 		            			
	 		            			win=0;
	 		            		}
	 		            		else if(x3>915)
	 		            		{
	 		            			winner=3;
	 		            			
	 		            			win=0;
	 		            		}
	 		            		else if(x4>915)
	 		            		{
	 		            			winner=4;
	 		            			
	 		            			win=0;
	 		            		}
	 		            		else if(x5>915)
	 		            		{
	 		            			winner=5;
	 		            	
	 		            			win=0;
	 		            		}
	 		            		
	 		            		
	 		            		
	 		            		
	 		            		
	 		            	}
	 		            	
	 		            	// �Ѹ����� ȭ�鿡�� ����� ������ ������ �����
	 		            	if(x > 1000 || x2 >1000 || x3 >1000 ||x4 >1000 ||x5 >1000 )
	 		            	{
	 		            		ta.appendText("���ֳ�!\n");
	 		            		ta.appendText("���ڴ� "+winner+"�� �Դϴ�\n");

	 		                   if(player1.getHorse()==winner)
	 		            		{
	 		          try {
						player1.setMoney( player1.getMoney()-player1.getBettingmoney()
								  +(int)(player1.getBettingmoney()*arr.get(winner-1).getDrate()));
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 		         ta.appendText("��÷�Ǿ����ϴ�!\n");
	 		        try {
						ta.appendText(player1.getMoney()+"���� �Ǿ����ϴ�!\n");
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 		        
	 		            		}
	 		            		
	 		            		else
	 		            		{
	 		            			try {
										player1.setMoney(player1.getMoney()-player1.getBettingmoney());
									} catch (ClassNotFoundException | SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
	 		            			ta.appendText(player1.getBettingmoney()+"���� �Ҿ����ϴ�!\n");
	
	 		            		}
	 		                  try {
								money.setText("player1 : "+player1.getMoney()+"��");
							} catch (ClassNotFoundException | SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
	 		                   try {
								ta.appendText("�ڳ��� ���� "+player1.getMoney()+"�� �Դϴ�.\n");
							} catch (ClassNotFoundException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	 		                 
	 		                    sw = 0;
	 		                    try {
									Thread.sleep(2500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	 		                    // �Ѱ����� ���� �� ���� ���ġ
	 		                   lb1.setLayoutX(23); lb1.setLayoutY(11);
	 		                	lb2.setLayoutX(23); lb2.setLayoutY(77);
	 		                	lb3.setLayoutX(23); lb3.setLayoutY(142);
	 		                	lb4.setLayoutX(23); lb4.setLayoutY(205);
	 		                	lb5.setLayoutX(23); lb5.setLayoutY(270);
	 		                	Button btn = 
	 		           				(Button)root.lookup("#startbtn");
	 		           			btn.setDisable(true); //������ ���� �� �غ� ��ư�� ��Ȱ��ȭ��
	 		           			
	 		           			try {
									if(player1.getMoney()<1000) // ���� ���� 1000�� �Ʒ��� �ڵ����� ���� ����
									{
									Alert alert = new Alert(Alert.AlertType.WARNING);
									alert.setContentText("�Ļ� !");
									alert.show();
       	try {
									exitProc();
} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
}
									}
								} catch (ClassNotFoundException | SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	 		           		
	 		                }
	 		          
	 		           randn++; // ���尪�� �����Ͽ� �������� �����Ѵ�
	                  }
	            	  
	               });
	               //Platform.runLater(()->{print(percent);}); // ���ٽ����ε� ����� �����ϴ�. 
	               try {
	                  Thread.sleep(20);
	               } catch (InterruptedException e) {
	                  e.printStackTrace();
	               }
	          
	            }//while ������ 
	         } 
	      };
	      th.start();
	   }//auto
	
	 public void startProc(long rand,int start) // �غ� ��ư
	 {
		 this.randn = rand; // ����(Client.java)���� ���� �������� �õ尪���� �Ͽ� ��� Ŭ���̾�Ʈ�� ����� �������� ������ �Ѵ�. 
		 
			
			 if(bettingController.ready == 1 && start==1) // ������ �߰� ��� �÷��̾ �غ� �ߴٸ� ������ �Ѵ�.
			 {
				 
				 sw = 1;
				 auto();
			 }
			
		 
		 
		 
	 }
	 
	 public void bettingProc() throws IOException // ���� ��ư ����
, ClassNotFoundException, SQLException
	 {   user player1 = new user();
	 
		 Stage stage = new Stage();
			FXMLLoader loader = 
					new FXMLLoader(getClass().getResource("betting.fxml"));
	
	    	Parent root = loader.load();
	    	bettingController ctrler2 = loader.getController();
		    ctrler2.setRoot(root,player1,this.root);
			stage.setTitle("����");
			stage.setScene(new Scene(root));
			stage.show();
	 }

	 public void exitProc() throws IOException // ������ ��ư
	 {
		 this.socket.close();
		 Stage stage = (Stage) root.getScene().getWindow();
	    	stage.close();
	    	
	 }
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}
