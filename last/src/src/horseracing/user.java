package horseracing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user {
	private String ID;
	private int money;
	private int horse; // �̱���
	private int bettingmoney; //������ ��
	private String password;
	
	user() throws ClassNotFoundException, SQLException{
		
		Class.forName("oracle.jdbc.driver.OracleDriver");//��ɵ�� �޸𸮰����� �÷�����
		String id= "java"; String pwd ="1234";
		String url="jdbc:oracle:thin:@192.168.19.167:1521:xe";//���� �õ� 1521����Ʈ��ȣ xe�� ����Ŭ ����
		Connection conn=DriverManager.getConnection(url,id,pwd);//��ϵ� ��ɻ���ϱ� ��ɵ���̾ȵǸ� ��� �Ұ� 
		System.out.println("���� ��ü:"+conn);
		PreparedStatement pstm= null;
		ResultSet rs=null;
		String sql=null; 
		sql = "select * from horse";
		pstm= conn.prepareStatement(sql);
		rs =pstm.executeQuery();
		while(rs.next()) {//rs next�� ���� ���� �ҷ��´� 
		this.ID=rs.getString("id");
        this.password=rs.getString("password");
        this.money=rs.getInt("money");
        this.bettingmoney=rs.getInt("bettingmoney");
		if(this.ID==rs.getString("id")) {break;}
		}
		if(rs!=null)rs.close();
		if(pstm!=null)pstm.close();
		if(conn!=null)conn.close();
		
		
	}


	public String getID() {

		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getMoney() throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");//��ɵ�� �޸𸮰����� �÷�����
		String id= "java"; String pwd ="1234";
		String url="jdbc:oracle:thin:@192.168.19.167:1521:xe";//���� �õ� 1521����Ʈ��ȣ xe�� ����Ŭ ����
		Connection conn=DriverManager.getConnection(url,id,pwd);//��ϵ� ��ɻ���ϱ� ��ɵ���̾ȵǸ� ��� �Ұ� 
		System.out.println("���� ��ü:"+conn);
		PreparedStatement pstm= null;
		ResultSet rs=null;
		String sql=null; 
		sql = "update newst set money=? where id=?";
		pstm= conn.prepareStatement(sql);
		pstm.setInt(1,this.money);
		pstm.setString(2,ID);
		pstm.executeUpdate();
		return money;
	}

	public void setMoney(int money) throws ClassNotFoundException, SQLException {
		this.money = money;
		Class.forName("oracle.jdbc.driver.OracleDriver");//��ɵ�� �޸𸮰����� �÷�����
		String id= "java"; String pwd ="1234";
		String url="jdbc:oracle:thin:@192.168.19.167:1521:xe";//���� �õ� 1521����Ʈ��ȣ xe�� ����Ŭ ����
		Connection conn=DriverManager.getConnection(url,id,pwd);//��ϵ� ��ɻ���ϱ� ��ɵ���̾ȵǸ� ��� �Ұ� 
		System.out.println("���� ��ü:"+conn);
		PreparedStatement pstm= null;
		ResultSet rs=null;
		String sql=null; 
		sql = "update newst set money=? where id=?";
		pstm= conn.prepareStatement(sql);
		pstm.setInt(1,this.money);
		pstm.setString(2,ID);
		pstm.executeUpdate();
		
		
	}

	public int getHorse() {
		return horse;
	}

	public void setHorse(int horse) {
		this.horse = horse;
	}

	public int getBettingmoney() {
		return bettingmoney;
	}

	public void setBettingmoney(int bettingmoney) {
	this.bettingmoney=bettingmoney;
	}
	


















}
