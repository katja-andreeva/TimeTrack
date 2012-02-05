/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */
import java.sql.*;

public class Account {
    
    int user_id;
    String username;
    String password;
    
    String first_name;
    String last_name;
    
    DBAccess dba;
    Connection con;
            
    public Account() {
        user_id = -1;
        username = "";
        password = "";
        
        first_name = "";
        last_name = "";
        
        dba = new DBAccess();
    }
    
    public void loadUserById(int id) {
        ResultSet rs = null;        
        try {
            rs = dba.read_db(String.format(
                "select employeeData.id, employeeData.first_name, "
                + "employeeData.last_name, user.username from employeeData, user "
                + "where employeeData.id=user.employee_id=%d;",id));
            
            while(rs.next()){
               user_id = rs.getInt("id");
               first_name = rs.getString("first_name");
               last_name = rs.getString("last_name");
               username = rs.getString("username");
            }
        }catch(SQLException e){
            
        }
    }
    
    public void loadUserByUname(String u) {

        try {            
            ResultSet rs = dba.read_db(String.format(
                "select employeeData.id, employeeData.first_name, employeeData.last_name, "
                + "user.username from employeeData, user "
                + "where user.username=\'%s\' and employeeData.id=user.employee_id;",u));
            
            while(rs.next()){
               user_id = rs.getInt("id");
               first_name = rs.getString("first_name");
               last_name = rs.getString("last_name");
               username = rs.getString("username");
            }
        }catch(SQLException e){
            
        }
    }
    
    public void setUsername(String u) {
        username = u;
        //here we query username from DB
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setFirstName(String f) {
        first_name = f;
    }
    
    public String getFirstName() {
        return first_name;
    }
    
    public void setLastName(String l) {
        last_name = l;
    }
    
    public String getLastName() {
        return last_name;
    }
    
    public void setUserId(int id) {
        id = user_id;
        //here we query id from DB        
    }
    
    public int getUserId() {
        return user_id;
    }

}
