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
	//@FXML private ImageView sign; //����
	//@FXML private ListView<String> listView;
	@FXML private TextArea total;
	@FXML private TableView<TableRowData> tableView;
	@FXML private TableColumn<TableRowData, String> food;
	@FXML private TableColumn<TableRowData, Integer> foodPrice;
	@FXML private Button id,money;
	
	
	public static LinkedList<TableRowData> payData = new LinkedList<TableRowData>();
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
		
		//���Ĺ�ư �ʱ�ȭ �� Ŭ������ ��� �����̸��� ���� tableView�� �߰� payData�� �����̸��� ������ �����Ͽ� ����â���� ��¿����� ���
		Button[] btn = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
		for(int i=0; i<9; i++) {
			Menu menu = new Menu(foodName[i], Price[i]);
			btn[i].setOnAction(e-> {
				tableView.getItems().add(new TableRowData(new SimpleStringProperty(menu.getFoodName()), 
						new SimpleIntegerProperty(menu.getPrice())));
				payData.add(new TableRowData(new SimpleStringProperty(menu.getFoodName()), 
						new SimpleIntegerProperty(menu.getPrice())));
				totalPrice += menu.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
				tableView.scrollTo(payData.size());
			});
		}
		
		
		food.setCellValueFactory(cellData->cellData.getValue().nameProperty());
		foodPrice.setCellValueFactory(cellData->cellData.getValue().priceProperty().asObject());
		
		
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
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
		
		
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				payData.clear();
				tableView.getItems().remove(0, tableView.getItems().size());
				totalPrice = 0;
				total.setText("");			
			}	
			
		});
		
	}
	
	public void log_out_handler(Event e)
	{
		
		Stage stage=(Stage)id.getScene().getWindow();
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
			
			
		}	
		
				
	}
	
	
	public void handleChargeBtnAtcion(ActionEvent e) {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("ChargeMoney.fxml"));
	 Stage charge_stage=(Stage)money.getScene().getWindow();
		try {
			charge_stage.close();
			Parent root = loader.load();
			Stage stage=new Stage(); 
			stage.setTitle("�ܾ�����");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void handlePayBtnAction(Event e)
	{
		FXMLLoader loader=new FXMLLoader(getClass().getResource("pay.fxml"));
		Parent p;
		Stage main_stage=(Stage)payBtn.getScene().getWindow();
		try {
			p = loader.load();
			Stage stage=new Stage();
			stage.setTitle("����");
			stage.setScene(new Scene(p));
			stage.show();
			main_stage.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}


}

	
