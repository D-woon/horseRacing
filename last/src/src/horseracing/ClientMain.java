package horseracing;

import horseracing.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Client Page");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
		Parent root = loader.load();
		ClientController controller = loader.getController(); // �� ������ �ڵ尡 �־��
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
