/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author toni
 */
public class Account {
    
    int user_id;
    String username;
    String password;
    
    String first_name;
    String last_name;
    
    DBAccess dba;
            
    public Account() {
        user_id = -1;
        username = "";
        password = "";
        
        first_name = "";
        last_name = "";
        
        dba = new DBAccess();
        dba.connectDB();
    }
    
    public void loadUserById(int id) {
        user_id = id;
        //here we query for user from DB
        dba.read_db(String.format("select * from employeeData where id = %d;",user_id));
    }
    
    public void loadUserByUname(String u) {
        //here we query for user from DB
    }
    
    public void setUsername(String u) {
        username = u;
        //here we query username from DB
    }
    
    public void setUserId(int id) {
        id = user_id;
        //here we query id from DB        
    }
}
