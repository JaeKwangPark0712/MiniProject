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
	public static List<Person> log_in_list=new ArrayList<>(); //���� �α��� �� ��� ����Ʈ ����
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		login_button.setOnAction(e->btn_login_action(e));
		register_button.setOnAction(e->btn_register_action(e));
	}
	
	public void btn_login_action(Event e)// 'login ��ư'�̺�Ʈ 
	{
		
		 Person person=new Person(id.getText(),pwd.getText());// id �ؽ�Ʈ �ʵ�� password �н����� �ʵ忡 �Էµ� ������ Person ��ü ����
		if(RegisterController.member.contains(person))// member HashSet�� �α��� �������� ������ Person ��ü�� ���� ��ü�� �ִ��� Ȯ��
		{
			if(person.getId().equals("admin"))
			{
				try {
					FXMLLoader admin=new FXMLLoader(getClass().getResource("Admin.fxml"));
					
					Parent p = admin.load();
					Stage stage=new Stage();
					stage.setScene(new Scene(p));
					stage.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else
			{
				 FXMLLoader loader=new FXMLLoader(getClass().getResource("orderSys.fxml"));// "orderSys.fxml" ���� �ҷ��ͼ� loader ��ü ����
				try { // ���� ��ü�� ���� ��, �Ʒ��� ���� ����
					if(orderSysController.save_money.containsKey(person.getId()))
					{
						String save_money=orderSysController.save_money.get(person.getId()); 
						person.setMoney(save_money);					
					}
					log_in_list.add(person);// log_in_list�� ���� �α������� Person ��ü �߰�
					Parent p=loader.load();//loader ��ü�� �����̳� ���� �� ��� ����
					Stage stage=new Stage();
					stage.setTitle("�ֹ�");
					stage.setScene(new Scene(p));
					
					stage.setOnCloseRequest(new EventHandler<WindowEvent>() {// "orderSys.fxml"â�� ���� �� �̺�Ʈ �߰�
						
						@Override
						public void handle(WindowEvent event) {
							Alert alert=new Alert(AlertType.CONFIRMATION);// ���ο� CONFIRMATION Ÿ�� �˸�â ����
							alert.setTitle("�α׾ƿ�");
							alert.setHeaderText("���� �α׾ƿ� �Ͻðھ��?");
							alert.setContentText("Ok ��ư Ŭ���� �α׾ƿ� �˴ϴ�.");
							Optional<ButtonType> result= alert.showAndWait();// �˸�â�� ��� ��, ������� ������ ��ٸ�
							
							if(result.get()==ButtonType.OK)// OK ��ư�� ������ ��
							{
								orderSysController.payData.clear();// �ֹ� ������ ����
								Person person=LoginController.log_in_list.get(0);
								orderSysController.save_money.put(person.getId(),person.getMoney());
								LoginController.log_in_list.remove(0);// log_in_list�� ����� Person ��ü ����
								stage.close();// "orderSys.fxml"â ����
								FXMLLoader loader=new FXMLLoader(getClass().getResource("Login.fxml"));// "Login.fxml" â �ҷ��ͼ� loader ��ü ����
								try {
									Parent p=loader.load();
									Stage main_stage=new Stage();
									main_stage.setScene(new Scene(p));
									main_stage.show();// "Login.fxml" â ����
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}else
							{
								event.consume();// OK ��ư �̿��� ��ư�� ������ �� if���� ��������
							}
							
						}
						
					});
					
					stage.show();// *"orderSys.fxml" ���Ϸ� ���� Stage ����
					Stage login_stage=(Stage)register_button.getScene().getWindow();//"register_button"�� ����� â(�α��� â) �ҷ�����
					login_stage.close();// �α��� â �ݱ�
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			
			
		}else // ���� ��ü�� ���� ��
		{
			alert.setTitle("�α��� ����!!");// �α��� ���� ����, �ؽ�Ʈ ���� �� �˸�â ���
			alert.setHeaderText("�ٽ� �Է����ּ���");
			alert.show();
		}
	}
	
	public void btn_register_action(Event e)//'�����ϱ� �������ؽ�Ʈ' �̺�Ʈ
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Register.fxml"));// "Register.fxml" â �ҷ��ͼ� loader ��ü ����
		
		try {
			Parent p=loader.load();//loader ��ü�� �����̳� ���� �� Stage ����
			Stage stage=new Stage();
			stage.setTitle("ȸ������");
			stage.setScene(new Scene(p)); 
			stage.show();//"Register.fxml" â���� ������ Stage ���
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
