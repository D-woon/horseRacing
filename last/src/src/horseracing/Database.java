package horseracing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.javafx.collections.SetListenerHelper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Database {

	public void memberShip(String insertId, String insertPwd, Stage primaryStage, Button memberShip,
			AnchorPane member) {
		String jdbcUrl = "jdbc:oracle:thin:@192.168.19.167:1521:xe";
		// MySQL ����
		String dbId = "java";
		// MySQL ���� ��й�ȣ
		String dbPw = "1234";
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = "";
		String sql2 = "";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ��� ����
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select id from horse where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, insertId);
			rs = pstmt.executeQuery();
			Stage dialog = new Stage(StageStyle.UTILITY); // ȸ�� ������ ��� ���̵��� �ߺ�
															// ���� �Ǵ� ���̵�� ��й�ȣ��
															// �Է����� ���� ��쿡 ����
															// ���̾�α�
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(primaryStage);
			dialog.setTitle("�ߺ�Ȯ��");

			AnchorPane parent = (AnchorPane) FXMLLoader.load(getClass().getResource("custom_dialog.fxml"));
			Label txtTitle = (Label) parent.lookup("#txtTitle");
			Button btnOk = (Button) parent.lookup("#btnOk");
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);

			if (rs.next()) { // �ߺ�Ȯ��
				if (rs.getString("id").equals(insertId)) {
					txtTitle.setText("�̹� ������� ���̵� �Դϴ�.");
					btnOk.setOnAction(event -> dialog.close());
					dialog.show();
				}
				// ���̵� ��й�ȣ�� �Է����� �ʾ��� ���
			} else if (insertId.equals("") || insertPwd.equals("")) {
				txtTitle.setText("���̵�� ��й�ȣ�� �Է����ּ���.");
				btnOk.setOnAction(event -> dialog.close());
				dialog.show();
			} else { // ȸ�������� ������ ���
				txtTitle.setText("��� ������ ���̵��Դϴ�. ȸ�������� �����մϴ�!");
				btnOk.setOnAction(event -> dialog.close());
				dialog.show();

				sql2 = "insert into horse values(?,?,?,?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, insertId);
				pstmt2.setString(2, insertPwd);
				pstmt2.setInt(3, 10000);
				pstmt2.setInt(4,0);
				pstmt2.executeUpdate();
				AnchorPane root = (AnchorPane) memberShip.getScene().getRoot();
				root.getChildren().remove(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
	}

	public void login(String insertId, String insertPwd, Stage primaryStage) {
		String jdbcUrl = "jdbc:oracle:thin:@192.168.19.167:1521:xe";
		// MySQL ����
		String dbId = "java";
		// MySQL ���� ��й�ȣ
		String dbPw = "1234";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ��� ����
			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);

			sql = "select id,password from horse where id = ? and password = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, insertId);
			pstmt.setString(2, insertPwd);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("id").equals(insertId) && rs.getString("password").equals(insertPwd)) {
					MainClass mc=new MainClass();
					mc.start(primaryStage);				
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	if (pstmt != null)	try {	pstmt.close();	} catch (SQLException ex) {	}
			if (conn != null)	try {	conn.close();	} catch (SQLException ex) {	}
		}
	}
}
