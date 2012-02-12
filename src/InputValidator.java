/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author katja
 */
public class InputValidator {
    
    String inputstring;
    int inputint;
    
    public InputValidator() {
        inputstring = "";
        inputint = -1;
    }
    
    public boolean validateString(String s) {
        inputstring = s;
        
        if (inputstring != null && inputstring.length() > 0) {
            return true;
        }
        
        return false;
    }
    
    public boolean validateInt(int i) {
        inputint = i;
        
        if (inputint >0) {
            return true;
        }
        
        return false;
    }
   
}
