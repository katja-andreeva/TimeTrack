/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    double payrate;
    int acctype;
    
    DBAccess dba;
    InputValidator iv;
            
    public Account(DBAccess d) {
        user_id = -1;
        username = "";
        password = "";
        
        firstname = "";
        lastname = "";
        title = "";
        address = "";
        phone = "";
        startdate = "";
        payrate = 0;
        acctype = -1;
        
        
        dba = d;
        iv = new InputValidator();
    }
    
    public Account(int uid, String uname, String pw, String fname, String lname,
            String t, String addr, String p, String start, double r, int atype) {
        user_id = uid;
        username = uname;
        password = pw;
        
        firstname = fname;
        lastname = lname;
        title = t;
        address = addr;
        phone = p;
        startdate = start;
        payrate = r;
        acctype = atype;
        
        dba = new DBAccess();
    }
    
    public Account(String pw, String fname, String lname,
            String t, String addr, String p, String start, double r, int atype) {
        password = pw;
        
        firstname = fname;
        lastname = lname;
        title = t;
        address = addr;
        phone = p;
        startdate = start;
        payrate = r;
        acctype = atype;
        
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
                + "employeeData.last_name, employeeData.title, "
                + "employeeData.address, employeeData.phone, "
                + "employeeData.start_date, employeeData.account_type, "
                + "user.username, salary.rate from employeeData, user, salary "
                + "where employeeData.id=%d and user.employee_id=%d "
                + "and salary.employee_id=%d;",id, id, id));
            
            while(rs.next()){
               success = true;
               user_id = rs.getInt("id");
               firstname = rs.getString("first_name");
               lastname = rs.getString("last_name");
               username = rs.getString("username");
               title = rs.getString("title");
               address = rs.getString("address");
               phone = rs.getString("phone");
               startdate = rs.getString("start_date");
               acctype = rs.getInt("account_type");
               payrate = rs.getDouble("rate");
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
                + "employeeData.title, employeeData.address, employeeData.phone, "
                + "employeeData.start_date, employeeData.account_type, "
                + "user.username, salary.rate from employeeData, user, salary"
                + "where user.username=\'%s\' and employeeData.id=user.employee_id "
                + "and salary.employee_id=user.employee_id;",u));
            
            while(rs.next()){
                success = true;
                user_id = rs.getInt("id");
                firstname = rs.getString("first_name");
                lastname = rs.getString("last_name");
                username = rs.getString("username");
                title = rs.getString("title");
                address = rs.getString("address");
                phone = rs.getString("phone");
                startdate = rs.getString("start_date");
                acctype = rs.getInt("account_type");
                payrate = rs.getDouble("rate");
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
    
    public void setTitle(String t) {
        if(iv.validateString(t))
            title = t;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setAddress(String a) {
        if(iv.validateString(a))
            address = a;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setPhone(String p) {
        if(iv.validateString(p))
            phone = p;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setStartDate(String d) {
        startdate = d;
    } 
    
    public String getStartDate() {
        return startdate;
    }
    
    public void setRate(double r) {
        payrate = r;
    }
    
    public double getRate(){
        return payrate;
    }
    
    public void setAccountType(int t) {
        acctype = t;
    }
    
    public int getAccountType() {
        return acctype;
    }
    
    public boolean addUser() {
        user_id = generateId();
        username = generateUname();
        boolean writeok = false;
        String[] hs = null;
        
        Authentication a = new Authentication();
        try {
            hs = a.createHashSalt(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(dba.write_db(String.format(
                   "INSERT INTO employeeData(id, first_name, last_name, title, "
                   + "address, phone, start_date, account_type) "
                   + "VALUES('%d', '%s', '%s', '%s', '%s', "
                   + "'%s', '%s', '1');"
                   ,user_id,firstname,lastname,title,address,phone,startdate))) {
               
                if(dba.write_db(String.format(
                   "INSERT INTO user(username, employee_id, pass_hash, salt) "
                   + "VALUES('%s', '%s', '%s', '%s');"
                   ,username,user_id,hs[0],hs[1]))){
                    writeok = dba.write_db(String.format("INSERT INTO "
                            + "salary(employee_id,rate) VALUES('%d','%f');",user_id,payrate));
                }

            } else {
                writeok = false;
            }
        }catch(SQLException e){
            return writeok;
        }
        return writeok;
    }
    
    public boolean modUser() {
        boolean writeok = false;
        try {
            if(dba.write_db(String.format(
                   "UPDATE employeeData first_name='%d', last_name='%s', title='%s', "
                   + "address='%s', phone='%s', start_date='%s', account_type='%s' "
                   + "where id=\'%d\';"
                   ,firstname,lastname,title,address,phone,startdate,user_id))){
                dba.write_db(String.format("UPDATE salary rate='%f' "
                        + "where id=\'%d\';",payrate));
            }
        }catch(SQLException e){
            return writeok;
        }
        return writeok;
    }
    
    private int generateId() {
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
    
    private String generateUname(){
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
