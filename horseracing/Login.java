package horseracing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("�渶����");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
		Parent root = loader.load();
		LoginController controller = loader.getController(); // �� ������ �ڵ尡 �־��
		controller.setPrimaryStage(primaryStage); // ȭ����ȯ�� ����� �����ϳ׿� ����
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
