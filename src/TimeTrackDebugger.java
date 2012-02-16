
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */
public class TimeTrackDebugger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DBAccess dba =  new DBAccess();
        
        try {
            System.out.println(dba.testConn());
        } catch (SQLException ex) {
            Logger.getLogger(TimeTrackDebugger.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Account a = new Account();
        
        /*Account a = new Account("seppo123","toni","lampela","CEO","jokutie 2, helsinki","044124567","2011-06-06",1);
        if(a.addUser()){
            System.out.println("user added");
        }else {
            System.out.println("user add failed");
        }*/
        
        Authentication a = new Authentication();
        try {
            if(a.authenticate(dba, "lampela0", "seppo123")){
                System.out.println("User authenticated");
                
            }else {
                System.out.println("Invalid credentials");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeTrackDebugger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TimeTrackDebugger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
