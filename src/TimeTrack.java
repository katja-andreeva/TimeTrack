/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */
public class TimeTrack {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DBAccess dba = new DBAccess();
        LoginWindow lw = new LoginWindow(dba);
    }
}
