package application;

import java.io.IOException;
import java.net.URL;
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

public class orderSysController implements Initializable{
	@FXML private HBox menuBox; //������ pane
	@FXML private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, payBtn, delete;
	//@FXML private ImageView sign; //����
	//@FXML private ListView<String> listView;
	@FXML private Label nameLabel, gradeLabel;
	@FXML private TextArea total;
	@FXML private TableView<TableRowData> tableView;
	@FXML private TableColumn<TableRowData, String> food;
	@FXML private TableColumn<TableRowData, Integer> foodPrice;
	@FXML private Button id,money;
	
	
	//int[] flag = new int[9];
	static int totalPrice =0;
	Menu kimbap = new Menu("���", 2500);
	Menu kimchi = new Menu("��ġ", 1500);
	Menu naengmyeon = new Menu("�ø�", 5000);
	Menu tteok = new Menu("��", 3500);
	Menu tteokbokki = new Menu("������", 3000);
	Menu bossam = new Menu("����", 20000);
	Menu bulgogi = new Menu("�Ұ��", 8000);
	Menu bibimbap = new Menu("�����", 6500);
	Menu japchae = new Menu("��ä", 7000);
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Person p = LoginController.log_in_list.get(0);
		id.setText(p.getId());
		money.setText(p.getMoney());
		
		
		food.setCellValueFactory(cellData->cellData.getValue().nameProperty());
		foodPrice.setCellValueFactory(cellData->cellData.getValue().priceProperty().asObject());
		
		
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				int slctPoint = tableView.getSelectionModel().getSelectedIndex();
				TableRowData slctData= tableView.getSelectionModel().getSelectedItem();
				int dltPrice = slctData.priceProperty().getValue();
				tableView.getItems().remove(slctPoint);
				totalPrice -= dltPrice;
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
							
			}
			
		});
		
		
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				tableView.getItems().remove(0, tableView.getItems().size());
				totalPrice = 0;
				total.setText("");
				
			}
			
		});
		
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				tableView.getItems().add(new TableRowData(new SimpleStringProperty("���"), 
						new SimpleIntegerProperty(2500)));
				kimbap.count += 1;
				totalPrice += kimbap.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
			}
			
		});
		
		
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				tableView.getItems().add(new TableRowData(new SimpleStringProperty("��ġ"), 
						new SimpleIntegerProperty(1500)));
				kimchi.count += 1;
				totalPrice += kimchi.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
								
			}
			
		});
				
		btn3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tableView.getItems().add(new TableRowData(new SimpleStringProperty("�ø�"), 
						new SimpleIntegerProperty(5000)));
				naengmyeon.count += 1;
				totalPrice += naengmyeon.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
			}
			
		});
		
		btn4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tableView.getItems().add(new TableRowData(new SimpleStringProperty("��"), 
						new SimpleIntegerProperty(3500)));
				tteok.count += 1;
				totalPrice += tteok.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
					
			}
			
		});
		
		btn5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tableView.getItems().add(new TableRowData(new SimpleStringProperty("������"), 
						new SimpleIntegerProperty(3000)));
				tteokbokki.count += 1;
				totalPrice += tteokbokki.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
				
			}
			
		});
		
		btn6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tableView.getItems().add(new TableRowData(new SimpleStringProperty("����"), 
						new SimpleIntegerProperty(20000)));
				bossam.count += 1;
				totalPrice += bossam.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
				
			}
			
		});
		
		btn7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//listView.getItems().add(bulgogi.getFoodName().toString() +"\t\t\t\t\t\t" + bulgogi.getPrice());
				tableView.getItems().add(new TableRowData(new SimpleStringProperty("�Ұ��"), 
						new SimpleIntegerProperty(8000)));
				bulgogi.count += 1;
				totalPrice += bulgogi.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
				
			}
			
		});
		
		btn8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tableView.getItems().add(new TableRowData(new SimpleStringProperty("�����"), 
						new SimpleIntegerProperty(6500)));
				bibimbap.count += 1;
				totalPrice += bibimbap.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);
				
			}		
		});
		
		btn9.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tableView.getItems().add(new TableRowData(new SimpleStringProperty("��ä"), 
						new SimpleIntegerProperty(7000)));
				japchae.count += 1;
				totalPrice += japchae.getPrice();
				String sum = Integer.toString(totalPrice);
				total.setText(sum);	
				
			}
			
		});
		
	}
	
	public void log_out_handler(Event e)
	{
		Stage stage=(Stage)id.getScene().getWindow();
		
				// TODO Auto-generated method stub
				Alert alert=new Alert(AlertType.CONFIRMATION);
				alert.setTitle("�α׾ƿ�");
				alert.setHeaderText("���� �α׾ƿ� �Ͻðھ��?");
				alert.setContentText("Ok ��ư Ŭ���� �α׾ƿ� �˴ϴ�.");
				Optional<ButtonType> result= alert.showAndWait();
				if(result.get()==ButtonType.OK)
				{
					LoginController.log_in_list.remove(0);
					stage.close();
					
					System.out.println(LoginController.log_in_list.size());
				}			
	}
	
	
	public void handleChargeBtnAtcion(ActionEvent e) {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("ChargeMoney.fxml"));
		try {
			Parent root = loader.load();
			Stage stage=new Stage();
			stage.setTitle("�ܾ�����");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	

}

	
