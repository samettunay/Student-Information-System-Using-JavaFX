package application;

import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;
import java.util.ResourceBundle;

import com.IsteMySQL.Util.DatabaseUtil;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.sql.*;
import animatefx.animation.FadeInUp;
import animatefx.animation.SlideInLeft;


public class LoginController {
	
	public LoginController() {
		baglanti = DatabaseUtil.Baglan();
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane btn_close, btn_down, btn_close1, animatePane;
    
    @FXML
    private AnchorPane form1, anchorPane2;
    @FXML
    private Button register_btn, btn_login, btn_loginA;
    
    @FXML
    private ImageView imgLogo1, imgLogo2;

    @FXML
    public TextField txtField1, txtField3, txtFieldA1, txtFieldA3;
    
    @FXML
    private PasswordField txtField2, txtFieldA2;
    
    @FXML
    private ToggleGroup loginGroup;
    
    @FXML
    private Label captcha, captcha2;

    @FXML
    private VBox akademisyenVBox, ogrenciVBox;
    
    // <MySQL>
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;
    // </MySQL>
    
    private DialogPane dialog;
    private Boolean txt1F = false, txt2F = false, txt1AF = false, txt2AF = false, captcha1Bool = false, captcha2Bool = false;
    private Image logo1 = new Image("img/iste1.png");
    private Image logo2 = new Image("img/iste2.png");
    private RotateTransition rotateTransition = new RotateTransition();
    private FadeTransition fadeTransition = new FadeTransition();
    private Random random = new Random();
    private int random_number1 = random.nextInt(100), random_number2 = random.nextInt(10);
    private int result = random_number1 + random_number2;
    private double xOffset = 0, yOffset = 0; 
    public static Dictionary<String, String> studentTable = new Hashtable<String, String>();
    public static Dictionary<String, String> academicianTable = new Hashtable<String, String>();

    // Akademisyen giriþi visible
    @FXML
    void akademisyen_click(ActionEvent event) {
    	ogrenciVBox.setVisible(false);
    	akademisyenVBox.setVisible(true);
    }
    
    // Öðrenci giriþi visible
    @FXML
    void ogrenci_click(ActionEvent event) {
    	ogrenciVBox.setVisible(true);
    	akademisyenVBox.setVisible(false);
    }
    
    // Uygulama çýkýþ
    @FXML
    void btn_closeClick(MouseEvent event) {
    	Platform.exit();
    }
    
    // Uygulamayý aþaðý alma
    @FXML
    void btn_downClick(MouseEvent event) {
    	Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.setIconified(true);
    }
    
    // Login Ýþlemleri
    @FXML
    void btn_loginClick(ActionEvent event) {
    	if (!txtField1.getText().isEmpty() && !txtField2.getText().isEmpty()) {
    		if(captcha1Bool) {
	    	sql = "select * from students where number=? and password=?";
	    	try {
	    	    sorguIfadesi=baglanti.prepareStatement(sql);
	    	    sorguIfadesi.setString(1,txtField1.getText().trim());
	    	    sorguIfadesi.setString(2,Encryption.MD5(txtField2.getText().trim()));
	    	    ResultSet getirilen = sorguIfadesi.executeQuery();
	    	    if(!getirilen.next()) { // Giriþ Baþarýsýz
	    	    	Alert alert=new Alert(AlertType.ERROR);
	    	    	alert.setTitle("HATA!");
	    	    	alert.setHeaderText("Þifrenizi doðru girdiðinizden emin olun!");
	    	    	alert.setContentText("Kullanýcý adý veya parola yanlýþ!");
	    	    	dialog = alert.getDialogPane();
	    	    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
	    	    	dialog.getStyleClass().add("dialog");
	    	    	alert.showAndWait();
	    	    }
	    	    else { // Giriþ baþarýlý
	    	    	System.out.println("Hoþgeldin: " + getirilen.getString("name") + " " + getirilen.getString("lastname"));
	    	    	studentTable.put("number", getirilen.getString("number"));
	    	    	studentTable.put("name", getirilen.getString("name"));
	    	    	studentTable.put("lastname", getirilen.getString("lastname"));
	    	    	studentTable.put("gender", getirilen.getString("gender"));
	    	    	studentTable.put("department", getirilen.getString("department"));
	    	    	studentTable.put("gradeYear", getirilen.getString("gradeYear"));
	    	    	studentTable.put("formalEducation", getirilen.getString("formalEducation"));
	    	    	studentTable.put("average", getirilen.getString("average"));
	    	    	
	    	    	try {
	    	    		Stage stage = new Stage();
	    	    		AnchorPane pane2 = (AnchorPane) FXMLLoader.load(getClass().getResource("StudentPanel.fxml"));	
	    	    		Scene scene2 = new Scene(pane2);
	    	    		stage.setScene(scene2);
	    	    		stage.centerOnScreen();
	    	    		stage.initStyle(StageStyle.UNDECORATED);
	    	    		
	    	    		pane2.setOnMousePressed((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
	    		            @Override
	    		            public void handle(MouseEvent event) {
	    		                xOffset = event.getSceneX();
	    		                yOffset = event.getSceneY();
	    		            }
	    		        });
	    		    	
	    		    	pane2.setOnMouseDragged(new EventHandler<MouseEvent>() {
	    		            @Override
	    		            public void handle(MouseEvent event) {
	    		            	
	    		            	stage.setX(event.getScreenX() - xOffset);
	    		            	stage.setY(event.getScreenY() - yOffset);
	    		            }
	    		        });
	    	    		stage.showAndWait();
	    			} catch (Exception e) {
	    				System.out.println(e);
	    			}
	    	    }
	    		} catch (Exception e) {
	    			System.out.println(e);
	    		}
    		} else {
    			Alert alert=new Alert(AlertType.ERROR);
    	    	alert.setTitle("HATA!");
    	    	alert.setHeaderText("Lütfen rakamlarý doðru toplayýnýz!");
    	    	dialog = alert.getDialogPane();
    	    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
    	    	dialog.getStyleClass().add("dialog");
    	    	alert.showAndWait();
    		}
    	} else {
    		Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("HATA!");
	    	alert.setHeaderText("Lütfen tüm alanlarý doldurunuz!");
	    	dialog = alert.getDialogPane();
	    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
	    	dialog.getStyleClass().add("dialog");
	    	alert.showAndWait();
    	}
    }
    
    // Kayýt Ýþlemleri
    @FXML
    void register_click(ActionEvent event) {
    	try {
    		Stage stage = new Stage();
    		AnchorPane pane1 = (AnchorPane) FXMLLoader.load(getClass().getResource("Register.fxml"));	
    		Scene scene1 = new Scene(pane1);
    		stage.setScene(scene1);
    		stage.centerOnScreen();
    		stage.initStyle(StageStyle.UNDECORATED);
    		
    		pane1.setOnMousePressed((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	        });
	    	
	    	pane1.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	
	            	stage.setX(event.getScreenX() - xOffset);
	            	stage.setY(event.getScreenY() - yOffset);
	            }
	        });
    		
    		stage.showAndWait();
		} catch (Exception e) {
			System.out.println(e);
		}
    }

    // Sadece sayýlar ve dokuz tane rakam yazýlabilir
    @FXML
    void txtField1_Changed(KeyEvent event) {
    	txt1F = true;
    	
    	int last_letter_index = txtField1.getText().length() - 1;
    	String last_letter = "";
    	
    	if (txtField1.getText().length() > 0)
    		last_letter = String.valueOf(txtField1.getText().charAt(last_letter_index));
    	
    	if ( last_letter.matches("^[a-zA-Z ]") || txtField1.getText().length() > 9)
		{
    		txtField1.replaceText(last_letter_index, last_letter_index + 1, "");
		}
    	
    }

    // Animasyon için
    @FXML
    void txtField2_Changed(KeyEvent event) {
    	 txt2F = true;
    }

    // Animasyonun çalýþmasý için
	@FXML
    void txtField3_Changed(KeyEvent event) {
		String input = txtField3.getText() == "" ? "0" : txtField3.getText();
		int text = Integer.valueOf(input);
    	
    	if ( txt1F && txt2F && text == result) {
    		captcha1Bool = true;
    		rotateTransition.play();
    		fadeTransition.setDuration(Duration.millis(1000));
    		fadeTransition.setFromValue(0.5);
    		fadeTransition.setToValue(1.0);
    		fadeTransition.play();
    		txt1F = false;
    		txt2F = false;
    	}
    	
    	/* Sadece sayýlar ve sonuç tane rakam yazýlabilir */
    	int last_letter_index = txtField3.getText().length() - 1;
    	String last_letter = "";
    	
    	if (txtField3.getText().length() > 0)
    		last_letter = String.valueOf(txtField3.getText().charAt(last_letter_index));
    		
    	
    	if ( last_letter.matches("^[a-zA-Z ]") || txtField3.getText().length() > String.valueOf(result).length())
		{
    		txtField3.replaceText(last_letter_index, last_letter_index + 1, "");
		}
    }
	
	// <Akedemisyen Giriþi>
	
	@FXML
    void txtFieldA1_Changed(KeyEvent event) {
		txt1AF = true;
    }

    @FXML
    void txtFieldA2_Changed(KeyEvent event) {
    	txt2AF = true;
    }

    @FXML
    void txtFieldA3_Changed(KeyEvent event) {
    	String input = txtFieldA3.getText() == "" ? "0" : txtFieldA3.getText();
		int text = Integer.valueOf(input);
    	
    	if ( txt1AF && txt2AF && text == result) {
    		captcha2Bool = true;
    		rotateTransition.play();
    		fadeTransition.setDuration(Duration.millis(1000));
    		fadeTransition.setFromValue(0.5);
    		fadeTransition.setToValue(1.0);
    		fadeTransition.play();
    		txt1AF = false;
    		txt2AF = false;
    	}
    	
    	/* Sadece sayýlar ve sonuç tane rakam yazýlabilir */
    	int last_letter_index = txtFieldA3.getText().length() - 1;
    	String last_letter = "";
    	
    	if (txtFieldA3.getText().length() > 0)
    		last_letter = String.valueOf(txtFieldA3.getText().charAt(last_letter_index));
    		
    	
    	if ( last_letter.matches("^[a-zA-Z ]") || txtFieldA3.getText().length() > String.valueOf(result).length())
		{
    		txtFieldA3.replaceText(last_letter_index, last_letter_index + 1, "");
		}
    }
	
   @FXML
    void btn_loginAClick(ActionEvent event) {
	   if (!txtFieldA1.getText().isEmpty() && !txtFieldA2.getText().isEmpty()) {
	   		if(captcha2Bool) {
		    	sql = "select * from academician where username=? and password=?";
		    	try {
		    	    sorguIfadesi=baglanti.prepareStatement(sql);
		    	    sorguIfadesi.setString(1,txtFieldA1.getText().trim());
		    	    sorguIfadesi.setString(2,Encryption.MD5(txtFieldA2.getText().trim()));
		    	    ResultSet getirilen = sorguIfadesi.executeQuery();
		    	    if(!getirilen.next()) { // Giriþ Baþarýsýz
		    	    	Alert alert=new Alert(AlertType.ERROR);
		    	    	alert.setTitle("HATA!");
		    	    	alert.setHeaderText("Þifrenizi doðru girdiðinizden emin olun!");
		    	    	alert.setContentText("Kullanýcý adý veya parola yanlýþ!");
		    	    	dialog = alert.getDialogPane();
		    	    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
		    	    	dialog.getStyleClass().add("dialog");
		    	    	alert.showAndWait();
		    	    }
		    	    else { // Giriþ baþarýlý
		    	    	System.out.println("Hoþgeldin: " + getirilen.getString("name") + " " + getirilen.getString("lastname"));
		    	    	academicianTable.put("name", getirilen.getString("name"));
		    	    	academicianTable.put("lastname", getirilen.getString("lastname"));
		    	    	academicianTable.put("mail", getirilen.getString("mail"));
		    	    	academicianTable.put("gender", getirilen.getString("gender"));

		    	    	try {
		    	    		Stage stage = new Stage();
		    	    		AnchorPane pane3 = (AnchorPane) FXMLLoader.load(getClass().getResource("AcademicianPanel.fxml"));	
		    	    		Scene scene3 = new Scene(pane3);
		    	    		stage.setScene(scene3);
		    	    		stage.centerOnScreen();
		    	    		stage.initStyle(StageStyle.UNDECORATED);
		    	    		
		    	    		pane3.setOnMousePressed((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
		    		            @Override
		    		            public void handle(MouseEvent event) {
		    		                xOffset = event.getSceneX();
		    		                yOffset = event.getSceneY();
		    		            }
		    		        });
		    		    	
		    		    	pane3.setOnMouseDragged(new EventHandler<MouseEvent>() {
		    		            @Override
		    		            public void handle(MouseEvent event) {
		    		            	
		    		            	stage.setX(event.getScreenX() - xOffset);
		    		            	stage.setY(event.getScreenY() - yOffset);
		    		            }
		    		        });
		    	    		stage.showAndWait();
		    			} catch (Exception e) {
		    				System.out.println(e);
		    			}
		    	    }
		    		} catch (Exception e) {
		    			System.out.println(e);
		    		}
	   		} else {
	   			Alert alert=new Alert(AlertType.ERROR);
	   	    	alert.setTitle("HATA!");
	   	    	alert.setHeaderText("Lütfen rakamlarý doðru toplayýnýz!");
	   	    	dialog = alert.getDialogPane();
	   	    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
	   	    	dialog.getStyleClass().add("dialog");
	   	    	alert.showAndWait();
	   		}
	   	} else {
	   		Alert alert=new Alert(AlertType.ERROR);
		    	alert.setTitle("HATA!");
		    	alert.setHeaderText("Lütfen tüm alanlarý doldurunuz!");
		    	dialog = alert.getDialogPane();
		    	dialog.getStylesheets().add(getClass().getResource("application.css").toString());
		    	dialog.getStyleClass().add("dialog");
		    	alert.showAndWait();
	   	}
    }

   // </Akedemisyen Giriþi>
   
    @FXML
    void initialize() {
    	/* ÝSTE Logosunda ki kýrmýzý yuvarlaðýn animasyonu */
    	imgLogo1.setImage(logo1);
    	rotateTransition.setNode(imgLogo1);
    	rotateTransition.setDuration(Duration.millis(1000));
    	rotateTransition.setFromAngle(0);
    	rotateTransition.setToAngle(360);
    	rotateTransition.setCycleCount(1);
    	
    	/* ÝSTE Logosunda ki i harfinin opaklýðý */
    	imgLogo2.setImage(logo2);
    	fadeTransition.setNode(imgLogo2);
    	fadeTransition.setFromValue(0.5);
    	fadeTransition.play();
    	
    	/* Captcha */
    	captcha.setText(String.valueOf(random_number1) + " + " + String.valueOf(random_number2));
    	captcha2.setText(String.valueOf(random_number1) + " + " + String.valueOf(random_number2));
    	new SlideInLeft(anchorPane2).setSpeed(0.6).play();
    	new FadeInUp(animatePane).setSpeed(0.6).play();
    }

}
