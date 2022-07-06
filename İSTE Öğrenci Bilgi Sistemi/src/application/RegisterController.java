package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import com.IsteMySQL.Util.DatabaseUtil;

import animatefx.animation.SlideInDown;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegisterController {
	
	public RegisterController() {
		baglanti = DatabaseUtil.Baglan();
	}

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane btn_close1;

    @FXML
    private Pane btn_down;

    @FXML
    private ImageView imgLogo1;

    @FXML
    private ImageView imgLogo2;

    @FXML
    private Button register_btn;

    @FXML
    private TextField txtField1;

    @FXML
    private TextField txtField2;

    @FXML
    private TextField txtField3;

    @FXML
    private PasswordField txtField4;

    @FXML
    private PasswordField txtField5;
    
    @FXML
    private Pane registerPane;
    
    @FXML
    private RadioButton radioBtn1, radioBtn2;
    
    private DialogPane dialog;
    
    
    
    
    // <MySQL>
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;
    // </MySQL>

    @FXML
    void btn_closeClick(MouseEvent event) {
    	Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    void btn_downClick(MouseEvent event) {
    	Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setIconified(true);
    }
    
    @FXML
    void register_click(ActionEvent event) {
    	sql = "insert into students(number, name, lastname, gender, password, department, gradeYear) values(?,?,?,?,?,?,?) ";
    	try {
    		if(txtField1.getText().isEmpty() || txtField2.getText().isEmpty() || txtField3.getText().isEmpty() 
    				|| txtField4.getText().isEmpty() || txtField5.getText().isEmpty()) {
    			Alert alert=new Alert(AlertType.ERROR);
    	    	alert.setTitle("HATA!");
    	    	alert.setHeaderText("Lütfen tüm alanlarý doldurunuz!");
    	    	dialog = alert.getDialogPane();
    	    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
    	    	dialog.getStyleClass().add("dialog");
    	    	alert.showAndWait();
    		}
    		else {
	    	    if(!txtField4.getText().trim().equals(txtField5.getText().trim())) {
	    	    	Alert alert=new Alert(AlertType.ERROR);
	    	    	alert.setTitle("HATA!");
	    	    	alert.setHeaderText("Þifrenizin tekrarýný yanlýþ girdiniz!");
	    	    	dialog = alert.getDialogPane();
	    	    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
	    	    	dialog.getStyleClass().add("dialog");
	    	    	alert.showAndWait();
	    	    }
	    	    else {
		    	    sorguIfadesi=baglanti.prepareStatement(sql);
		    	    if (radioBtn1.isSelected()) {
		    	    	sorguIfadesi.setString(4, "Erkek");
		    	    } else if (radioBtn2.isSelected()) {
		    	    	sorguIfadesi.setString(4, "Kadýn");
		    	    } 
		    	    sorguIfadesi.setString(1,txtField1.getText().trim());
		    	    sorguIfadesi.setString(2,txtField2.getText().trim());
		    	    sorguIfadesi.setString(3,txtField3.getText().trim());
		    	    sorguIfadesi.setString(5,Encryption.MD5(txtField4.getText().trim()));
		    	    sorguIfadesi.setString(6,"Bilgisayar Mühendisliði");
		    	    sorguIfadesi.setString(7,"3");
		    	    sorguIfadesi.executeUpdate();
		    	    add_lessons();
		    	    Alert alert=new Alert(AlertType.INFORMATION);
	    	    	alert.setTitle("Kayýt Baþarýlý!");
	    	    	alert.setHeaderText("Baþarýlý bir þekilde kaydýnýzý tamamladýnýz!");
	    	    	dialog = alert.getDialogPane();
	    	    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
	    	    	dialog.getStyleClass().add("dialog");
	    	    	alert.showAndWait();
	    	    }
    		}
    		
    		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
    			Alert alert=new Alert(AlertType.ERROR);
    	    	alert.setTitle("HATA!");
    	    	alert.setHeaderText("Bu numara kullanýlýyor lütfen farklý numara seçiniz!");
    	    	dialog = alert.getDialogPane();
    	    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
    	    	dialog.getStyleClass().add("dialog");
    	    	alert.showAndWait();
    		}
    		catch (Exception e) {
    			System.out.println(e);
    		}
    }
    
    void add_lessons() {
    	sql = "insert into lessons(studentNumber, department, Ders, gradeYear) values(?,?,?,?) ";
    	for(int i = 0; i < 6; i++) {
    		
    		String lesson = "null";
    		if (i == 0)
    			lesson = "Nesne Tabanlý Programlama";
    		if (i == 1)
    			lesson = "Staj";
    		if (i == 2)
    			lesson = "Mikroiþlemciler";
    		if (i == 3)
    			lesson = "Bilgisayar Aðlarý";
    		if (i == 4)
    			lesson = "Web Tabanlý Programlama";
    		if (i == 5)
    			lesson = "Yazýlým Mühendisliðine Giriþ";

	    	try {
	    	    sorguIfadesi=baglanti.prepareStatement(sql);
	    	    sorguIfadesi.setString(1,txtField1.getText().trim());
	    	    sorguIfadesi.setString(2,"Bilgisayar Mühendisliði");
	    	    sorguIfadesi.setString(3,lesson);
	    	    sorguIfadesi.setString(4,"3");
	    	    sorguIfadesi.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
    	}
    }
    
    @FXML
    void txtField1_Changed(KeyEvent event) {
    	/* Sadece sayýlar ve dokuz tane rakam yazýlabilir */
    	
    	int last_letter_index = txtField1.getText().length() - 1;
    	String last_letter = "";
    	
    	if (txtField1.getText().length() > 0)
    		last_letter = String.valueOf(txtField1.getText().charAt(last_letter_index));
    	
    	if ( last_letter.matches("^[a-zA-Z ]") || txtField1.getText().length() > 9)
		{
    		txtField1.replaceText(last_letter_index, last_letter_index + 1, "");
		}
    	
    }

    @FXML
    void initialize() {
    	new SlideInDown(registerPane).play();
    }

}

