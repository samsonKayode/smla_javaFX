/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.scene.control.TreeItem;
import unesco.elearning.database.DatabaseConn;
import unesco.elearning.language.Language;
import unesco.elearning.model.LibraryModel;

/**
 *
 * @author SAMSON KAYODE
 */
public class ContentSettings {
    
    public static int resx=0;
    int res =1;
    Language lang = new Language();
    
    public static String C_LOC, LANG, SERVER;
    public static float dscore=0;
    
    //DatabaseConn db = new DatabaseConn();
    Connection cn = LoginPage.cn;
    
    public int inserContenttDirectory(String str, String lang, Float score, String server){
        
        try{
            
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("UPDATE SETTINGS SET C_LOC=?, LANGUAGE=?, SCORE=?, SERVER=?");
            
            ps.setString(1, str);
            ps.setString(2, lang);
            ps.setFloat(3, score);
            ps.setString(4, server);
            ps.executeUpdate();
            
            res = 0;
            
            ps.close();
            //cn.close();
        }
        
        catch(Exception nn){
            
            System.out.println("Error saving settings "+nn);
            res =1;
        }
        
        return res;
    }
    
    
    public int getContentDirectory()
    {
        int res =1;
        
        try{
            //Connection cn = db.Connection();
            
            Statement stat = cn.createStatement();
            ResultSet rs = stat.executeQuery("Select c_loc, language, score, server from SETTINGS");
            
            if(rs.next()){
                //result found..
                C_LOC = rs.getString(1).trim();
                LANG = rs.getString(2).trim();
                dscore = rs.getFloat(3);
                SERVER = rs.getString(4).trim();
                res = 0;
            }
            
            else{
                
                //No result found..
                res = 1;
            }
            
            rs.close();
            stat.close();
            //cn.close();
            
        }
        catch(Exception nn){
            System.out.println("Error loading content directory "+nn);
            res = 2;
        }
                
        return res;
    }
    
    //load courses list..
    
    
    
    public List loadFiles(String location){

	List<String> results = new ArrayList<String>();
        int sn =0;
	try

{

    File[] files = new File(location).listFiles();
    
    //If this pathname does not denote a directory, then listFiles() returns null. 
Arrays.sort(files, new AlphanumFileComparator() );
    
    
    for (File file : files) { 
        
        System.out.println(file.getName());
        
        if (file.isDirectory()) {
            //String a = stripExtension(String.valueOf(file.getName()));
            sn++;
            String a = file.getName();
            
            String sna = String.valueOf(sn);
            
            if(a.equals("QUIZ")){
                
                //Not adding the quiz folder.
                
            }else{
                results.add(sna+"/"+a);
            }
            
            
    }
        else{
            //Load 
        }
}
}


catch(Exception nn){

System.out.println("ERRR");

}

    return results;
    
    }
    
    //Load NonFormal..

    public List loadNonFormal(String location) {

        List<String> results = new ArrayList<String>();
        int sn = 0;
        try

        {
            File[] files = new File(location).listFiles();
            Arrays.sort(files, new AlphanumFileComparator());

            for (File file : files) {
                if (file.isDirectory()) {

                }else{

                    String a = file.getName();

                    if(a.endsWith(".mp4")||a.endsWith(".MP4")){

                        String ax = lang.stripExtension(String.valueOf(file.getName()));
                        sn++;
                        String sna = String.valueOf(sn);

                        results.add(sna + "/" + ax);

                    }
                }
            }
        } catch (Exception nn) {

            System.out.println("ERROR LOADING NON FORMAL FILES "+nn);
        }

        return results;

    }
    
    //Quiz retrieval..
    
    public List loadQuizOne(String location){

	List<String> results = new ArrayList<String>();
        int sn =0;
	try

{

    File[] files = new File(location).listFiles();
    //If this pathname does not denote a directory, then listFiles() returns null. 

    Arrays.sort(files, new AlphanumFileComparator() );
    
    for (File file : files) {
        if (file.isDirectory()) {
            //String a = stripExtension(String.valueOf(file.getName()));
            sn++;
            String a = file.getName();
            results.add(a);
    }
        else{
            //Load 
        }
}
}


catch(Exception nn){

System.out.println("ERRR");

}

    return results;
    
    }
    
    
    //Load quiz files..
    
        public List loadQuizFiles(String location){

	List<String> results = new ArrayList<String>();
        int sn =0;
	try

{

    File[] files = new File(location).listFiles();
    //If this pathname does not denote a directory, then listFiles() returns null. 
    
    Arrays.sort(files, new AlphanumFileComparator() );

    for (File file : files) {
        if (file.isFile()) {
            
            sn++;
            
            String a = lang.stripExtension(String.valueOf(file.getName()));
            
            String sna = String.valueOf(sn);
            
                results.add(sna+"/"+a);
 
    }
        else{
            //Load 
        }
}
}


catch(Exception nn){

System.out.println("ERRR");

}

    return results;
    
    }
        
   public static int countQuizFile(String dirPath) {
    File f = new File(dirPath);
    File[] files = f.listFiles();

    if (files != null)
    for (int i = 0; i < files.length; i++) {
        
            //resx++;

        File file = files[i];
        
        if(file.isFile()){
            resx++;
        }

        if (file.isDirectory()) {   
             countQuizFile(file.getAbsolutePath()); 
        }
    }
    
    return resx;
}
   
   public List loadLibrary(String location){

    List<LibraryModel> results = new ArrayList<LibraryModel>();

    String fileName="", fName="";
    String note="";
    String type="";
    int sn =0;
    try

    {

    File[] files = new File(location).listFiles();
    Arrays.sort(files, new AlphanumFileComparator() );
    
    
    
    for (File file : files) { 
        
        if (file.isFile()) {
            fName = lang.stripExtension(String.valueOf(file.getName()));
            LibraryModel lMode = new LibraryModel();
            
            fileName = file.getName();
            
            
            
                        
                        if(fileName.contains(".MP4")||fileName.contains(".mp4")){
                            type="Video";
                        }
                        
                        if(fileName.contains(".JPG")||fileName.contains(".jpg")||fileName.contains(".PNG")||fileName.contains(".png")){
                            type="Image";
                        }
                        
                        if(fileName.contains(".GIF")||fileName.contains(".gif")){
                            type="Animation";
                        }
                        
                        if(fileName.contains(".PDF")||fileName.contains(".pdf")){
                            type="Document";
                        }
                        
                        
            
            //get content of notes..            
            try{
                
                /*
                String tyLoc = location +"/"+fileName;
                
                File[] tyFile = new File(tyLoc).listFiles();
                
                for(File fx : tyFile){
                    
                    if(fx.isFile()){
                        String fxName = fx.getName();
                        
                        if(fxName.contains(".MP4")||fxName.contains(".mp4")){
                            type="Video";
                        }
                        
                        if(fxName.contains(".JPG")||fxName.contains(".jpg")||fxName.contains(".GIF")||fxName.contains(".gif")){
                            type="Image";
                        }
                        
                        if(fxName.contains(".PDF")||fxName.contains(".pdf")){
                            type="Document";
                        }
                    }
                    
                }
                */
                
                
                String noteLoc = location +"/"+fName+"_note.txt";
                
                File fx = new File(noteLoc);
                
                if(fx.exists()){
                    
                    note = new String(Files.readAllBytes(Paths.get(noteLoc)));
                }
                
                else{
                    System.out.println("Note file does not exist");
                    note="INFO UNAVIALABLE";
                }
                
            }
            catch(Exception fnf){
                
            }
            
            if(fName.contains("_note")){
                
            }else{
                
            lMode.setTitle(fName);
            lMode.setWithExtension(fileName);
            lMode.setDescription(note);
            lMode.setType(type);
            
            results.add(lMode);
                
            }
            
            
            
    }
        else{
            //it is a file..
        }
}
}


catch(Exception nn){

System.out.println("Error loading Library files.." +nn);

nn.printStackTrace();

}

    return results;
    
    }
    
}
