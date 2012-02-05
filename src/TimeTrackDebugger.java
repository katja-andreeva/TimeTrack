
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
        
        Account a = new Account();
        
        a.loadUserById(1);
        System.out.println(a.getFirstName()+" "+a.getLastName()+", ID: "+a.getUserId());
    }
}
