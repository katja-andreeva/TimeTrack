/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */
import java.sql.*;
        
public class TimeData {
    
    private int user_id;
    private String dt;
    private double hours;
    private double coeff;
    private DBAccess dba;
    private double rate;
    
    public TimeData(DBAccess d) {
        user_id = -1;
        dt = "";
        hours = 0;
        coeff = 0;
        rate = 0;
        dba = d;
    }
    
    public void setDate(String d){
        dt = d;
    }
    
    public String getDate() {
        return dt;
    }
    
    public void setHours(double h){
        hours = h;
    }
    
    public double getHours(){
        return hours;
    }
    
    public double getCoeff(){
        return coeff;
    }
    
    public void setCoeff(double c){
        coeff = c;
    }
    
    public double getRate(){
        return rate;
    }
    
    public void saveTimeData(int uid) throws SQLException{
        user_id = uid;
        ResultSet rs = dba.read_db(String.format("SELECT rate from salary "
                + "where employee_id='%d');", user_id));
        while(rs.next()){
               rate = rs.getDouble("rate");
        }
        dba.write_db(String.format("INSERT INTO "
                + "timeUnit(employee_id,coeff,hours, date, rate) "
                + "VALUES('%d','%f','%f');"
                ,user_id, coeff, hours, dt, rate));
        
        
    }
    
    public void loadTimeData(int uid, String d) throws SQLException{
        user_id = uid;
        dt = d;
        ResultSet rs = dba.read_db(String.format("SELECT coeff, hours, rate "
                + "where employee_id='%d' and date='%s';",user_id, dt));
        while(rs.next()){
            coeff = rs.getDouble("coeff");
            hours = rs.getDouble("hours");
            rate = rs.getDouble("rate");
        }
    }
}
