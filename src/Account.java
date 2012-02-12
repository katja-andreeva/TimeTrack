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
    
    String firstname;
    String lastname;
    String title;
    String address;
    String phone;
    String startdate;
    String acctype;
    
    DBAccess dba;
    InputValidator iv;
            
    public Account() {
        user_id = -1;
        username = "";
        password = "";
        
        firstname = "";
        lastname = "";
        
        
        dba = new DBAccess();
        iv = new InputValidator();
    }
    
    public Account(int uid, String uname, String pw, String fname, String lname) {
        user_id = uid;
        username = uname;
        password = pw;
        firstname = fname;
        lastname = lname;
        
        dba = new DBAccess();
    }
    
    public boolean loadUser() {
        if (iv.validateInt(user_id)){
           return loadUserById(user_id);
        }
        else if (iv.validateString(username)) {
           return loadUserByUname(username);
        }
        else
            return false;
    }
    private boolean loadUserById(int id) {        
        
        boolean success = false;
        try {
            ResultSet rs = dba.read_db(String.format(
                "select employeeData.id, employeeData.first_name, "
                + "employeeData.last_name, user.username from employeeData, user "
                + "where employeeData.id=%d and user.employee_id=%d;",id, id));
            
            while(rs.next()){
               success = true;
               user_id = rs.getInt("id");
               firstname = rs.getString("first_name");
               lastname = rs.getString("last_name");
               username = rs.getString("username");
            }
        }catch(SQLException e){
            success = false;
        }
        
        return success;
    }
    
    private boolean loadUserByUname(String u) {
        
        boolean success = false;
        try {            
            ResultSet rs = dba.read_db(String.format(
                "select employeeData.id, employeeData.first_name, employeeData.last_name, "
                + "user.username from employeeData, user "
                + "where user.username=\'%s\' and employeeData.id=user.employee_id;",u));
            
            while(rs.next()){
                success = true;
                user_id = rs.getInt("id");
                firstname = rs.getString("first_name");
                lastname = rs.getString("last_name");
                username = rs.getString("username");
            }
        }catch(SQLException e){
            success = false;
        }
        
        return success;
    }
    
    private boolean usernameExists(String u) {
        
        boolean exists = false;
        try {            
            ResultSet rs = dba.read_db(String.format(
                "select * from user where username=\'%s\';",u));
            
            if(rs.next()){
                exists = true;
            }
        }catch(SQLException e){
            exists = false;
        }
        
        return exists;
    }
    
    public void setUserId(int id) {
        if(iv.validateInt(id))
            user_id = id;
    }
    
    public int getUserId() {
        return user_id;
    }
    
    public void setUsername(String u) {
        if(iv.validateString(u))
            username = u;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setFirstName(String f) {
        if(iv.validateString(f))
            firstname = f;
    }
    
    public String getFirstName() {
        return firstname;
    }
    
    public void setLastName(String l) {
        if(iv.validateString(l))
            lastname = l;
    }
    
    public String getLastName() {
        return lastname;
    }
    
    public void setAddress(String a) {
        if(iv.validateString(a))
            address = a;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setTitle(String t) {
        if(iv.validateString(t))
            title = t;
    }
    
    public String getTitle() {
        return title;
    }
    
    public boolean addUser() {
        user_id = generateId();
        username = generateUname();
        boolean writeok = false;
        
        try {
           writeok = dba.write_db(String.format(
                   "INSERT INTO employeeData(first_name, last_name, title, "
                   + "address, phone, start_date, account_type) "
                   + "VALUES('%s', '%s', '%s', '%s', "
                   + "'%s', '%s', '1');"
                   ,firstname,lastname,"title","address","phone","2001-01-01"));
        }catch(SQLException e){
            return writeok;
        }
        return writeok;
    }
    
    public boolean modUser() {
        
        return false;
    }
    
    public int generateId() {
        int id = -1;
        
        try {            
            ResultSet rs = dba.read_db("select max(id) from employeeData;");
            while(rs.next()){
                id = rs.getInt("max(id)");                
            }
        }catch(SQLException e){
        }
        id++;
        
        return id;
    }
    
    public String generateUname(){
        String uname = "";
        String first = firstname.toLowerCase();
        String last = lastname.toLowerCase();
        
        if(last.length()>=8){
           for(int i = 0; i<8; i++) {
               uname = uname + last.charAt(i);
           } 
        }
        else if(last.length() < 8){
            uname = last;
            if((uname.length() + first.length()) >= 8) {
                for(int i = 0; i<first.length(); i++) {
                    uname = uname + first.charAt(i);
                    if (uname.length() == 8)
                        break;
                }
            }
            else {
                uname = uname + first;
                
                while (uname.length() < 8) {
                    uname = uname + "0";
                }        
            }
        }
        
        if(!usernameExists(uname)){
            return uname;
        }
        else {
            uname = uname.substring(0, uname.length()-1);
            int i = 0;
            while(true){
                uname = uname + i;
                i++;
                if (!usernameExists(uname)) {
                    return uname;
                }
            }
        }
    }
}
