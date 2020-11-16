package com.luck.dataService.common;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserConstants {
    public static final String REDIS_USER = "user.home";
    public static final int REDIS_USER_TIME = 86400;
    private UserConstants(){}
    public static String[] values(){
        Field[] fields=UserConstants.class.getFields();
        String[] s = new String[fields.length];
        for(int i=0,n=fields.length;i<n;i++){
            try {
                Field f = fields[i];
                s[i] = (String) f.get(null);
            } catch (Exception ex) {
                Logger.getLogger(UserConstants.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    return s;
  }

    public static Set<String> asSet() {
        return new HashSet<String>(Arrays.asList(values()));
  }

  public static void main(String[] args) {
        System.out.println("values="+Arrays.asList(values()));
        System.out.println("set="+asSet());
  }
}
