/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import unesco.elearning.database.DatabaseConn;
import unesco.elearning.model.StudentList;

/**
 *
 * @author SAMSON KAYODE
 */
public class StudentSearch {
    
    SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy");
    
    DatabaseConn db = new DatabaseConn();
    String tmpd = System.getProperty("java.io.tmpdir");
    
    public static Image scaledImages;
    //Connection cn = db.Connection();
    Connection cn = LoginPage.cn;
    
    //MyConnection mycon = new MyConnection();
    
    
    
    //Find Students..
    
    public List<StudentList> findStudent(String name, String more){

        List<StudentList> reps = new ArrayList<>();
        try{
           
            /*
            String QUERY="SELECT FIRSTNAME, LASTNAME, STUDENTID, DOB, SEX, "
                    + "STYPE, datediff(Year, dob, getdate()) FROM PROFILES WHERE STUDENTID LIKE '%"+name+"%' OR FIRSTNAME LIKE '%"+name+"%' OR LASTNAME LIKE '%"+name+"%' AND ACCOUNT_TYPE='STUDENT'";
            */
            
            String QUERY="SELECT FIRSTNAME, LASTNAME, STUDENTID, DOB, SEX, "
                    + "STYPE, (julianday(CURRENT_TIMESTAMP) - julianday(DOB)) FROM PROFILES WHERE STUDENTID LIKE '%"+name+"%' "
                    + "OR FIRSTNAME LIKE '%"+name+"%' OR LASTNAME LIKE '%"+name+"%'"+more;
            
            System.out.println("QUERY "+QUERY);
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement(QUERY);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                StudentList ls = new StudentList();
                
                String a = rs.getString(1).trim();
                String b = rs.getString(2).trim();
                String fullname = a+" "+b;
                
                String id = rs.getString(3).trim();
                
                String date1 = rs.getString(4);
                Date dates = sdfIn.parse(date1);
                String dob = sdfOut.format(dates);
                
                String sex = rs.getString(5).trim();
                
                String stype = rs.getString(6).trim();
                
                int xx = rs.getInt(7)/365;
                
                ls.setDob(dob+" ["+String.valueOf(xx)+" Yrs]");
                ls.setId(id);
                ls.setName(fullname);
                ls.setSex(sex);
                ls.setType(stype);
                
                reps.add(ls);
                
            }
            rs.close();
            ps.close();
            //cn.close();
        }
        catch(Exception nn){
            System.out.println("Error retrieving student data "+nn);
        }
        
        return reps;
    }
    
    public Image getImage(String stID){
        
        try
        {
            
            //Connection cn = db.Connection();
            
            PreparedStatement ps = cn.prepareStatement("Select u_image from PROFILES where StudentID=?");
            
            ps.setString(1, stID);
            
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {

                
                byte[] vv = rs.getBytes(1);
                
                RandomAccessFile raf = new RandomAccessFile(tmpd+"UN.png","rw");
                    raf.write(vv);
                    raf.close();
                    File kk = new File(tmpd+"UN.png");                    
                    BufferedImage images = ImageIO.read(kk);
                    scaledImages =  new Image("file:"+tmpd+"UN.png", 253, 200, true, true);
                    
            }
            
            else
                
            {
                try{
                scaledImages = new Image("/unesco/elearning/image/no_img_profile.png");    
                }
                catch(Exception nn){
                    System.out.println("Error loading default image "+nn);
                }
                
            }
            rs.close();
            ps.close();
            //cn.close();
        }
        
        catch(SQLException | IOException nn)
        {
            
            System.out.println(nn);
            
        }
        
        return scaledImages;
    }
    
    //Delete Profile..
    
    public int removeProfile(String studentID, String username){
        
        int x = 0;
        
        try{
           
            String QUERY="DELETE FROM PROFILES WHERE STUDENTID=? AND USERNAME=? ";
            
            PreparedStatement ps = cn.prepareStatement(QUERY);
            
            ps.setString(1, studentID);
            ps.setString(2, username);
            
            ps.executeUpdate();
            
            System.out.println("Profile deleted");
            x =1;
            
        }
        catch(Exception nn){
            
            System.out.println("Error removing profile "+nn);
            x=0;
        }
        
        return x;
        
    }
    
    //Load all profiles....
    
    public List<StudentList> loadProfiles(){

        List<StudentList> reps = new ArrayList<>();
        try{
           
            String QUERY="SELECT FIRSTNAME, LASTNAME, STUDENTID, USERNAME, PASSWORD, ACCOUNT_TYPE FROM PROFILES WHERE ACCOUNT_TYPE!='ADMINISTRATOR' ";
            
            PreparedStatement ps = cn.prepareStatement(QUERY);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                StudentList ls = new StudentList();
                
                String a = rs.getString(1).trim();
                String b = rs.getString(2).trim();
                String fullname = a+" "+b;
                
                String id = rs.getString(3).trim();
                String uname = rs.getString(4).trim();
                String upass = rs.getString(5).trim();
                String atype = rs.getString(6).trim();
                                
                ls.setId(id);
                ls.setName(fullname);
                ls.setUsername(uname);
                ls.setPassword(upass);
                ls.setType(atype);
                
                reps.add(ls);
                
            }
            rs.close();
            ps.close();
            //cn.close();
        }
        catch(Exception nn){
            System.out.println("Error retrieving student data "+nn);
        }
        
        return reps;
    }
    
}
