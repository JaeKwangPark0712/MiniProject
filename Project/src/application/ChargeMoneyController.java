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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChargeMoneyController implements Initializable{
	@FXML Label money;
	@FXML TextField chargeMoney;
	@FXML Button chargeButton;
	FXMLLoader loader;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		money.setText(LoginController.log_in_list.get(0).getMoney());// ���� �α��� �� Person ��ü���� money�� ���� Label�� ���
		chargeMoney.setText("0");
	}
	
	public void handlerChargeButtonAction(ActionEvent e) {// '�����ϱ� ��ư' �̺�Ʈ �Ҵ�
		orderSysController.payData.clear();// ����â�� ���� ���� �ʱ�ȭ
		int leftMoney = Integer.parseInt(LoginController.log_in_list.get(0).getMoney());
		int cgMoney = Integer.parseInt(chargeMoney.getText());//TextField���� �����ϰ��� �ϴ� �ݾ� ����
		int tot = leftMoney + cgMoney;// �ܾ׿� �����ݾ� �ջ�
		String totMoney = Integer.toString(tot);
		LoginController.log_in_list.get(0).setMoney(totMoney);// ���� �α��� �� Person ��ü�� money ���� ������Ʈ
		money.setText(LoginController.log_in_list.get(0).getMoney());// ���� �α��� �� Person ��ü���� money�� ���� Label�� ���
		
		//"orderSys.fxml" Stage�� �ٽ� ǥ���� �� �α׾ƿ� ���� �ϰ� ����
		loader=new FXMLLoader(getClass().getResource("orderSys.fxml"));
		try {
			Stage charge_stage=(Stage)chargeButton.getScene().getWindow();// ���� â�� ����
			charge_stage.close();// ���� â�� ����
			Parent p=loader.load();// "orderSys.fxml" ������ ���� Stage ����
			Stage main_stage=new Stage();
			main_stage.setOnCloseRequest(new EventHandler<WindowEvent>() {// "orderSys.fxml" Stage�� �ݾ��� �� �̺�Ʈ �Ҵ�
				
				@Override
				public void handle(WindowEvent event) {
					Alert alert=new Alert(AlertType.CONFIRMATION);// ���ο� CONFIRMATION Ÿ�� �˸�â ����
					alert.setTitle("�α׾ƿ�");
					alert.setHeaderText("���� �α׾ƿ� �Ͻðھ��?");
					alert.setContentText("Ok ��ư Ŭ���� �α׾ƿ� �˴ϴ�.");
					Optional<ButtonType> result= alert.showAndWait();// �˸�â�� ��� ��, ������� ������ ��ٸ�
					if(result.get()==ButtonType.OK)// OK ��ư�� ������ ��
					{
						Person person=LoginController.log_in_list.get(0);
						orderSysController.save_money.put(person.getId(),person.getMoney());
						LoginController.log_in_list.remove(0);// �ֹ� ������ ����
						main_stage.close();// "orderSys.fxml" Stage�� ����
					}else
					{
						event.consume();// OK ��ư �̿��� ��ư�� ������ �� if���� ��������
					}
				}
			});
			main_stage.setScene(new Scene(p));
			main_stage.show();// "orderSys.fxml" Stage�� ���� ��
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
