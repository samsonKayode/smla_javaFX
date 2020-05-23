/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.controllers;

import java.io.FileReader;
import java.text.DecimalFormat;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author SAMSON KAYODE
 */
public class UploadJSONData {
    
    int sn=0;
    int logData, profileData, quizData, courseData;
        
    
    LoginPage lpage = new LoginPage();
    
    public String retrieveData(String fileLoc){
        
        StringBuilder sb= new StringBuilder();
        
        String str="";
        
        try{
            
            JSONParser parser = new JSONParser();
            
            Object obj = parser.parse(new FileReader(fileLoc));
 
            JSONObject jsonObject = (JSONObject) obj;
                        
            //Log Result
            JSONArray jarr = (JSONArray) jsonObject.get("LogResult");
            
            if(jarr!=null){
            
            for(int i = 0; i < jarr.size(); i++) {
                
                if(jarr.size()<=0){
                    
                    sb.append("\n"+"LOG RESULT IS EMPTY...");
                    
                }else{
                    
                sn++;    
                JSONObject c = (JSONObject) jarr.get(i);
                
                String DATE = (String) c.get("DATE");
                String ID = (String) c.get("STUDENTID");
                String ACTIVITY = (String) c.get("ACTIVITY");
                String NAME = (String) c.get("NAME");
                
                logData = lpage.JSONLogView(ID, ACTIVITY, NAME, DATE);               
                }
            }
                
    }
            sb.append("\n"+ sn+" Data Found in Log Result");
            sb.append("\n"+ logData+" Data Moved From Log Result to Database");
                   
            //CourseView...
            
            JSONArray courseView = (JSONArray) jsonObject.get("CourseView");
            sn=0;
            
            if(courseView!=null){
            
            for(int i = 0; i < courseView.size(); i++) {
                
                if(courseView.size()<=0){
                    sb.append("\n"+"COURSE VIEW RESULT IS EMPTY...");
                }else{
                    
                JSONObject c = (JSONObject) courseView.get(i);
                
                sn++;
                String SDATE = (String) c.get("DATE");
                String EDATE = (String) c.get("EDATE");
                String SID = (String) c.get("STUDENTID");
                String COURSE = (String) c.get("COURSE");
                String TERM = (String) c.get("TERM");
                String LESSON = (String) c.get("LESSON");
                
                courseData = lpage.JSONCourseView(SID, COURSE, TERM, LESSON, SDATE, EDATE);                
                }            
            }
    }
            
            sb.append("\n"+ sn+" Data Found in COURSE VIEW Result");
            sb.append("\n"+ courseData+" Data Moved From COURSE VIEW Result to Database");
            //Quiz Record..
            
            JSONArray quizView = (JSONArray) jsonObject.get("QuizResult");
            
            sn=0;
            
            DecimalFormat df = new DecimalFormat("###.#");
            
            if(quizView!=null){
                            
            for(int i = 0; i < quizView.size(); i++) {
                
                if(quizView.size()<=0){
                    sb.append("\n"+"QUIZ RESULT IS EMPTY...");
                }else{
                    
                JSONObject c = (JSONObject) quizView.get(i);
                
                sn++;
                String SDATE = (String) c.get("DATE");
                long SCORE = (long) c.get("SCORE");
                
                
                String SID = (String) c.get("STUDENTID");
                String COURSE = (String) c.get("COURSE");
                String TERM = (String) c.get("TERM");
                String LESSON = (String) c.get("LESSON");
                
                quizData = lpage.JSONQuizView(SID, COURSE, TERM, LESSON, SCORE, SDATE);
                
                }   
            }
    }
            
            sb.append("\n"+ sn+" Data Found in QUIZ Result");
            sb.append("\n"+ quizData+" Data Moved from QUIZ Result to Database");
            
            //Profile Record..
            
            JSONArray profileView = (JSONArray) jsonObject.get("Profiles");
            
            sn=0;
            
            if(profileView!=null){
            
            for(int i = 0; i < profileView.size(); i++) {
                
                if(profileView.size()<=0){
                    sb.append("\n"+"PROFILE RESULT IS EMPTY...");
                }else{
                    
                JSONObject c = (JSONObject) profileView.get(i);
                
                sn++;
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
                
                byte[] base64Decoded = DatatypeConverter.parseBase64Binary(IMAGE);
                
                profileData = lpage.JSONProfileView(FIRSTNAME, LASTNAME, USERNAME, PASSWORD, DOB, SEX, ACCOUNT_TYPE, base64Decoded, STYPE, STUDENTID); 
                }
                
            }
    }
            sb.append("\n"+ sn+" Data Found in PROFILE Result");
            sb.append("\n"+ profileData+" Data Moved From PROFILE Result to Database");
            
            str = sb.toString();
            
        }
        catch(Exception nn){
            
            System.out.println("ERROR HAPPENED HERES..."+nn);
            
            str = "ERROR COMPLETING OPERATION "+nn;
            
            nn.printStackTrace();
            
        }
        
        return str;
    }
    
}
