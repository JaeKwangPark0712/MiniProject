package application;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class orderSysController implements Initializable{
	@FXML private HBox menuBox; //������ pane
	@FXML private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, payBtn, delete;
	@FXML private TextArea total;
	@FXML private TableView<TableRowData> tableView;
	@FXML private TableColumn<TableRowData, String> food;
	@FXML private TableColumn<TableRowData, Integer> foodPrice;
	@FXML private Button id,money;
	
	
	public static LinkedList<TableRowData> payData = new LinkedList<TableRowData>();// ����â���� ����� ����ֱ� ���� LinkedList �÷��� ����
	static int totalPrice =0;
	Menu[] menu = new Menu[9];
	public String[] foodName ={"���", "��ġ", "�ø�", "��", "������", "����", "�Ұ��", "�����", "��ä"};
	public int[] Price = {2500, 1500, 5000, 3500, 3000, 20000, 8000, 6500, 7000};
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//����� �̸��� �ݾ��� id,money ��ư�� ���
		Person p = LoginController.log_in_list.get(0);
		id.setText(p.getId());
		money.setText(p.getMoney());
				
		// ���Ĺ�ư �ʱ�ȭ �� Ŭ������ ��� �����̸��� ���� tableView�� �߰� payData�� �����̸��� ������ �����Ͽ� ����â���� ��¿����� ���
		Button[] btn = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
		for(int i=0; i<9; i++) {// �ݺ������� �� ��ư�� ActionEvent �Ҵ�
			Menu menu = new Menu(foodName[i], Price[i]);// �� ��ư�� �ش��ϴ� �޴��� �̸��� ���� ������
			btn[i].setOnAction(e-> {// �� ��ư�� ActionEvent �Ҵ�
				tableView.getItems().add(new TableRowData(new SimpleStringProperty(menu.getFoodName()),
						new SimpleIntegerProperty(menu.getPrice())));// tableView�� �� TableColumn�� �� ��ư�� �ش��ϴ� �޴��� �̸��� ���� ���
				payData.add(new TableRowData(new SimpleStringProperty(menu.getFoodName()), 
						new SimpleIntegerProperty(menu.getPrice())));// LinkedList �÷��ǿ� �� ��ư�� �ش��ϴ� �޴��� �̸��� ���� ����
				totalPrice += menu.getPrice();// ��ư�� ���� �� ���� totalPrice �� ����
				String sum = Integer.toString(totalPrice);
				total.setText(sum);// TextArea�� totalPrice �� ���
				tableView.scrollTo(payData.size());// �̿��ڰ� ���� ��ư�� �ش��ϴ� �޴��� tableView�� ��ũ�� �̵�
			});
		}
		// CellValueFactorty�� �Ű������� �޾� TableView�� �� TableColumn���� ����
		food.setCellValueFactory(cellData->cellData.getValue().nameProperty());
		foodPrice.setCellValueFactory(cellData->cellData.getValue().priceProperty().asObject());
		
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {// tableView�� �� �׸� ���콺�� Ŭ������ �� �̺�Ʈ �Ҵ�

			@Override
			public void handle(MouseEvent event) {//EventHandler �������̽��� handle �޼ҵ� ������
				
				int slctPoint = tableView.getSelectionModel().getSelectedIndex(); //Ŭ���� �ε���
				TableRowData slctData= tableView.getSelectionModel().getSelectedItem(); //Ŭ���� ���̺�
				if (slctData != null) {
					int dltPrice = slctData.priceProperty().getValue(); //Ŭ���� ���̺��� ���ݵ����� 
					tableView.getItems().remove(slctPoint); //���̺��� Ŭ���� �ε����� ����
					totalPrice -= dltPrice;	//������ �ε����� ���ݸ�ŭ �����ݾ� ����
					String sum = Integer.toString(totalPrice); //�����ݾ� Stringĳ����	
					payData.remove(slctPoint);	//����â �޴� ����
					if (totalPrice == 0) {
						total.clear();	//�� �ݾ� 0�� ����� ���ؼ� TextArea ����
					}
					else {
						total.setText(sum);	//�����ݾ� ���	
					}				
				}
				tableView.getSelectionModel().clearSelection();
							
			}	
			
		});
		
		
		delete.setOnAction(new EventHandler<ActionEvent>() {// '��ü ���� ��ư'�̺�Ʈ
			@Override
			public void handle(ActionEvent event) {// EventHandler �������̽��� handle �޼ҵ� ������
				
				payData.clear();// ����â �޴� ����
				tableView.getItems().remove(0, tableView.getItems().size());// tableView�� ó������ tableView�� ũ�⿡ �ش��ϴ� tableView �׸� ����
				totalPrice = 0;// �� �ݾ� 0������ �ʱ�ȭ
				total.setText("");			
			}	
			
		});
		
	}
	
	public void log_out_handler(Event e)// �α׾ƿ� ��ư(id�� ǥ�õǴ� ��ư)�� �̺�Ʈ �Ҵ�
	{
		
		Stage stage=(Stage)id.getScene().getWindow();// ���� â�� ����
		Alert alert=new Alert(AlertType.CONFIRMATION);// ���ο� CONFIRMATION Ÿ�� �˸�â ����
		alert.setTitle("�α׾ƿ�");
		alert.setHeaderText("���� �α׾ƿ� �Ͻðھ��?");
		alert.setContentText("Ok ��ư Ŭ���� �α׾ƿ� �˴ϴ�.");
		Optional<ButtonType> result= alert.showAndWait();// �˸�â�� ��� ��, ������� ������ ��ٸ�
		
		if(result.get()==ButtonType.OK)// OK ��ư�� ������ ��
		{
			orderSysController.payData.clear();// �ֹ� ������ ����
			LoginController.log_in_list.remove(0);// log_in_list�� ����� Person ��ü ����
			stage.close();// ���� â�� ����
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Login.fxml"));// "Login.fxml" â �ҷ��ͼ� loader ��ü ����
			try {
				Parent p=loader.load();
				Stage main_stage=new Stage();
				main_stage.setScene(new Scene(p));
				main_stage.show();// "Login.fxml" â ����
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		}	
		
				
	}
	
	
	public void handleChargeBtnAtcion(ActionEvent e) {// ������ư(�ܾ��� ǥ�õǴ� ��ư)�� �̺�Ʈ �Ҵ�
		FXMLLoader loader=new FXMLLoader(getClass().getResource("ChargeMoney.fxml"));// "ChargeMoney.fxml" ���� �ҷ��ͼ� loader ��ü ����
	 Stage charge_stage=(Stage)money.getScene().getWindow();// ���� â�� ����
		try {
			charge_stage.close();// ȭ�� ��ȯ�� ���� ���� â�� ����
			Parent root = loader.load();
			Stage stage=new Stage(); 
			stage.setTitle("�ܾ�����");
			stage.setScene(new Scene(root));
			stage.show();// "ChargeMoney.fxml" â ����
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void handlePayBtnAction(Event e)//���� ��ư(���� �Ϸ���Ʈ�� ǥ�õǴ� ��ư)�� �̺�Ʈ �Ҵ�
	{
		FXMLLoader loader=new FXMLLoader(getClass().getResource("pay.fxml"));// "pay.fxml" ���� �ҷ��ͼ� loader ��ü ����
		Parent p;
		Stage main_stage=(Stage)payBtn.getScene().getWindow();// ���� â�� ����
		try {
			p = loader.load();
			Stage stage=new Stage();
			stage.setTitle("����");
			stage.setScene(new Scene(p));
			stage.show();// "pay.fxml" â ����
			main_stage.close();// ȭ�� ��ȯ�� ���� ���� â�� ����
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}


}

	
