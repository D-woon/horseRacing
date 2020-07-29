package horseracing;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class bettingController implements Initializable{
	private Parent root;
	private Parent parentroot;
	private user player; // �÷��̾� ����
	private TextArea ta;
	public static int ready; // ���� ���� �Ǻ� ����

	
	public void setRoot(Parent root,user player,Parent root2) throws ClassNotFoundException, SQLException
	{
		this.root = root;
		this.player = player;
		this.parentroot = root2;
		ta =  (TextArea)root2.lookup("#info");
		ready = 0;
		Label lb = (Label) root.lookup("#pmoney");
		lb.setText("���� �ݾ� : "+player.getMoney());
		AddComboBox();
	}
	
	public void AddComboBox() {
		ComboBox<String> cmb = 
				(ComboBox<String>)root.lookup("#combo");
		if(cmb != null)
			cmb.getItems().addAll("1����","2����","3����",
													"4����","5����");
	}
	 
	 public void okProc() throws ClassNotFoundException, SQLException // ���� Ȯ�� ��ư
	 {
		 ComboBox<String> cmb = (ComboBox<String>)root.lookup("#combo");
			String horse="";
			TextField tf = (TextField)root.lookup("#money");	
			String money = tf.getText();
			
			
			try
			{
				int money2 = Integer.parseInt(money);
				if(money2<1000)
				{
					ta.appendText("õ�� �̻��� �Է��ϼ��� !\n");
	
				}
				else if(player.getMoney()<money2)
				{
					ta.appendText("�ɵ��� �����մϴ� !\n");
				}
				else
				{
					player.setBettingmoney(money2);
					ta.appendText(money2+"���� �����Ͽ����ϴ� !\n");
				   Button btn = (Button)this.parentroot.lookup("#startbtn");
				   btn.setDisable(false);
				   
				}
				
				
			}
			catch(Exception e)
			{
				ta.appendText("�Է� ���� !\n");
			}
			
			
			
			if(cmb==null){ ta.appendText("������ ���� �ʾҽ��ϴ� !\n"); }
			else if(cmb.getValue()==null){
				ta.appendText("���ָ��� �����ϼ��� !\n");
				cmb.requestFocus();
			}else{	horse = cmb.getValue().toString(); 
			   if(horse=="1����")
			   {
				   player.setHorse(1);
				   
			   }
			   if(horse=="2����")
			   {
				   player.setHorse(2);
				   
			   }
			   if(horse=="3����")
			   {
				   player.setHorse(3);
				 
			   }
			   if(horse=="4����")
			   {
				   player.setHorse(4);
				 
			   }
			   if(horse=="5����")
			   {
				   player.setHorse(5);
				   
			   }
			}
			
			
			
			
			
			
			
	
			Stage stage = (Stage) root.getScene().getWindow();
	    	
			stage.close();
			ready = 1;
	 }
	 
	 public void cancelProc() // ���� ��� ��ư
	 {
		 Stage stage = (Stage) root.getScene().getWindow();
	    	stage.close();
	 }
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}
