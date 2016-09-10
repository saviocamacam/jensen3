package jensen2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyConnection {
	String host = "localhost";
	String port = "5432";
	String nomeBancoDados = "jensen2";
	String urlConnection;
	private String user = "postgres";
	private String password = "root";
	
	public MyConnection() {
		this.urlConnection = "jdbc:postgresql://" + host + ":" + port + "/" + nomeBancoDados;
	}
	
	public Connection getConnection(){
        try {
           return DriverManager.getConnection(urlConnection, user, password);
        } catch (SQLException ex) {
        	throw new RuntimeException(ex);
        }
	}
	
	public void release( PreparedStatement stmt ) throws SQLException{
    	if( stmt != null && !stmt.isClosed())
    		try {
    			stmt.close();
    			stmt = null;
    		} catch (SQLException ex) {
    			Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
    		}	
    }
   
    public void release( Connection conn ) throws SQLException{
    	if( conn != null  && !conn.isClosed())
    		try {
    			conn.close();
    			conn = null;
    		} catch (SQLException ex) {
    			Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
    		}
    }
    
    public void release( ResultSet rs ) throws SQLException{
    	if( rs != null &&  !rs.isClosed())
    		try {
    			rs.close();
    		} catch (SQLException ex) {
    			Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
    		}
  	}
    
    public void releaseAll( PreparedStatement stmt, Connection conn ) throws SQLException{
    	release(stmt);
    	release(conn);
    }
     
    public void releaseAll( ResultSet rs, PreparedStatement stmt, Connection conn ) throws SQLException {
    	release(rs);
    	releaseAll(stmt, conn);
    }

	public void releaseAll(ResultSet rs, Statement stmt, Connection conn) {
		try {
			stmt.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
