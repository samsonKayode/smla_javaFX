/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author SAMSON KAYODE
 */
public class UploadData {
       
    String fileP="";
    
    public UploadData(){
        
        try{
            
            JSONParser parser = new JSONParser();
            
            Object obj = parser.parse(new FileReader("C:\\SMLA_SHARE_DATA\\57866756.txt"));
 
            JSONObject jsonObject = (JSONObject) obj;
                        
            //Log Result
            JSONArray jarr = (JSONArray) jsonObject.get("LogResult");
            for(int i = 0; i < jarr.size(); i++) {
 
                JSONObject c = (JSONObject) jarr.get(i);
                
                String DATE = (String) c.get("DATE");
                String ID = (String) c.get("STUDENTID");
                String ACTIVITY = (String) c.get("ACTIVITY");
                String NAME = (String) c.get("NAME");
                
                System.out.println("DATE: -"+DATE);
                System.out.println("ID: -"+ID);
                System.out.println("ACTIVITY: -"+ACTIVITY);
                System.out.println("NAME: -"+NAME);
                System.out.println("--------------------------------"+"\n");
    }
                   
            //CourseView...
            
            JSONArray courseView = (JSONArray) jsonObject.get("CourseView");
            
            for(int i = 0; i < courseView.size(); i++) {
 
                JSONObject c = (JSONObject) courseView.get(i);
                
                String SDATE = (String) c.get("DATE");
                String EDATE = (String) c.get("EDATE");
                String SID = (String) c.get("STUDENTID");
                String COURSE = (String) c.get("COURSE");
                String TERM = (String) c.get("TERM");
                String LESSON = (String) c.get("LESSON");
                
                System.out.println("DATE: -"+SDATE);
                System.out.println("EDATE: -"+EDATE);
                System.out.println("ID: -"+SID);
                System.out.println("COURSE: -"+COURSE);
                System.out.println("TERM: -"+TERM);
                System.out.println("LESSON: -"+LESSON);
                System.out.println("--------------------------------"+"\n");
    }
            //Quiz Record..
            
            JSONArray quizView = (JSONArray) jsonObject.get("QuizResult");
            
            for(int i = 0; i < quizView.size(); i++) {
 
                JSONObject c = (JSONObject) quizView.get(i);
                
                String SDATE = (String) c.get("DATE");
                String SCORE = (String) c.get("SCORE");
                String SID = (String) c.get("STUDENTID");
                String COURSE = (String) c.get("COURSE");
                String TERM = (String) c.get("TERM");
                String LESSON = (String) c.get("LESSON");
                
                System.out.println("DATE: -"+SDATE);
                System.out.println("SCORE: -"+SCORE);
                System.out.println("ID: -"+SID);
                System.out.println("COURSE: -"+COURSE);
                System.out.println("TERM: -"+TERM);
                System.out.println("LESSON: -"+LESSON);
                System.out.println("--------------------------------"+"\n");
    }
            
            //Profile Record..
            
            JSONArray profileView = (JSONArray) jsonObject.get("Profiles");
            
            for(int i = 0; i < profileView.size(); i++) {
 
                JSONObject c = (JSONObject) profileView.get(i);
                
                String USERNAME = (String) c.get("USERNAME");
                String PASSWORD = (String) c.get("PASSWORD");
                String FIRSTNAME = (String) c.get("FIRSTNAME");
                String LASTNAME = (String) c.get("LASTNAME");
                String DOB = (String) c.get("DOB");
                String SEX = (String) c.get("SEX");
                String ACCOUNT_TYPE = (String) c.get("ACCOUNT_TYPE");
                String IMAGE = (String) c.get("IMAGE");
                String STYPE = (String) c.get("STYPE");
                String STUDENTID = (String) c.get("STUDENTID");
                
                System.out.println("USERNAME: -"+USERNAME);
                System.out.println("PASSWORD: -"+PASSWORD);
                System.out.println("FIRSTNAME: -"+FIRSTNAME);
                System.out.println("LASTNAME: -"+LASTNAME);
                System.out.println("DOB: -"+DOB);
                System.out.println("SEX: -"+SEX);
                
                System.out.println("ACCOUNT_TYPE: -"+ACCOUNT_TYPE);
                System.out.println("IMAGE: -"+IMAGE);
                System.out.println("STYPE: -"+STYPE);
                System.out.println("STUDENTID: -"+STUDENTID);
                System.out.println("--------------------------------"+"\n");
    }
            
            
        }
        catch(Exception nn){
            
            System.out.println("ERROR HAPPENED HERES..."+nn);
            
            nn.printStackTrace();
            
        }
        
        
    }
       
    public static void main(String gd[]){
        
        new UploadData();
    }
    
}
