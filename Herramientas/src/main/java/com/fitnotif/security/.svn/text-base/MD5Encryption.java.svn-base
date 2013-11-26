package com.fitnotif.security;

import com.fitnotif.common.Encryption;
import java.security.MessageDigest;

/**
 * Manejo de contrasenias con metodo MD5
 * @author santiago
 * @version 1.0
 */
public class MD5Encryption implements Encryption{

    public MD5Encryption(){
        
    }
    
    @Override
    public String encrypt(String pText) {
        
        byte[] md5hash = new byte[32];
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");            
            md.update(pText.getBytes("iso-8859-1"), 0, pText.length());
            md5hash = md.digest();
        }catch(Exception e){
            
        }
        return convertToHex(md5hash);
    }

    @Override
    public String decrypt(String pText) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * Convierte bytes a String en formato Hex para transporte
     * @param data encryption bytes
     * @return Hex String
     */
    private static String convertToHex(byte[] data) { 
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)){
                    buf.append((char) ('0' + halfbyte));
                }                     
                else{
                    buf.append((char) ('a' + (halfbyte - 10)));
                } 
                    
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
    
    public static void main(String args[]){
        System.out.println( new MD5Encryption().encrypt("1234abcd") );
    } 
    
}
