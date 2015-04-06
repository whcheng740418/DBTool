/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whc.mysql;


import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author whcheng
 */
public class MySQL {
    
    private Connection conn;
    private String driver = "com.mysql.jdbc.Driver"; 
    
    private static MySQL mysql = null;
    
    private MySQL(){
        
    }
    
    public static MySQL getInstance(){
        if(mysql==null){
            mysql = new MySQL();
        }
        return mysql;
    }
    
    public boolean connect(String url,String user,String passWord) {
        try { 
            Class.forName(driver); 
            conn = DriverManager.getConnection(url, user, passWord);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public void close(){
        try {
            if(!conn.isClosed()) conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean execute(String sql) throws SQLException{
        Statement statement = null;
        try {
            statement = conn.createStatement();
            return statement.execute(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
           return false;
        }finally{
            statement.close();
        }
        
    }
    
    public ArrayList<Object> executeQuery(String sql) throws SQLException{
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet res = statement.executeQuery(sql);
            int count = res.getMetaData().getColumnCount();
            ArrayList<Object> list = new ArrayList<>();
            
            String[] column = new String[count];
            for(int i=1;i<=count;i++){
                column[i-1] = res.getMetaData().getColumnName(i);
            }
            list.add(column);
            
            while(res.next()){
                String[] str = new String[count];
                for(int i=1;i<=count;i++){
                    str[i-1]=res.getString(i);
                }
                list.add(str);
            }
             return list;
        } catch (SQLException ex) {
           return null;
        }finally{
            statement.close();
        }
    }
    
    
}
