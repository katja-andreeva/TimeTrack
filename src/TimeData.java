/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
        
public class TimeData {
    
    private int user_id;
    private String dt;
    private double hours;
    private double coeff;
    private DBAccess dba;
    private double rate;
    private Connection con;
    
    public TimeData(DBAccess d, int id) {
        user_id = id;
        dt = "";
        hours = 0;
        coeff = 0;
        rate = 0;
        dba = d;
        con = dba.getConnection();
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
    
    public void saveTimeData(){
        try {
            PreparedStatement ps = con.prepareStatement("SELECT rate from salary "
                    + "where employee_id = ?");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                   rate = rs.getDouble("rate");
            }
            
            ps = con.prepareStatement("SELECT * from timeUnit "
                    + "where employee_id = ? and date = ?");
            ps.setInt(1,user_id);
            ps.setString(2, dt);
            rs = ps.executeQuery();
            
            if(rs.next()){
                ps = con.prepareStatement("UPDATE timeUnit "
                        + "SET coeff = ?, hours = ?, rate = ? "
                        + "where employee_id = ? and date = ?");
                
                ps.setDouble(1, coeff);
                ps.setDouble(2, hours);
                ps.setDouble(3, rate);
                ps.setInt(4, user_id);
                ps.setString(5, dt);
            } else {
                ps = con.prepareStatement("INSERT INTO "
                        + "timeUnit(employee_id,coeff,hours, date, rate) "
                        + "VALUES(?,?,?,?,?)");
                ps.setInt(1, user_id);
                ps.setDouble(2, coeff);
                ps.setDouble(3, hours);
                ps.setString(4, dt);
                ps.setDouble(5, rate);
            }
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TimeData.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }
    
    public void loadTimeData(){
        coeff = 0;
        hours = 0;
        rate = 0;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT coeff, hours, rate from timeUnit "
                    + "where employee_id = ? and date = ?");
            ps.setInt(1, user_id);
            ps.setString(2, dt);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                coeff = rs.getDouble("coeff");
                hours = rs.getDouble("hours");
                rate = rs.getDouble("rate");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
