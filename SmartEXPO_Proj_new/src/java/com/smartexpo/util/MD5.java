/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.util;

import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MD5 {

    /**
     * Convert a string to MD5 encrypted
     * @param str String to be encrypted
     * @return MD5 corresponding
     */
    public static String md5(String str) {
        String s = str;
        if (s == null) {
            return "";
        } else {
            String value = null;
            MessageDigest md5 = null;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(MD5.class.getName()).log(Level.SEVERE, null, ex);
            }
            sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
            try {
                value = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
            } catch (Exception ex) {
            }
            return value;
        }
    }
}
