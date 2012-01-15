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
        
        dba.ConnectDB();
        
        System.out.println(dba.testConn());
    }
}
