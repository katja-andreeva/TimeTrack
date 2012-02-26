
/**
 *
 * @author katja
 */

import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;


public class LoginWindow implements ActionListener
{
  JFrame frame;
  JTextField user;
  JPasswordField password;
  JCheckBox newuser;
  JButton ok, cancel;
  DBAccess dba;
  
  public LoginWindow(DBAccess d)
  {
    frame = new JFrame( "Time Tracker Login" );
    dba = d;
    
    JPanel top = new JPanel();
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    top.setLayout( gbl );
    c.insets = new Insets( 5, 7, 5, 7 );
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0;
    
    c.gridwidth = 1;
    top.add( new JLabel( "Username: " ));
    user = new JTextField( 30 );
    c.gridwidth = GridBagConstraints.REMAINDER;
    top.add( user, c );

    c.gridwidth = 1;
    password = new JPasswordField( 30 );
    c.gridwidth = GridBagConstraints.REMAINDER;
    top.add( new JLabel( "Password: "));
    top.add( password, c );

    frame.getContentPane().add( top, BorderLayout.NORTH );

    JPanel buttons = new JPanel();
    ok = new JButton( "Login" );
    ok.addActionListener( this );
    buttons.add( ok );
    
    cancel = new JButton( "Exit" );
    cancel.addActionListener( this );
    buttons.add( cancel );
    
    frame.getContentPane().add( buttons, BorderLayout.SOUTH );
    
    frame.pack();
    frame.setVisible( true );
    frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e){
            dba.close();
            System.exit(0);
        }
    });
  }

  public void actionPerformed( ActionEvent e )
  {
    if( e.getSource() == ok )
      {
	Authentication login = new Authentication();
        String pw = new String(password.getPassword());
        String username = user.getText();
            try {
                if( !login.authenticate(dba,username,pw))
                  {
                    JOptionPane.showMessageDialog( frame, "There was a problem logging into Time Tracker", "Error", 
                                                   JOptionPane.ERROR_MESSAGE );
                  }
                else
                  {
                    //new AccountWindow(dba, username).setVisible(true);
                      
                    JFrame userframe = new JFrame("Time Tracker");

                    userframe.getContentPane().add(new UserWindow(dba, username), BorderLayout.CENTER);
                    userframe.setSize(500, 500);
                    userframe.setVisible(true);
                    
                    userframe.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e){
                            dba.close();
                            System.exit(0);
                        }
                    });
        
                    frame.setVisible( false );
                  }
            } catch (SQLException ex) {
                Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (e.getSource() == cancel){
            dba.close();
            System.exit(0);
        }
    }
}
