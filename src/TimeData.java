/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */
import java.sql.SQLException;
import java.util.*;
        
public class TimeData {
    
    private int user_id;
    private String dt;
    private double hours;
    private double coeff;
        
    public TimeData(int u){
        user_id = u;
        
    }
    
    public TimeData(int u, String d, double h, double c) {
        user_id = u;
        dt = d;
        hours = h;
        coeff = c;
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
    
    public void saveTimeData(DBAccess d) throws SQLException{
        
        DBAccess dba = d;
        double rate = 0;
        dba.write_db(String.format("INSERT INTO "
                + "timeUnit(employee_id,coeff,rate) VALUES('%d','%f','%f');"
                ,user_id,coeff,rate));
        
        
    }
    
    public void loadTimeData(DBAccess d){
        
    }
}
