/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DBAccess {
     
    String dbUrl = null;
    String dbuser = null;
    String dbpass = null;
    
    Connection dbconnection = null;

    public void ConnectDB(){
        
        Properties prop = new Properties();
        
        try {
               //load a properties file
    		prop.load(new FileInputStream("database.properties"));
 
               //get the property value and print it out
                dbUrl = prop.getProperty("db");
    		dbuser = prop.getProperty("dbuser");
    		dbpass = prop.getProperty("dbpass");
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
        
        try {
            dbconnection = DriverManager.getConnection( dbUrl, dbuser, dbpass );
        } catch (SQLException e) {
            
        }
        
    }
    
    public String testConn(){
        String dbtime = null;
        try {
            Statement stmt =  dbconnection.createStatement();
            ResultSet rs = stmt.executeQuery("select now()");
            while (rs.next()){
                dbtime=rs.getString(1);
            }
        } catch (SQLException e) {
            
        }
        return dbtime;
    }
    
    public ResultSet read_db(String s){
        ResultSet rs = null;
        try {
            Statement stmt =  dbconnection.createStatement();
            rs = stmt.executeQuery(s);
        } catch (SQLException e) {
            
        }
        return rs;
    }
    
    public boolean write_db(String s){
        try {
            Statement stmt =  dbconnection.createStatement();
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
}
