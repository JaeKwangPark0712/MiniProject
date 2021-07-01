package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController implements Initializable {

	@FXML private TextField id;
	@FXML private PasswordField pwd;
	@FXML private TextField name;
	@FXML private Button register_button;
	@FXML private Button check_button;
	Alert alert=new Alert(AlertType.INFORMATION);
	public static Set<Person> member=new HashSet<Person>(); //����� ��� HashSet
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void register_action(Event e)
	{
		
		Stage stage=(Stage)register_button.getScene().getWindow();
		for(Person p:member)
		{
			if(!p.getId().equals(id.getText()))
			{
				Person person=new Person(id.getText(), pwd.getText(), name.getText());
			//	person.print(); //������ �ϴ� �ڵ�
				member.add(person);
				stage.close();
				alert.setTitle("ȸ������ ����");
				alert.setHeaderText("ȸ���� �ǽŰ��� �����մϴ�.");
				alert.show();
				person.member_info();
				break;
			}
			else
			{
				alert.setHeaderText("�̹� �ִ� ���̵� �Դϴ�..");
				alert.show();
				break;
			}
		}
		
	}
	
	public void check_action(Event e)
	{
		for(Person p:member)
		{
			if(p.getId().equals(id.getText()))
			{
				alert.setHeaderText("�̹� �ִ� ���̵� �Դϴ�.(�ٽ� �Է����ּ���)");
				alert.show();
				break;
			}else
			{
				alert.setHeaderText("��밡���� ���̵��Դϴ�.");
				alert.show();
				break;
			}
		}
	}
	
	
	

}
