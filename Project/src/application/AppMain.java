package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application{
  
	FXMLLoader loader;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		loader=new FXMLLoader(getClass().getResource("Login.fxml"));
		Parent p=loader.load();
		primaryStage.setScene(new Scene(p));
		primaryStage.setTitle("�α���");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Person person=new Person("admin", "admin");
		RegisterController.member.add(person);// member HashSet�� admin ���� �߰�
		launch(args);
		
	}

}
   