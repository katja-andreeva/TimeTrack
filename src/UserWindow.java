/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */
import javax.swing.*;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.*;

public class UserWindow extends JPanel {
    
    private DBAccess dba;
    private Account acc;
    
    public UserWindow(DBAccess d, String u) {
        
        dba = d;
        acc = new Account(dba);
        acc.setUsername(u);
        acc.loadUser();
        
        ImageIcon icon = new ImageIcon("");
        JTabbedPane tabbedPane = new JTabbedPane();

        Component panel1 = makeAccountPanel();
        tabbedPane.addTab("Account", icon, panel1, "Account Info");
        tabbedPane.setSelectedIndex(0);

        Component panel2 = makeTimeSheetPanel();
        tabbedPane.addTab("Time Sheet", icon, panel2, "Time Sheet");
        
        if(acc.getAccountType()==2){
            Component panel3 = makeCreateUserPanel();
            tabbedPane.addTab("Add User", icon, panel3, "Add new user");
        }

        //Add the tabbed pane to this panel.
        setLayout(new GridLayout(1, 1)); 
        add(tabbedPane);
    }

    private Component makeAccountPanel() {
        AccountInfo accpanel = new AccountInfo(dba,acc);
        return accpanel;
    }
    
    private Component makeTimeSheetPanel() {
        TimeSheet tspanel = new TimeSheet(dba,acc);
        return tspanel;
    }
    
    private Component makeCreateUserPanel() {
        CreateAccountInfo newaccpanel = new CreateAccountInfo(dba);
        return newaccpanel;
    }
}
