package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.IsteMySQL.Util.DatabaseUtil;

import animatefx.animation.FadeInRight;
import animatefx.animation.SlideInLeft;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.*;

public class StudentPanelController {
	
	public StudentPanelController() {
		baglanti = DatabaseUtil.Baglan();
	}


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane btn_close1;
    
    @FXML
    private ImageView imgLogo1;
    private Image logo1 = new Image("img/avatar_man.png");
    private Image logo2 = new Image("img/avatar_woman.png");

    @FXML
    private Label label1, label2, numaraLabel, orgunLabel, ortalamaLabel, sinifLabel, soyisimLabel, isimLabel, bolumLabel;
    
    @FXML
    private HBox genelBilgiler;
    
    @FXML
    private AnchorPane animateAnchPane;
    
    @FXML
    private TableView<ObservableList> tableView1, tableView2, tableView3, tableView4, tableView5;
    
    private ObservableList<ObservableList> data, data2, data3, data4, data5;
    
    
    // <MySQL>
    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;
    // </MySQL>
    
    
    @FXML
    void btn1_click(ActionEvent event) {
    	new FadeInRight(genelBilgiler).play();
    	genelBilgiler.setVisible(true);
    	tableView3.setVisible(false);
    	tableView1.setVisible(false);
    	tableView2.setVisible(false);
    	tableView4.setVisible(false);
    	tableView5.setVisible(false);
    }

    @FXML
    void btn2_click(ActionEvent event) {
    	new FadeInRight(tableView1).play();
    	tableView1.setVisible(true);
    	tableView3.setVisible(false);
    	tableView2.setVisible(false);
    	tableView4.setVisible(false);
    	tableView5.setVisible(false);
    	genelBilgiler.setVisible(false);
    }

    @FXML
    void btn3_click(ActionEvent event) {
    	new FadeInRight(tableView2).play();
    	tableView2.setVisible(true);
    	tableView3.setVisible(false);
    	tableView1.setVisible(false);
    	tableView4.setVisible(false);
    	tableView5.setVisible(false);
    	genelBilgiler.setVisible(false);
    }

    @FXML
    void btn4_click(ActionEvent event) {
    	new FadeInRight(tableView3).play();
    	tableView3.setVisible(true);
    	tableView2.setVisible(false);
    	tableView1.setVisible(false);
    	tableView4.setVisible(false);
    	tableView5.setVisible(false);
    	genelBilgiler.setVisible(false);
    }

    @FXML
    void btn5_click(ActionEvent event) {
    	new FadeInRight(tableView4).play();
    	tableView4.setVisible(true);
    	tableView3.setVisible(false);
    	tableView2.setVisible(false);
    	tableView1.setVisible(false);
    	tableView5.setVisible(false);
    	genelBilgiler.setVisible(false);
    }

    @FXML
    void btn6_click(ActionEvent event) {
    	new FadeInRight(tableView5).play();
    	tableView5.setVisible(true);
    	tableView4.setVisible(false);
    	tableView3.setVisible(false);
    	tableView2.setVisible(false);
    	tableView1.setVisible(false);
    	genelBilgiler.setVisible(false);
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
    	label1.setText(LoginController.studentTable.get("name") + " " + LoginController.studentTable.get("lastname"));
    	label2.setText(LoginController.studentTable.get("department"));
    	
    	if (LoginController.studentTable.get("gender").equals("Erkek")) {
    		imgLogo1.setImage(logo1);
    	} else {
    		imgLogo1.setImage(logo2);
    	}
    	
    	numaraLabel.setText(LoginController.studentTable.get("number"));
    	isimLabel.setText(LoginController.studentTable.get("name"));
    	soyisimLabel.setText(LoginController.studentTable.get("lastname"));
    	bolumLabel.setText(LoginController.studentTable.get("department"));
    	String orgun = LoginController.studentTable.get("formalEducation") == "1" ? "BÖ":"ÝÖ";
    	orgunLabel.setText(orgun);
    	sinifLabel.setText(LoginController.studentTable.get("gradeYear"));
    	ortalamaLabel.setText(LoginController.studentTable.get("average"));
    	
    	// Not listesi & Devamsýzlýk listesi & Transkript
    	// https://stackoverflow.com/questions/18941093/how-to-fill-up-a-tableview-with-database-data
    	// Bu linkden bulduklarýmý özelleþtirdim
    	
    	sql = "select * from lessons where studentNumber=?";
    	data = FXCollections.observableArrayList();
    	data2 = FXCollections.observableArrayList();
    	data3 = FXCollections.observableArrayList();
    	data4 = FXCollections.observableArrayList();
    	data5 = FXCollections.observableArrayList();
    	
    	try {
    	    sorguIfadesi=baglanti.prepareStatement(sql);
    	    sorguIfadesi.setInt(1,Integer.valueOf(LoginController.studentTable.get("number")));
    	    ResultSet getirilen = sorguIfadesi.executeQuery();
    	    
    	    for(int i=0 ; i<getirilen.getMetaData().getColumnCount(); i++){
    	    	if (getirilen.getMetaData().getColumnName(i+1).equals("Ders") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Vize") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Final") || 
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Ortalama") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Durum"))
    	    	{
    	    		final int j = i;                
                    TableColumn col = new TableColumn(getirilen.getMetaData().getColumnName(i+1));
                    col.setStyle("-fx-background-color: #393351;");
                    col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                            return new SimpleStringProperty(param.getValue().get(j).toString());                        
                        }                    
                    });

                    tableView1.getColumns().addAll(col);
    	    	}
    	    	else
    	    		continue;
                
            }
    	    
    	    for(int i=0 ; i<getirilen.getMetaData().getColumnCount(); i++){
    	    	if (getirilen.getMetaData().getColumnName(i+1).equals("Ders") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Devamsizlik") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Durum"))
    	    	{
    	    		final int j = i;                
                    TableColumn col2 = new TableColumn(getirilen.getMetaData().getColumnName(i+1));
                    col2.setStyle("-fx-background-color: #393351;");
                    col2.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                            return new SimpleStringProperty(param.getValue().get(j).toString());                        
                        }                    
                    });

                    tableView2.getColumns().addAll(col2); 
    	    	} else {
    	    		continue;
    	    	}
                
            }
    	    
    	    for(int i=0 ; i<getirilen.getMetaData().getColumnCount(); i++){
    	    	if (getirilen.getMetaData().getColumnName(i+1).equals("Ders") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Ortalama") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("gradeYear") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Durum"))
    	    	{
    	    		final int j = i;                
    	    		String name = getirilen.getMetaData().getColumnName(i+1).equals("gradeYear") ? "Sýnýf" : getirilen.getMetaData().getColumnName(i+1);
                    TableColumn col3 = new TableColumn(name);
                    col3.setStyle("-fx-background-color: #393351;");
                    col3.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                            return new SimpleStringProperty(param.getValue().get(j).toString());                        
                        }                    
                    });

                    tableView3.getColumns().addAll(col3); 
    	    	} else {
    	    		continue;
    	    	}
                
            }
    	    
    	    for(int i=0 ; i<getirilen.getMetaData().getColumnCount(); i++){
    	    	if (getirilen.getMetaData().getColumnName(i+1).equals("Ders") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Derslik") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Saat"))
    	    	{
    	    		final int j = i;                
                    TableColumn col4 = new TableColumn(getirilen.getMetaData().getColumnName(i+1));
                    col4.setStyle("-fx-background-color: #393351;");
                    col4.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                            return new SimpleStringProperty(param.getValue().get(j).toString());                        
                        }                    
                    });

                    tableView4.getColumns().addAll(col4); 
    	    	} else {
    	    		continue;
    	    	}
                
            }
    	    
    	    while(getirilen.next())	{
                ObservableList<String> row = FXCollections.observableArrayList();
                ObservableList<String> row2 = FXCollections.observableArrayList();
                ObservableList<String> row3 = FXCollections.observableArrayList();
                ObservableList<String> row4 = FXCollections.observableArrayList();
                for(int i=1 ; i<=getirilen.getMetaData().getColumnCount(); i++){
                	if (getirilen.getString(i).equals("0.0") || getirilen.getString(i).equals("0")) {
                		row.add("--");
                		row2.add("--");
                		row3.add("--");
                		row4.add("--");
                	}
                	else {
                		if (getirilen.getString("gradeYear").equals(LoginController.studentTable.get("gradeYear"))) {
                			row.add(getirilen.getString(i));                		
                			row2.add(getirilen.getString(i));                			
                			row4.add(getirilen.getString(i));                			
                		} else {
                			row.add("");                		
                			row2.add("");  
                			row4.add("");  
                		}
                		row3.add(getirilen.getString(i));
                	}
                }
                data.add(row);
                data2.add(row2);
                data3.add(row3);
                data4.add(row4);
            }
    	    tableView1.setItems(data);
    	    tableView2.setItems(data2);
    	    tableView3.setItems(data3);
    	    tableView4.setItems(data4);
    	} catch (Exception e) {
			// TODO: handle exception
    		System.out.println(e);
		}
    	
    	
    	// Sýnav Takvimi
    	sql = "select * from examSchedule where gradeYear=? and Bölüm=?";
    	
    	try {
    	    sorguIfadesi=baglanti.prepareStatement(sql);
    	    sorguIfadesi.setInt(1,Integer.valueOf(LoginController.studentTable.get("gradeYear")));
    	    sorguIfadesi.setString(2,LoginController.studentTable.get("department"));
    	    ResultSet getirilen = sorguIfadesi.executeQuery();
    	    
    	    for(int i=0 ; i<getirilen.getMetaData().getColumnCount(); i++){
    	    	if (getirilen.getMetaData().getColumnName(i+1).equals("Ders") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Derslik") ||
    	    			getirilen.getMetaData().getColumnName(i+1).equals("Saat"))
    	    	{
    	    		final int j = i;                
                    TableColumn col5 = new TableColumn(getirilen.getMetaData().getColumnName(i+1));
                    col5.setStyle("-fx-background-color: #393351;");
                    col5.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                            return new SimpleStringProperty(param.getValue().get(j).toString());                        
                        }                    
                    });

                    tableView5.getColumns().addAll(col5);
    	    	}
    	    	else
    	    		continue;
                
            }
    	    
    	    while(getirilen.next())	{
                ObservableList<String> row5 = FXCollections.observableArrayList();
                for(int i=1 ; i<=getirilen.getMetaData().getColumnCount(); i++){
                	row5.add(getirilen.getString(i)); 
                }
                data5.add(row5);
            }

    	    tableView5.setItems(data5);
    	} catch (Exception e) {
			// TODO: handle exception
    		System.out.println(e);
		}
    }
}