/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.sql.*;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import unesco.elearning.database.DatabaseConn;

/**
 *
 * @author SAMSON KAYODE
 */
public class LoginPage {
    
    public static String dts;
    
    public static Image scaledImages;
    
    public static String ufirstname="", ulastname="", udob="", usex="", ustype="", username="", password="", atype="", uID="";
    
    String tmpd = System.getProperty("java.io.tmpdir");
    
    static Image image;
    
    public static File mainfile;
    
    public static DatabaseConn db = new DatabaseConn();
    
    public static Connection cn = db.Connection();
    
    public int validateLogin(String user, String pass, String stype)
            
    {
        int res = 1;
        
        try
        {
            
            //Connection cn = db.Connection();
            
            PreparedStatement ps = cn.prepareStatement("Select firstname, lastname, dob, sex, stype,"
                    + " u_image, username, password, ACCOUNT_TYPE, studentID from PROFILES where username=? and password=?");
            
            ps.setString(1, user);
            ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                
                ufirstname = rs.getString(1).trim();
                ulastname = rs.getString(2).trim();
                 dts = rs.getString(3);
                udob = String.valueOf(dts);
                usex = rs.getString(4).trim();
                ustype = rs.getString(5).trim();
                
                byte[] vv = rs.getBytes(6);
                
                username = rs.getString(7).trim();
                password = rs.getString(8).trim();
                
                atype = rs.getString(9).trim();
                uID = rs.getString(10).trim();
                
                RandomAccessFile raf = new RandomAccessFile(tmpd+"UN.png","rw");

                    raf.write(vv);
                    raf.close();
                    File kk = new File(tmpd+"UN.png");
                    
                    mainfile = kk;
                    
                    BufferedImage images = ImageIO.read(kk);
                    scaledImages =  new Image("file:"+tmpd+"UN.png", 176, 160, true, true);
                    
                    if(atype.equals("STUDENT")&& ustype.equals(stype)){
                        
                        res = 0;
                    }
                    
                    if(atype.equals("LEARNER")&& ustype.equals(stype)){
                        
                        res = 0;
                    }
                    
                    if(atype.equals("TEACHER")&& ustype.equals(stype)){
                        
                        res = 0;
                    }
                    
                    if(atype.equals("FACILITATOR")&& ustype.equals(stype)){
                        
                        res = 0;
                    }
                    
                    if(atype.equals("ADMINISTRATOR")){
                        
                        res = 0;
                    }

                //res = 0;
            }
            
            else
                
            {
                
                res = 1;
                
            }
            rs.close();
            ps.close();
            //cn.close();
        }
        
        catch(Exception nn)
        {
            
            System.out.println(nn);
            nn.printStackTrace();
            res = 2;
        }
        
        return res;
    }
    
    public int checkUser(String uname, String upass, String stID)
    {
        int res = 1;
        
        try
        {
            
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("Select * from profiles where username=? and password =? or studentID=?");
            
            ps.setString(1, uname);
            ps.setString(2, upass);
            ps.setString(3, stID);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                
            {
                //profile already exist..
                
                res = 1;
            }
            
            else{
                //No profile exist, register..
                
                res = 0;
            }
            
            rs.close();
            ps.close();
            //cn.close();
        }
        
        catch(Exception nn)
        {
            res =2;
        }
        
        return res;
        
    }
    
    public int insertProfile(String fname, String lname, String uname, String upass, String dob, String sex, String atype, File mainfile, String stype, String studentID){
        
        int res = 1;
        
       
        try
        {
            FileInputStream fis = new FileInputStream(mainfile);
            
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("INSERT INTO PROFILES (USERNAME, PASSWORD, FIRSTNAME, LASTNAME, DOB, SEX, ACCOUNT_TYPE, U_IMAGE, STYPE, STUDENTID) VALUES(?,?,?,?,?,?,?,?,?,?)");
            
            ps.setString(1, uname);
            ps.setString(2, upass);
            ps.setString(3, fname);
            ps.setString(4, lname);
            ps.setString(5, dob);
            ps.setString(6, sex);
            ps.setString(7, atype);
            ps.setBinaryStream(8, (InputStream) fis, (int) mainfile.length());
            ps.setString(9, stype);
            ps.setString(10,  studentID);
            
            ps.executeUpdate();
            
            res =0;
            
            ps.close();
            //cn.close();
        }
        catch(FileNotFoundException | SQLException nn)
        {
            res = 1;
            System.out.println(nn);
        }
               
        return res;
    }
    
    
    public int editUserProfile(String fname, String lname, String uname, String upass, String dob, String sex, String atype, File mainfile, String stype, String studentID){
        
        int res = 1;
        
        try
        {
            FileInputStream fis = new FileInputStream(mainfile);
            
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("UPDATE PROFILES SET USERNAME=?, PASSWORD=?, FIRSTNAME=?, LASTNAME=?, DOB=?, "
                    + "SEX=?, ACCOUNT_TYPE=?, U_IMAGE=?, STYPE=?, STUDENTID=? WHERE USERNAME=? AND PASSWORD=?");
            
            ps.setString(1, uname);
            ps.setString(2, upass);
            ps.setString(3, fname);
            ps.setString(4, lname);
            ps.setString(5, dob);
            ps.setString(6, sex);
            ps.setString(7, atype);
            ps.setBinaryStream(8, (InputStream) fis, (int) mainfile.length());
            ps.setString(9, stype);
            ps.setString(10, studentID);
            
            ps.setString(11, username);
            ps.setString(12, password);
            
            
            ps.executeUpdate();
            
            System.out.println("USERNAME - "+username);
            System.out.println("PASSWORD - "+password);
            
            res = 0;
            
            ps.close();
            //cn.close();
            
        }
        
        catch(FileNotFoundException | SQLException nn){
            
            System.out.println("Error updating user profile");
            res =1;
        }
        return res;
    }
    
    public void loadData()
    {
        try
        {
            
            //Connection cn = db.Connection();
            
            PreparedStatement ps = cn.prepareStatement("Select firstname, lastname, dob, sex, stype, u_image, "
                    + "username, password, ACCOUNT_TYPE, studentID from PROFILES where username=? and password=?");
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                
                ufirstname = rs.getString(1).trim();
                ulastname = rs.getString(2).trim();
                 dts = rs.getString(3);
                udob = String.valueOf(dts);
                usex = rs.getString(4).trim();
                ustype = rs.getString(5).trim();
                
                byte[] vv = rs.getBytes(6);
                
                username = rs.getString(7).trim();
                password = rs.getString(8).trim();
                
                atype = rs.getString(9).trim();
                uID = rs.getString(10).trim();
                
                RandomAccessFile raf = new RandomAccessFile(tmpd+"UN.png","rw");

                    raf.write(vv);
                    raf.close();
                    File kk = new File(tmpd+"UN.png");
                    
                    mainfile = kk;
                    
                    BufferedImage images = ImageIO.read(kk);
                    scaledImages =  new Image("file:"+tmpd+"UN.png", 150, 150, true, true);
                
                
            }
            rs.close();
            ps.close();
            //cn.close();
        }
            catch(Exception nn){
                    
                    }
    }
    
    //Logs..
    
    //login log..
    
    public int loginLog(String id, String activity, String name){
        int res = 1;
        
        try{
            //Connection cn = db.Connection();
            
            PreparedStatement ps = cn.prepareStatement("INSERT INTO LOGS(ID, DATE, ACTIVITY, NAME) VALUES(?,CURRENT_TIMESTAMP,?,?)");
            
            ps.setString(1, id);
            ps.setString(2, activity);
            ps.setString(3, name);
            ps.executeUpdate();
            
            System.out.println("Log saved");
            ps.close();
            //cn.close();
            
        }
        catch(Exception nn){
            System.out.println("error saving log "+nn);
        }
        
        return res;
    }
    
    //JSON METHODS...
    
    public int JSONLogView(String id, String activity, String name, String date){
        int res = 0;
        
        try{
            //Connection cn = db.Connection();
            
            PreparedStatement ps = cn.prepareStatement("INSERT INTO LOGS(ID, DATE, ACTIVITY, NAME) VALUES(?,?,?,?)");
            
            ps.setString(1, id);
            ps.setString(2, activity);
            ps.setString(3, name);
            ps.setString(4, date);
            ps.executeUpdate();
            
            System.out.println("Log Data Moved");
            ps.close();
            //cn.close();
            
            res++;
            
        }
        catch(Exception nn){
            System.out.println("error moving log data "+nn);
            res=0;
        }
        
        return res;
    }
    
    public int JSONCourseView(String studentID, String course, String term, String lesson, String date, String edate){
        
        int res=0;
        
        try{
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("INSERT INTO COURSEVIEW(STUDENTID, COURSE, TERM, LESSON, DATE, EDATE) VALUES(?,?,?,?,?,?)");
            
            ps.setString(1, studentID);
            ps.setString(2, course);
            ps.setString(3, term);
            ps.setString(4, lesson);
            ps.setString(5, date);
            ps.setString(6, edate);
            
            ps.executeUpdate();
            
            res++;
            
            ps.close(); 
            //cn.close();
            
        }
        catch(Exception nn){
            System.out.println("Error saving course view "+nn);
            res =0;
        }
        
        return res;
    }
    
    //Quiz...
    
    public int JSONQuizView(String studentID, String course, String term, String lesson, long score, String date){
        
        int res = 0;
        
        try{
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("INSERT INTO QUIZ(STUDENTID, COURSE, TERM, LESSON, SCORE, DATE) VALUES(?,?,?,?,?,?)");
            
            ps.setString(1, studentID);
            ps.setString(2, course);
            ps.setString(3, term);
            ps.setString(4, lesson);
            ps.setDouble(5, score);
            ps.setString(6, date);
            
            ps.executeUpdate();
            
            res++;
            
            ps.close(); 
            //cn.close();
            
        }
        catch(Exception nn){
            System.out.println("Error saving result "+nn);
            res =0;
        }
        
        
        return res;
    }
    
    public int JSONProfileView(String fname, String lname, String uname, String upass, String dob, String sex, String atype, byte[] mainfile, String stype, String studentID){
        
        int res = 1;
        
       
        try
        {
           //FileInputStream fis = new FileInputStream(mainfile);
            
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("INSERT INTO PROFILES (USERNAME, PASSWORD, FIRSTNAME, LASTNAME, DOB, SEX, ACCOUNT_TYPE, U_IMAGE, STYPE, STUDENTID) VALUES(?,?,?,?,?,?,?,?,?,?)");
            
            ps.setString(1, uname);
            ps.setString(2, upass);
            ps.setString(3, fname);
            ps.setString(4, lname);
            ps.setString(5, dob);
            ps.setString(6, sex);
            ps.setString(7, atype);
            ps.setBytes(8, mainfile);
            ps.setString(9, stype);
            ps.setString(10,  studentID);
            
            ps.executeUpdate();
            
            res =0;
            
            ps.close();
            //cn.close();
        }
        catch(Exception nn)
        {
            res = 1;
            System.out.println(nn);
        }
               
        return res;
    }
    
}
