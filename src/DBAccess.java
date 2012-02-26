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
     
    private String dbUrl = null;
    private String dbuser = null;
    private String dbpass = null;
    
    private Connection dbconnection;
    private Statement stmt;
    private static final String PROP_FILE="database.properties";
    
    public DBAccess(){
        
        readPropertiesFile();
        
        try {
            dbconnection = DriverManager.getConnection( dbUrl, dbuser, dbpass );
            stmt = dbconnection.createStatement();
        } catch (SQLException e) {
            System.err.println ("Error message: " + e.getMessage ());
            System.err.println ("Error number: " + e.getErrorCode ());
        }
                
    }
    
    public String testConn() throws SQLException{
        String dbtime = null;
        try {
            ResultSet rs = stmt.executeQuery("select now()");
            while (rs.next()){
                dbtime=rs.getString(1);
            }
        } catch (SQLException e) {
            System.err.println ("Error message: " + e.getMessage ());
            System.err.println ("Error number: " + e.getErrorCode ());
        }
        return dbtime;
    }
    
    public ResultSet read_db(String s) throws SQLException{
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(s);
        }catch (SQLException e) {
            System.err.println ("Error message: " + e.getMessage ());
            System.err.println ("Error number: " + e.getErrorCode ());
        }
        
        return rs;
        
    }
    
    public Connection getConnection(){
        return dbconnection;
    }
    
    public boolean write_db(String s) throws SQLException{
        try {
            stmt.executeUpdate(s);
        } catch (SQLException e) {
            System.err.println ("Error message: " + e.getMessage ());
            System.err.println ("Error number: " + e.getErrorCode ());
            return false;
        }
        
        return true;
    }
    
    /** 
    * release connection 
    **/
    public void close() {
        if (dbconnection==null) return;
        try {
            dbconnection.close();
        } catch(SQLException e){
            System.err.println ("Error message: " + e.getMessage ());
            System.err.println ("Error number: " + e.getErrorCode ());
        }
    }

    
    public void readPropertiesFile(){
        try{
                InputStream is = DBAccess.class.getResourceAsStream(PROP_FILE);
                Properties prop = new Properties();
                prop.load(is);
                
                dbUrl = prop.getProperty("db");
    		dbuser = prop.getProperty("dbuser");
    		dbpass = prop.getProperty("dbpass");
                
                is.close();
            /* code to use values read from the file*/
        }catch(Exception e){
            System.out.println("Failed to read from " + PROP_FILE + " file.");
        }
    }

}
