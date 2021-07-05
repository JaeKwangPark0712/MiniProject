package application;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController implements Initializable {

	@FXML private TextField id;
	@FXML private PasswordField pwd;
	@FXML private Button login_button;
	@FXML private Hyperlink register_button;
	Alert alert=new Alert(AlertType.INFORMATION);
	public static List<Person> log_in_list=new ArrayList<>(); //�α����� �� ����� ��� �迭
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		login_button.setOnAction(e->btn_login_action(e));
		register_button.setOnAction(e->btn_register_action(e));
	}
	
	public void btn_login_action(Event e)
	{
		 FXMLLoader loader=new FXMLLoader(getClass().getResource("orderSys.fxml"));
		 Person person=new Person(id.getText(),pwd.getText());
		if(RegisterController.member.contains(person))
		{
			try {
				log_in_list.add(person);
				Parent p=loader.load();
				Stage stage=new Stage();
				stage.setTitle("�ֹ�");
				stage.setScene(new Scene(p));
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					
					@Override
					public void handle(WindowEvent event) {
						// TODO Auto-generated method stub
						Alert alert=new Alert(AlertType.CONFIRMATION);
						alert.setTitle("�α׾ƿ�");
						alert.setHeaderText("���� �α׾ƿ� �Ͻðھ��?");
						alert.setContentText("Ok ��ư Ŭ���� �α׾ƿ� �˴ϴ�.");
						Optional<ButtonType> result= alert.showAndWait();
						if(result.get()==ButtonType.OK)
						{
							orderSysController.payData.clear();
							LoginController.log_in_list.remove(0);
							stage.close();
							FXMLLoader loader=new FXMLLoader(getClass().getResource("Login.fxml"));
							try {
								Parent p=loader.load();
								Stage main_stage=new Stage();
								main_stage.setScene(new Scene(p));
								main_stage.show();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
						}else
						{
							event.consume();
						}
					}
				});
				
				stage.show();
				Stage login_stage=(Stage)register_button.getScene().getWindow();
				login_stage.close();
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else
		{
			alert.setTitle("�α��� ����!!");
			alert.setHeaderText("�ٽ� �Է����ּ���");
			alert.show();
		}
	}
	
	public void btn_register_action(Event e)
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Register.fxml"));
		
		try {
			Parent p=loader.load();
			Stage stage=new Stage();
			stage.setTitle("ȸ������");
			stage.setScene(new Scene(p)); 
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
