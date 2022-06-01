package mavenproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Set;

public class lpEm implements Runnable{

	HashMap<Integer, modelChannel> modelChannel;
	Statement stmt = null;
	
	lpEm(HashMap<Integer, modelChannel> modelChannel){
		this.modelChannel = modelChannel;
	}
	
	@Override
	public void run() {
		Set<Integer> keySet = modelChannel.keySet();
		for(Integer key : keySet) {
			
		}
		stmt = null;
		try (Connection conn = getConnection()) {
			StringBuffer sqlObject = new StringBuffer();
	        	
			sqlObject.append("	select 1 from lp_em ");
			sqlObject.append("	where channel = 0 ");
			sqlObject.append("	AND mdev_id = '1111' ");
			sqlObject.append("	AND yyyymmddhh = '2022042814' ");
	        	
				stmt = conn.createStatement();
				int g = stmt.executeUpdate(sqlObject.toString());	
				
			    
			    stmt.close();
			    
			    stmt = null;
			    if(g> 0) {
			    	sqlObject = new StringBuffer();
			    	sqlObject.append("	update lp_em ");
					sqlObject.append("	set hh = '11' ");
			    	sqlObject.append("	where channel = 0 ");
					sqlObject.append("	AND yyyymmddhh = '2022042814' ");
					sqlObject.append("	AND dst = 0 ");
			    	sqlObject.append("	AND MDEV_ID='1111' ");
					sqlObject.append("	AND MDEV_TYPE = 'Meter' ");
			    }else {
			    	sqlObject = new StringBuffer();
			    	sqlObject.append("	insert into lp_em(channel,YYYYMMDDHH,DST,MDEV_ID,MDEV_TYPE) ");
					sqlObject.append("	values(0,'2022042814',0,'1111','Meter') ");
			    }
			    stmt = conn.createStatement();
				stmt.executeUpdate(sqlObject.toString());	
				stmt.close();
			    if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static Connection getConnection() throws SQLException {		
		String dbURL = "jdbc:oracle:thin:@192.168.1.17:1521:NURI";
		String username = "aimir";
		String password = "aimiramm";
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		return DriverManager.getConnection(dbURL, username, password);
	} 
}
