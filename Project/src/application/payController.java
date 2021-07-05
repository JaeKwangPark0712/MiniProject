package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class payController implements Initializable{

	@FXML private TableView<TableRowData> payTable;
	@FXML private TableColumn<TableRowData, String> payFood;
	@FXML private TableColumn<TableRowData, Integer> payPrice;
	@FXML private Button cancel,payok;
	@FXML private TextField payTotal,id,money;
	int user_money,total_money=0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		id.setText(LoginController.log_in_list.get(0).getId());// "log_in_list"���� ���� �α��� ���� Person�� id�� money ����
		money.setText(LoginController.log_in_list.get(0).getMoney());
		
		// CellValueFactorty�� �Ű������� �޾� TableView�� �� TableColumn���� ����
		payFood.setCellValueFactory(cellData->cellData.getValue().nameProperty());
		payPrice.setCellValueFactory(cellData->cellData.getValue().priceProperty().asObject());
		
		for (TableRowData t: orderSysController.payData) {
			payTable.getItems().add(t);// "orderSysController"���� ����� payData���� �ֹ� ����� ������
		}
		
		String sum = Integer.toString(orderSysController.totalPrice);// "orderSysController"���� ����� totalPrice�� ������
		if(orderSysController.totalPrice == 0) {// �Ѿ��� 0�� ��� TextField�� ����
			payTotal.clear();
		} 
		else {
			payTotal.setText(sum);// �Ѿ��� 0�� �ƴ� ���, �Ѿ��� String���� ��ȯ�Ͽ� TextField�� ���
		}
		
		payok.setOnAction(new EventHandler<ActionEvent>() {// ����â�� 'Ȯ�� ��ư' �̺�Ʈ ó��
			
			@Override
			public void handle(ActionEvent event) {
				 user_money=Integer.parseInt(money.getText());
				 total_money=Integer.parseInt(payTotal.getText());// �α��� �� Person�� Money�� �ֹ� �Ѿ��� Integer�� ��ȯ
				if(user_money<total_money)// Person�� Money�� �ֹ� �Ѿ׺��� ���� ���
				{
					Alert alert=new Alert(AlertType.INFORMATION);// Alert ���� �� ���
					alert.setHeaderText("�ܾ��� �����մϴ�");
					alert.show();
					
				}else
				{
					user_money-=total_money;// Person�� Money���� �Ѿ׸�ŭ ����
					LoginController.log_in_list.get(0).setMoney(String.valueOf(user_money));// Person�� Money �������Ͽ� ��ü�� ������Ʈ
					Stage stage=(Stage)payok.getScene().getWindow();// ���� â�� ����
					orderSysController.totalPrice=0;
					orderSysController.payData.clear();// �Ѿװ� �ֹ� ����� ����
					payTable.getItems().remove(0, payTable.getItems().size());// tableView�� ó������ tableView�� ũ�⿡ �ش��ϴ� tableView �׸� ����
					payTotal.clear();
					stage.close();// ���� â ����
					
					main_page_load();// "orderSys.fxml" â�� ��� �� �α׾ƿ� ��� ����
				}
			}
			
		});
		
		
	}
	
	public void handle_cancel()// ����â�� '��� ��ư' �̺�Ʈ ó��
	{
		Stage stage=(Stage)cancel.getScene().getWindow();// ���� â�� ����
		orderSysController.totalPrice=0;
		orderSysController.payData.clear();// �Ѿװ� �ֹ� ����� ����
		payTable.getItems().remove(0, payTable.getItems().size());// tableView�� ó������ tableView�� ũ�⿡ �ش��ϴ� tableView �׸� ����
		payTotal.clear();
		stage.close();// ���� â ����
		
		main_page_load();// "orderSys.fxml" â�� ��� �� �α׾ƿ� ��� ����
	}
	
	//����â�� ���� �α��� ȭ������ ���ư���(�α׾ƿ�) ����� ������ �޼ҵ�
	public  void main_page_load()
	{
		FXMLLoader loader=new FXMLLoader(getClass().getResource("orderSys.fxml"));// "orderSys.fxml" ���� �ҷ��ͼ� loader ��ü ����
		try {
			Parent p=loader.load();//loader ��ü�� �����̳� ���� �� ��� ����
			Stage main_stage=new Stage();
			main_stage.setScene(new Scene(p));
			
			main_stage.setOnCloseRequest(e->{// "orderSys.fxml"â�� ���� �� �̺�Ʈ �߰�
				Alert alert=new Alert(AlertType.CONFIRMATION);// ���ο� CONFIRMATION Ÿ�� �˸�â ����
				alert.setTitle("�α׾ƿ�");
				alert.setHeaderText("���� �α׾ƿ� �Ͻðھ��?");
				alert.setContentText("Ok ��ư Ŭ���� �α׾ƿ� �˴ϴ�.");
				Optional<ButtonType> result= alert.showAndWait();// �˸�â�� ��� ��, ������� ������ ��ٸ�
				if(result.get()==ButtonType.OK)// OK ��ư�� ������ ��
				{

					Person person=LoginController.log_in_list.get(0);
					orderSysController.save_money.put(person.getId(),person.getMoney());
					LoginController.log_in_list.remove(0);// log_in_list�� ����� Person ��ü ����
					main_stage.close();// "orderSys.fxml"â ����
					FXMLLoader	login_page=new FXMLLoader(getClass().getResource("Login.fxml"));// "Login.fxml" â �ҷ��ͼ� loader ��ü ����

				try {
					Parent p1=login_page.load();
					Stage login_stage=new Stage();
					login_stage.setScene(new Scene(p1));
					login_stage.show();// "Login.fxml" â ����
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
					
				}else
				{
					e.consume();// OK ��ư �̿��� ��ư�� ������ �� if���� ��������
				}
			});
			main_stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
