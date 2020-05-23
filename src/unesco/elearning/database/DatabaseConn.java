/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author SAMSON KAYODE
 */
public class DatabaseConn {
    
    String driver = "net.sourceforge.jtds.jdbc.Driver";
    String url = "jdbc:jtds:sqlserver://127.0.0.1:1433/UN_ELEARN";
    String username = "HYPEN";
    String password = "S3CR3T";
    private String conString = "jdbc:sqlite:";
            private String db_name = "SMLA.db";
   
    
    public Connection Connection(){
        
        //String dataFolder2 = System.getenv("USERS");
        
        String dataFolder2 = System.getProperty("user.home");
        
        
        //String dataFolder2 = "D://COMPLETE SMLA CONTENT/DATABASES";
        
        //File f2 = new File(dataFolder2+"/SMLA");
        
        String cnc=conString+dataFolder2+"/";
        System.out.println("CNC _-----------"+cnc);
        
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(cnc+db_name);
            
            return con;
        }
        catch(Exception ex){
            System.out.println("Error connectiong to database "+ex);
            
            return null;
        }

    }
    
    public int initTables(){
        
        int res = 1;
        try{
            
            String Q1 = "CREATE TABLE IF NOT EXISTS COURSEVIEW( STUDENTID TEXT, COURSE TEXT, TERM TEXT, "
                    + "LESSON TEXT, DATE DATETIME DEFAULT CURRENT_TIMESTAMP, EDATE DATETIME DEFAULT CURRENT_TIMESTAMP, CID INTEGER PRIMARY KEY AUTOINCREMENT, SYNC TEXT DEFAULT 'NO')";
            
            String Q2 = " CREATE TABLE IF NOT EXISTS LOGS(ID TEXT, DATE DATETIME DEFAULT CURRENT_TIMESTAMP, ACTIVITY TEXT, NAME TEXT, SYNC TEXT DEFAULT 'NO')";
            
            String Q3 = " CREATE TABLE IF NOT EXISTS QUIZ (STUDENTID TEXT, COURSE TEXT, TERM TEXT, LESSON TEXT, "
                    + "SCORE FLOAT, DATE DATETIME DEFAULT CURRENT_TIMESTAMP, QID INTEGER PRIMARY KEY AUTOINCREMENT, SYNC TEXT DEFAULT 'NO')";
            
            String Q4 = " CREATE TABLE IF NOT EXISTS SETTINGS (C_LOC TEXT, LANGUAGE TEXT, SCORE FLOAT, SERVER TEXT);";
            
            String Q5 = " CREATE TABLE IF NOT EXISTS SYNC (DATE DATETIME DEFAULT CURRENT_TIMESTAMP);";
            
            String QUERY = " CREATE TABLE IF NOT EXISTS PROFILES(USERNAME TEXT, PASSWORD TEXT, FIRSTNAME TEXT, LASTNAME TEXT, "
                    + "DOB DATETIME, DOR DATETIME DEFAULT CURRENT_TIMESTAMP, SEX TEXT, ACCOUNT_TYPE TEXT, SID INTEGER PRIMARY KEY AUTOINCREMENT, U_IMAGE BLOB, STYPE TEXT, STUDENTID TEXT, SYNC TEXT DEFAULT 'NO')";
            
            
            
            Statement st = Connection().createStatement();
            st.execute(Q1);
            st.execute(Q2);
            st.execute(Q3);
            st.execute(Q4);
            st.execute(Q5);
            st.execute(QUERY);
            
            res =0;
            
        }
        catch(Exception nn){
            System.out.println("ERROR CREATING DATABASE TABLES "+nn);
            res = 1;
        }
        return res;
    }
    
    public void confirmSettings(){
        
        try{
        Statement st = Connection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM SETTINGS");
        
        if(rs.next()){
            //Settings exist..
            
        }else{
            String query = "INSERT INTO SETTINGS(C_LOC, LANGUAGE, SCORE, SERVER) VALUES('C:\\COMPLETE SMLA CONTENT','EN',80,'192.168.0.1#10898') ";
                   
            int a = insertSettings(query);
            
            adminProfile();
            
            if(a==0){
                System.out.println("Data Inserted");
            }else{
                System.out.println("Data Not Inserted");
            }
        }
        rs.close();
        st.close();
        Connection().close();
        }
        catch(Exception nn){
            System.out.println("Error confirming data existence "+nn);
            
        }
    }
    
    public void adminProfile(){
        
        String query = "INSERT INTO PROFILES(USERNAME, PASSWORD, FIRSTNAME, LASTNAME, DOB, SEX, ACCOUNT_TYPE, U_IMAGE, STYPE, STUDENTID) VALUES('UNESCO', 'UNE$C0', 'UNESCO', 'ADMIN', '01-01-1960', 'F', 'ADMINISTRATOR','0X', 'NOT APPLICABLE', 'UNES001') ";
        
        try{
            Statement st = Connection().createStatement();
            st.execute(query);
            
            System.out.println("Admin Data Inserted");
        }
        catch(Exception nn){
            System.out.println("Error inserting Admin Data "+nn);
            return;
        }
        
    }
    
    public int insertSettings(String insert){
        int res =1;
        
        try{
            Statement st = Connection().createStatement();
            st.execute(insert);
            res=0;
        }
        catch(Exception nn){
            System.out.println("Error inserting settings "+nn);
            res=1;
        }
        
        return res;
    }
    
    
}
