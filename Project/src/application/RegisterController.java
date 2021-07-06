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
	Alert alert=new Alert(AlertType.INFORMATION); // ȸ������ �ȳ� �˸�â ����
	public static Set<Person> member=new HashSet<Person>(); //ȸ�� ����� ��� HashSet ����
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) { // Initializable �������̽��� initiazlize �޼ҵ� ������
		
	}
	
	public void register_action(Event e) // 'ȸ������ ��ư' �̺�Ʈ 
	{
		
		Stage stage=(Stage)register_button.getScene().getWindow(); // ���� �����ִ� Stage�� ����		
		if(!member.contains(id.getText()))
		{
			Person person=new Person(id.getText(), pwd.getText(), name.getText()); 
			member.add(person); //�ߺ��Ǵ� id�� ���� ��� member HashSet�� Person ��ü �߰�
			stage.close();
			alert.setTitle("ȸ������ ����");
			alert.setHeaderText("ȸ���� �ǽŰ��� �����մϴ�.");
			alert.show();
		}else
		{
			alert.setHeaderText("�̹� �ִ� ���̵� �Դϴ�.."); //�ߺ��Ǵ� id�� ���� ��� ���â ���
			alert.show();
		}
		
	}
	
	public void check_action(Event e)// '�ߺ�üũ ��ư' �̺�Ʈ 
	{
		
		if(member.contains(new Person(id.getText())))
		{
			alert.setHeaderText("�̹� �ִ� ���̵� �Դϴ�.(�ٽ� �Է����ּ���)");//�ߺ��Ǵ� id�� ���� ��� ���â ���
			alert.show();
		}else
		{
			alert.setHeaderText("��밡���� ���̵��Դϴ�.");//�ߺ��Ǵ� id�� ���� ��� id ��� ���� �ȳ�â ���
			alert.show();
		}
	}
	
	}
	


