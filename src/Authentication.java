/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Borrowed functionality from https://www.owasp.org/index.php/Hashing_Java
 * @author katja
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Authentication {
    
    private final static int ITERATION_NUMBER = 1000;
    Connection con;
    
    public Authentication() {
        
    }
    
    public boolean authenticate(DBAccess dba, String login, String password)
           throws SQLException, NoSuchAlgorithmException{
       try {
           boolean userExist = true;
           // INPUT VALIDATION
           if (login==null||password==null){
               // TIME RESISTANT ATTACK
               // Computation time is equal to the time needed by a legitimate user
               userExist = false;
               login="";
               password="";
           }
           con = dba.getConnection();
           PreparedStatement ps = con.prepareStatement("select pass_hash, "
                   + "salt from user where username = ?");
           ps.setString(1,login);
           ResultSet rs = ps.executeQuery();
           
           String digest, salt;
           if (rs.next()) {
               digest = rs.getString("pass_hash");
               salt = rs.getString("salt");
               // DATABASE VALIDATION
               if (digest == null || salt == null) {
                   throw new SQLException("Database inconsistant Salt or Digested Password altered");
               }
               if (rs.next()) { // Should not append, because login is the primary key
                   throw new SQLException("Database inconsistent two CREDENTIALS with the same LOGIN");
               }
           } else { // TIME RESISTANT ATTACK (Even if the user does not exist the
               // Computation time is equal to the time needed for a legitimate user
               digest = "000000000000000000000000000=";
               salt = "00000000000=";
               userExist = false;
           }
 
           byte[] bDigest = base64ToByte(digest);
           byte[] bSalt = base64ToByte(salt);
 
           // Compute the new DIGEST
           byte[] proposedDigest = getHash(ITERATION_NUMBER, password, bSalt);
 
           return Arrays.equals(proposedDigest, bDigest) && userExist;
       } catch (IOException ex){
           throw new SQLException("Database inconsistant Salt or Digested Password altered");
       }
   }
    
    public String[] createHashSalt(String password)
           throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String[] hs = new String[2];
        
        if (password!=null){
            // Uses a secure Random not a simple Random
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // Salt generation 64 bits long
            byte[] bSalt = new byte[8];
            random.nextBytes(bSalt);
            // Digest computation
            byte[] bDigest = getHash(ITERATION_NUMBER,password,bSalt);
            hs[0] = byteToBase64(bDigest);
            hs[1] = byteToBase64(bSalt);
 
        }
        
        return hs;
    }
    
    
    
    /**
    * From a password, a number of iterations and a salt,
    * returns the corresponding digest
    * @param iterationNb int The number of iterations of the algorithm
    * @param password String The password to encrypt
    * @param salt byte[] The salt
    * @return byte[] The digested password
    * @throws NoSuchAlgorithmException If the algorithm doesn't exist
    */
   public byte[] getHash(int iterationNb, String password, byte[] salt) 
           throws NoSuchAlgorithmException, UnsupportedEncodingException {
       MessageDigest digest = MessageDigest.getInstance("SHA-1");
       digest.reset();
       digest.update(salt);
       byte[] input = digest.digest(password.getBytes("UTF-8"));
       for (int i = 0; i < iterationNb; i++) {
           digest.reset();
           input = digest.digest(input);
       }
       return input;
   }
   
    /**
    * From a base 64 representation, returns the corresponding byte[] 
    * @param data String The base64 representation
    * @return byte[]
    * @throws IOException
    */
   public static byte[] base64ToByte(String data) throws IOException {
       BASE64Decoder decoder = new BASE64Decoder();
       return decoder.decodeBuffer(data);
   }
 
   /**
    * From a byte[] returns a base 64 representation
    * @param data byte[]
    * @return String
    * @throws IOException
    */
   public static String byteToBase64(byte[] data){
       BASE64Encoder endecoder = new BASE64Encoder();
       return endecoder.encode(data);
   }
}
