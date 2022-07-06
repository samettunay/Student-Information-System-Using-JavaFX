package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.IsteMySQL.Util.DatabaseUtil;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class AcademicianPanelController {
	
	public AcademicianPanelController() {
		baglanti = DatabaseUtil.Baglan();
	}
	
	// <MySQL>
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;
    // </MySQL>

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane animateAnchPane;

    @FXML
    private Pane btn_close1;

    @FXML
    private ImageView imgLogo1;

    @FXML
    private ImageView imgLogo12;
    
    @FXML
    private VBox notGirBox;

    @FXML
    private Label label1, label2;
    
    @FXML
    private ChoiceBox<String> bolumBox;
    @FXML
    private ChoiceBox<String> dersBox;

    @FXML
    private TableColumn<Lessons, String> ogrNo;
    @FXML
    private TableColumn<Lessons, String> vize;
    @FXML
    private TableColumn<Lessons, String> ortalama;
    @FXML
    private TableColumn<Lessons, String> finall;
    @FXML
    private TableColumn<Lessons, String> ders;
    @FXML
    private TableColumn<Lessons, String> devamsizlik;
    @FXML
    private TableColumn<Lessons, String> durum;
    
    private Image logo1 = new Image("img/avatar_man.png");
    private Image logo2 = new Image("img/avatar_woman.png");
    
    @FXML
    private TableView<Lessons> notTableView;

    @FXML
    void btn1_click(ActionEvent event) {
    	new SlideInRight(notGirBox).play();
    	notGirBox.setVisible(true);
    }
    
    
    @FXML
    void ara_click(ActionEvent event) {
    	notTableView.getItems().clear();
        sql = "select * from lessons where department=? and Ders=?";
        try {
        	sorguIfadesi=baglanti.prepareStatement(sql);
    	    sorguIfadesi.setString(1,bolumBox.getValue());
    	    sorguIfadesi.setString(2,dersBox.getValue());
    	    ResultSet getirilen = sorguIfadesi.executeQuery();
    	    while(getirilen.next()) {
    	    	notTableView.getItems().add(
    	    	          new Lessons(
    	    	        		  getirilen.getInt("studentNumber"),
    	    	        		  getirilen.getString("Ders"),
    	    	        		  getirilen.getString("Vize"),
    	    	        		  getirilen.getString("Final"),
    	    	        		  getirilen.getString("Ortalama"),
    	    	        		  getirilen.getString("Devamsizlik"),
    	    	        		  getirilen.getString("Durum")
    	    	        		  ));
    	    }
        	ogrNo.setCellValueFactory(
        		    new PropertyValueFactory<>("studentNumber"));
        	ders.setCellValueFactory(
        		    new PropertyValueFactory<>("Ders"));
        	vize.setCellValueFactory(
        		    new PropertyValueFactory<>("Vize"));
        	finall.setCellValueFactory(
        		    new PropertyValueFactory<>("Final"));
        	ortalama.setCellValueFactory(
        		    new PropertyValueFactory<>("Ortalama"));
        	devamsizlik.setCellValueFactory(
        		    new PropertyValueFactory<>("Devamsizlik"));
        	durum.setCellValueFactory(
        		    new PropertyValueFactory<>("Durum"));
        	
        } catch (Exception e) {
			System.out.println(e);
		}
    }

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
    void initialize() {

    	new SlideInLeft(animateAnchPane).setSpeed(0.6).play();
    	label1.setText(LoginController.academicianTable.get("name") + " " + LoginController.academicianTable.get("lastname"));
    	label2.setText(LoginController.academicianTable.get("mail"));
    	
    	bolumBox.getItems().add("Bilgisayar Mühendisliði");
    	bolumBox.getItems().add("Elektronik Mühendisliði");
    	bolumBox.getItems().add("Mekatronik Mühendisliði");
    	
    	dersBox.getItems().add("Nesne Tabanlý Programlama");
    	dersBox.getItems().add("Mikroiþlemciler");
    	dersBox.getItems().add("Bilgisayar Aðlarý");
    	dersBox.getItems().add("Web Tabanlý Programlama");
    	dersBox.getItems().add("Yazýlým Mühendisliðine Giriþ");
    	dersBox.getItems().add("Staj");
    	
    	if (LoginController.academicianTable.get("gender").equals("Erkek")) {
    		imgLogo1.setImage(logo1);
    	} else {
    		imgLogo1.setImage(logo2);
    	}
    	
    	notTableView.setEditable(true);
    	vize.setCellFactory(TextFieldTableCell.forTableColumn());
    	finall.setCellFactory(TextFieldTableCell.forTableColumn());
    	devamsizlik.setCellFactory(TextFieldTableCell.forTableColumn());
    	
    	vize.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lessons,String>>() {
			
			@Override
			public void handle(CellEditEvent<Lessons, String> event) {
				Lessons lessons = event.getRowValue();
				lessons.setVize(event.getNewValue());
				
				sql = "UPDATE lessons SET Vize =? WHERE studentNumber=? and Ders=?";
				try {
					sorguIfadesi=baglanti.prepareStatement(sql);
		    	    sorguIfadesi.setDouble(1,Double.valueOf(lessons.getVize()));
		    	    sorguIfadesi.setInt(2,lessons.getStudentNumber());
		    	    sorguIfadesi.setString(3,dersBox.getValue());
		    	    sorguIfadesi.executeUpdate();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		finall.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lessons,String>>() {
			@Override
			public void handle(CellEditEvent<Lessons, String> event) {
				Lessons lessons = event.getRowValue();
				lessons.setFinal(event.getNewValue());
				Double result = Double.valueOf(lessons.getVize()) * 0.4 + Double.valueOf(lessons.getFinal()) * 0.6;
				
				sql = "UPDATE lessons SET Final =?, Ortalama=?, Durum=? WHERE studentNumber=? and Ders=?";
				try {
					sorguIfadesi=baglanti.prepareStatement(sql);
		    	    sorguIfadesi.setDouble(1,Double.valueOf(lessons.getFinal()));
		    	    sorguIfadesi.setDouble(2,result);
		    	    if(result <= 40)
		    	    	sorguIfadesi.setString(3,"Kaldý");
		    	    else
		    	    	sorguIfadesi.setString(3,"Geçti");
		    	    sorguIfadesi.setInt(4,lessons.getStudentNumber());
		    	    sorguIfadesi.setString(5,dersBox.getValue());
		    	    sorguIfadesi.executeUpdate();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		devamsizlik.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Lessons,String>>() {
			@Override
			public void handle(CellEditEvent<Lessons, String> event) {
				Lessons lessons = event.getRowValue();
				lessons.setDevamsizlik(event.getNewValue());
				
				sql = "UPDATE lessons SET Devamsizlik =?, Durum=? WHERE studentNumber=? and Ders=?";
				try {
					sorguIfadesi=baglanti.prepareStatement(sql);
		    	    sorguIfadesi.setInt(1,Integer.valueOf(lessons.getDevamsizlik()));
		    	    if(Integer.valueOf(lessons.getDevamsizlik()) >= 4)
		    	    	sorguIfadesi.setString(2,"Kaldý");
		    	    else if(Double.valueOf(lessons.getOrtalama()) >= 40 && Integer.valueOf(lessons.getDevamsizlik()) <= 4)
		    	    	sorguIfadesi.setString(2,"Geçti");
		    	    else
		    	    	sorguIfadesi.setString(2,"Kaldý");
		    	    sorguIfadesi.setInt(3,lessons.getStudentNumber());
		    	    sorguIfadesi.setString(4,dersBox.getValue());
		    	    sorguIfadesi.executeUpdate();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
}
    
    public class Lessons {

        private int studentNumber;
        private int gradeYear;
        private String Devamsizlik;
        private String Ders = null;
        private String Durum = null;
        private String Vize = null;
        private String Final = null;
        private String Ortalama;


        public Lessons() {
        }

        public Lessons(int studentNumber, String Ders, String Vize, String Final, String Ortalama, String Devamsizlik, String Durum) {
        	this.studentNumber = studentNumber;
        	this.Ders = Ders;
        	this.Vize = Vize;
        	this.Final = Final;
        	this.Ortalama = Ortalama;
        	this.Devamsizlik = Devamsizlik;
        	this.Durum = Durum;
        }
        

        public int getStudentNumber() {
            return studentNumber;
        }

        public void setFirstName(int studentNumber) {
            this.studentNumber = studentNumber;
        }
        
        public String getDers() {
            return Ders;
        }

        public void setDers(String Ders) {
            this.Ders = Ders;
        }
        
        public String getVize() {
            return Vize;
        }

        public void setVize(String Vize) {
            this.Vize = Vize;
        }
        
        public String getFinal() {
            return Final;
        }

        public void setFinal(String Final) {
            this.Final = Final;
        }
        
        public String getDurum() {
            return Durum;
        }

        public void setDurum(String Durum) {
            this.Durum = Durum;
        }
        
        public String getDevamsizlik() {
            return Devamsizlik;
        }

        public void setDevamsizlik(String Devamsizlik) {
            this.Devamsizlik = Devamsizlik;
        }
        
        public int getGradeYear() {
            return gradeYear;
        }

        public void setGradeYear(int gradeYear) {
            this.gradeYear = gradeYear;
        }
        
    	public String getOrtalama() {
    		return Ortalama;
    	}

    	public void setOrtalama(String Ortalama) {
    		this.Ortalama = Ortalama;
    	}

    }

}

