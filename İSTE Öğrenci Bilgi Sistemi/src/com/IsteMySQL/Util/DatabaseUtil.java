package com.IsteMySQL.Util;
import java.sql.*;

public class DatabaseUtil {
	
	static Connection conn=null;
	public static Connection Baglan(){
		try{
      // jdbc:mysql:// Server IPAdresi/db_ismi","kullanici",
    	conn = DriverManager.getConnection("jdbc:mysql://localhost/isteDB", "root", "mysql");
    	return conn;
    	}
    	catch(Exception e){
    		// TODO:handle exception
    		return null;
    	}
	}
}
