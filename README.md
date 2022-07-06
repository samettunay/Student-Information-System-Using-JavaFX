# Student-Information-System-Using-JavaFX
<p align="center">
Student information system automation with JavaFX and MySQL Database
</p>

![bandicam 2022-07-06 13-17-54-031](https://user-images.githubusercontent.com/79511355/177530310-2deeee60-c9a3-424b-9f7a-ef44af10bfd0.gif)

# For Connection MySQL Database

- I used xampp for database

- DatabaseUtil.java

```java
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
```
# Usage

- Import istedb.sql file your MySQL Database
- Open "İSTE Öğrenci Bilgi Sistemi" file from Eclipse or your compiler
