/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import unesco.elearning.database.DatabaseConn;
import unesco.elearning.model.LessonHistory;
import unesco.elearning.model.LogModel;
import unesco.elearning.model.QuizHistory;
import unesco.elearning.model.SyncData;

/**
 *
 * @author SAMSON KAYODE
 */
public class LogCtrl {
    
    SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
    //SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd  HH:mm:ss a");
    
    JSONObject Root = new JSONObject();
    
    DatabaseConn db = new DatabaseConn();
    int sn =0;
    String query="";
    String query1="";
    String query2="";
    String query3="";
    
    Connection cn = LoginPage.cn;
    
    JSONArray CourseViewArray, QuizViewArray, LogViewArray, ProfileViewArray ;
    

    public List<SyncData> syncData(){
        
        List<SyncData> reps = new ArrayList<SyncData>();
        
        try{
            
            String QUERY ="SELECT DATE FROM SYNC ORDER BY DATE DESC";
            
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(QUERY);
            
            while(rs.next()){
                SyncData sdata = new SyncData();
                String dt = rs.getString(1);
                
                Date dates = sdfIn.parse(dt);
                String date = sdfOut.format(dates);
                
                sdata.setDate(date);
                
                reps.add(sdata);
                
            }
            rs.close();
            st.close();
        }
        catch(Exception nn){
            System.out.println("Error reteievning sync history "+nn);
        }
        
        return reps;
    }
    
    //Quiz history..
    public List<QuizHistory> loadQuizHistory(String studentID, float score){
        
        List<QuizHistory> reps = new ArrayList<QuizHistory>();
        
        String status = "";
        
        try{
            
            //Connection cn = db.Connection();
            PreparedStatement st = cn.prepareStatement("SELECT DATE, COURSE, TERM, LESSON, SCORE FROM QUIZ WHERE STUDENTID=? ORDER BY DATE DESC");
            
            st.setString(1, studentID);

            ResultSet rs = st.executeQuery();
            while(rs.next()){
                
                QuizHistory lmodel = new QuizHistory();
                
                //java.sql.Date dts = rs.getDate(1);
                String date1 = rs.getString(1);
                
                Date dates = sdfIn.parse(date1);
                String date = sdfOut.format(dates);
                
                String course = rs.getString(2);
                String term = rs.getString(3);
                String lesson = rs.getString(4);
                int ts = rs.getInt(5);
                
                if(Float.valueOf(ts) >=score){
                    status = "PASS";
                }
                else{
                    status = "FAIL";
                }
                
                lmodel.setDate(date);
                lmodel.setTerm(term);
                lmodel.setCourse(course);
                lmodel.setLesson(lesson);
                lmodel.setScore(ts);
                lmodel.setStatus(status);
               
                
                reps.add(lmodel);
                
            }
            rs.close();
           st.close();
           //cn.close();
            
        }
        catch(Exception nn){
            System.out.println("Error loading quiz history "+nn);
        }
        
        return reps;
    }
    
    //Lesson history..
    
    public List<LessonHistory> loadHistory(String studentID, int secs){
        
        List<LessonHistory> reps = new ArrayList<LessonHistory>();
        
        try{
            
            /*
            String QUERY ="\"SELECT DATE, COURSE, TERM, LESSON, DATEDIFF(SECOND, DATE, EDATE) FROM COURSEVIEW WHERE \"\n" +
"                    + \"STUDENTID=? AND DATEDIFF(SECOND, DATE, EDATE)>? ORDER BY DATE DESC\"";
            */
            
            String QUERY ="SELECT DATE, COURSE, TERM, LESSON, ((julianday(EDATE) - julianday(DATE)) * 86400.0) "
                    + "FROM COURSEVIEW WHERE STUDENTID=? AND ((julianday(EDATE) - julianday(DATE)) * 86400.0)>? ORDER BY DATE DESC";
            
            //Connection cn = db.Connection();
            PreparedStatement st = cn.prepareStatement(QUERY);
            
            st.setString(1, studentID);
            st.setInt(2, secs);

            ResultSet rs = st.executeQuery();
            while(rs.next()){
                
                LessonHistory lmodel = new LessonHistory();
                
                //java.sql.Date dts = rs.getDate(1);
                //String date = String.valueOf(dts);
                String date1 = rs.getString(1);
                
                System.out.println("DATES ONE IS -"+date1);
                
                Date dates = sdfIn.parse(date1);
                String date = sdfOut.format(dates);
                
                System.out.println("DATES TWO IS -"+date);

                String course = rs.getString(2);
                String term = rs.getString(3);
                String lesson = rs.getString(4);
                String ts = getFormattedTime(rs.getInt(5));
                
                lmodel.setDate(date);
                lmodel.setTerm(term);
                lmodel.setCourse(course);
                lmodel.setLesson(lesson);
                lmodel.setTimeSpent(ts);
                
                reps.add(lmodel);
                
            }
            rs.close();
           st.close();
           //cn.close();
            
        }
        catch(Exception nn){
            System.out.println("Error loading lesson history "+nn);
        }
        
        return reps;
    }
    
    //No of Quiz Passed...
    
    public int quizPassed(String studentID, float score, String sign){
        //List<String> reps = new ArrayList<String>();
        int res=0;
        try{
            
            String QUERY = "SELECT DISTINCT COURSE, TERM, LESSON FROM QUIZ WHERE SCORE"+sign+"? AND STUDENTID=?";
            //System.out.println("QUERY IS "+QUERY);
            System.out.println("STUDENT ID IS "+studentID);
                System.out.println("SCORE IS "+score);
            
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement(QUERY);
            
            ps.setFloat(1, score);
            ps.setString(2, studentID);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                res++;
                String a = rs.getString(1);
            }
            rs.close();
            ps.close();
            //cn.close();
        }
        catch(Exception nn){
            System.out.println("Error loading no of passed quiz "+nn);
        }
        
        return res;
    }
    
    // Total hours spent..
    
    public String hoursSpent(String studentID){
        String result="";
        int res = 0;
        try{
            //String QUERY="SELECT sum( DATEDIFF(SECOND, date, edate)) from COURSEVIEW WHERE STUDENTID=?";
            
            String QUERY="SELECT sum( ((julianday(EDATE) - julianday(DATE)) * 86400.0)) from COURSEVIEW WHERE STUDENTID=?";
            
            
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement(QUERY);
            
                    ps.setString(1, studentID);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                res = rs.getInt(1);
                
                result = getFormattedTime(res);
            }
                rs.close();
                ps.close();
                //cn.close();
        }
        catch(Exception nn){
            System.out.println("Error getting hours spent "+nn);
        }
        
        return result;
    }
    
    //convert time to HH:MM:SS
    
    private static String getFormattedTime(int secs) {
    // int secs = (int) Math.round((double) milliseconds / 1000); // for millisecs arg instead of secs
    if (secs < 60)
        return secs + "s";
    else {
        int mins = (int) secs / 60;
        int remainderSecs = secs - (mins * 60);
        if (mins < 60) {
            return (mins < 10 ? "0" : "") + mins + "m "
                    + (remainderSecs < 10 ? "0" : "") + remainderSecs + "s";
        }
        else {
            int hours = (int) mins / 60;
            int remainderMins = mins - (hours * 60);
            return (hours < 10 ? "0" : "") + hours + "h "
                    + (remainderMins < 10 ? "0" : "") + remainderMins + "m "
                    + (remainderSecs < 10 ? "0" : "") + remainderSecs + "s";
        }
    }
}
    
    
    public List<LogModel> loadLog(String sdate, String edate, String stID, String more)
    {
        //ObservableList<LogModel> reps = FXCollections.observableArrayList();
        
        List<LogModel> reps = new ArrayList<LogModel>();
        
        if(stID.length()>0){
            query1 = "and id ='"+stID+"'";
        }
        if(more.length()>0){
            query1 = "and activity like '%"+more+"%'";            
        }
        
        query = "Select id, date, activity, name from LOGS where date between ? and ? " +query1+" "+query2;
        
        try{
            
            //System.out.println("FINAL QUERY "+query);
            
            //Connection cn = db.Connection();
            PreparedStatement st = cn.prepareStatement(query);
            
            st.setString(1, sdate);
            st.setString(2, edate);
            
            ResultSet rs = st.executeQuery();
            sn=0;
            while(rs.next()){
                
                LogModel lmodel = new LogModel();
                
                sn++;
                
                String id = rs.getString(1).trim();
                //java.sql.Date dts = rs.getDate(2);
                String date1 = rs.getString(2);
                Date dates = sdfIn.parse(date1);
                String date = sdfOut.format(dates);
                String activity = rs.getString(3).trim();
                String name = rs.getString(4).trim();
                
                //String timex = rs.getString(5).trim();
                
                lmodel.setSno(sn);
                lmodel.setActivity(activity);
                lmodel.setDate(date );
                lmodel.setStudentID(id);
                lmodel.setStudentName(name);

                                
                reps.add(lmodel);
                
                //return reps;
            }
            
            rs.close();
            st.close();
            //cn.close();
           
            
        }
        catch(Exception nn){
            System.out.println("Error loading result "+nn );
        }
               
        
        return reps;
    }
    
    public int saveQuizResult(String studentID, String course, String term, String lesson, double score){
        
        int res = 1;
        
        try{
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("INSERT INTO QUIZ(STUDENTID, COURSE, TERM, LESSON, SCORE) VALUES(?,?,?,?,?)");
            
            ps.setString(1, studentID);
            ps.setString(2, course);
            ps.setString(3, term);
            ps.setString(4, lesson);
            ps.setDouble(5, score);
            
            ps.executeUpdate();
            
            res=0;
            
            ps.close(); 
            //cn.close();
            
        }
        catch(Exception nn){
            System.out.println("Error saving result "+nn);
            res =1;
        }
        
        
        return res;
    }
    
    //confirm if Lesson is accessed before taking quiz..
    
    public int lessonAccessed(String studentID, String course, String term, String lesson){
        int x =1;
        
        try{
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM COURSEVIEW WHERE STUDENTID=? AND COURSE=? AND TERM=? AND LESSON=?");
            
            ps.setString(1, studentID);
            ps.setString(2, course);
            ps.setString(3, term);
            ps.setString(4, lesson);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                x = 0;
            }else{
                x = 1;
            }
        }
        catch(Exception xx){
            System.out.println("Error confirming if lesson was accessed "+xx);
            x= 2;
        }
        
        return x;
    }
    
    public int accessLesson(String studentID, String course, String term, String lesson){
        
        int res=1;
        
        try{
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("INSERT INTO COURSEVIEW(STUDENTID, COURSE, TERM, LESSON) VALUES(?,?,?,?)");
            
            ps.setString(1, studentID);
            ps.setString(2, course);
            ps.setString(3, term);
            ps.setString(4, lesson);
            
            ps.executeUpdate();
            
            res=0;
            
            ps.close(); 
            //cn.close();
            
        }
        catch(Exception nn){
            System.out.println("Error saving course view "+nn);
            res =1;
        }
        
        return res;
    }
    
    public void selectTime(String studentID,  String course, String term, String lesson)
    {
     
        int CID=0;
        try{
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("SELECT CID FROM COURSEVIEW WHERE STUDENTID=? AND COURSE=? AND TERM=? AND LESSON=? ORDER BY CID DESC LIMIT 1");
            
            ps.setString(1, studentID);
            ps.setString(2, course);
            ps.setString(3, term);
            ps.setString(4, lesson);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                CID = rs.getInt(1);
                updateTime(studentID, CID);
            }
            
            else{
                
            }
            
            rs.close();
            ps.close();
            //cn.close();
            
        }
        catch(Exception nn){
            System.out.println("Error doing this "+nn);
        }
    }
    
    
    public void updateTime(String studentID, int CID ){
     
        try{
            //Connection cn = db.Connection();
            PreparedStatement ps = cn.prepareStatement("UPDATE COURSEVIEW SET EDATE =CURRENT_TIMESTAMP WHERE STUDENTID=? AND CID=?");
            
            ps.setString(1, studentID);
            ps.setInt(2, CID);
            
            ps.executeUpdate();
            System.out.println("Time Updated");
            
            ps.close();
            //cn.close();
        }
        catch(Exception nn){
            System.out.println("Error updating course view time "+nn);
        }
        
    }
    
    //Synchronize data methods..
    
    
    //get coursevie to JSON..
    
    public String getCourseViewJSON(String file){
        
        String res="";
        
        int sn=0;
        
        try{
            //JSONObject Root = new JSONObject();
            CourseViewArray = new JSONArray();
            
            //File f = new File(file);
            //FileOutputStream fos = new FileOutputStream(f,true);
            //PrintStream pStream = new PrintStream(fos);

            
            PreparedStatement ps = cn.prepareStatement("SELECT STUDENTID, COURSE, TERM, LESSON, DATE, EDATE FROM COURSEVIEW WHERE SYNC=?");
            ps.setString(1, "NO");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                JSONObject obj = new JSONObject();
                
                sn++;
                String stID = rs.getString(1);
                String course = rs.getString(2);
                String term = rs.getString(3);
                String lesson = rs.getString(4);
                String date = rs.getString(5);
                String edate = rs.getString(6);
                
                obj.put("STUDENTID", stID);
                obj.put("COURSE", course);
                obj.put("TERM", term);
                obj.put("LESSON", lesson);
                obj.put("DATE", date);
                obj.put("EDATE", edate);
                
                CourseViewArray.put(obj);
                
                res = sn+" results of unsynchronized data scanned and saved....";
            }
            rs.close();
            ps.close();
            
            Root.put("CourseView", CourseViewArray);
            //pStream.append(Root.toString());
            
            //pStream.close();
        }
        catch(Exception nn){
            res ="Error performing task "+nn;
            System.out.println(res);

        }
        
        return res;
    }
    
    //get QUIZ RESULT..
    
    public String getQuizJSON(String file){
        
        String res="";
        
        int sn=0;
        
        try{
            //JSONObject Root = new JSONObject();
            QuizViewArray = new JSONArray();
            
            //File f = new File(file);
            //FileOutputStream fos = new FileOutputStream(f,true);
            //PrintStream pStream = new PrintStream(fos);

            
            PreparedStatement ps = cn.prepareStatement("SELECT STUDENTID, COURSE, TERM, LESSON, SCORE, DATE FROM QUIZ WHERE SYNC=?");
            ps.setString(1, "NO");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                JSONObject obj = new JSONObject();
                
                sn++;
                String stID = rs.getString(1);
                String course = rs.getString(2);
                String term = rs.getString(3);
                String lesson = rs.getString(4);
                Float score = rs.getFloat(5);
                String date = rs.getString(6);
                
                obj.put("STUDENTID", stID);
                obj.put("COURSE", course);
                obj.put("TERM", term);
                obj.put("LESSON", lesson);
                obj.put("SCORE", score);
                obj.put("DATE", date);
                
                QuizViewArray.put(obj);
                
                res = sn+" results of unsynchronized data scanned and saved....";
            }
            rs.close();
            ps.close();
            
            Root.put("QuizResult", QuizViewArray);
            //pStream.append(Root.toString());
            
            //pStream.close();
        }
        catch(Exception nn){
            res ="Error performing task "+nn;
            System.out.println(res);
        }        
        return res;
    }
    
    //Logs Data..
    
        public String getLogJSON(String file){
        
        String res="";
        
        int sn=0;
        
        try{
            //JSONObject Root = new JSONObject();
            LogViewArray = new JSONArray();
            
            //File f = new File(file);
            //FileOutputStream fos = new FileOutputStream(f,true);
            //PrintStream pStream = new PrintStream(fos);

            
            PreparedStatement ps = cn.prepareStatement("SELECT ID, DATE, ACTIVITY, NAME FROM LOGS WHERE SYNC=?");
            ps.setString(1, "NO");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                JSONObject obj = new JSONObject();
                
                sn++;
                String stID = rs.getString(1);
                String date = rs.getString(2);
                String activity = rs.getString(3);
                String name = rs.getString(4);
                
                obj.put("ID", stID);
                obj.put("DATE", date);
                obj.put("ACTIVITY", activity);
                obj.put("NAME", name);
                
                LogViewArray.put(obj);
                
                res = sn+" results of unsynchronized data scanned and saved....";
            }
            rs.close();
            ps.close();
            
            Root.put("LogResult", LogViewArray);
            //pStream.append(Root.toString());
            
            //pStream.close();
        }
        catch(Exception nn){
            res ="Error performing task "+nn;
            System.out.println(res);
        }        
        return res;
    }
        
        //Profiles Data..
        
        public String getProfileJSON(String file){
        
        String res="";
        
        int sn=0;
        
        try{
            //JSONObject Root = new JSONObject();
            ProfileViewArray = new JSONArray();
            
            //File f = new File(file);
            //FileOutputStream fos = new FileOutputStream(f,true);
            //PrintStream pStream = new PrintStream(fos);

            
            PreparedStatement ps = cn.prepareStatement("SELECT USERNAME, PASSWORD, FIRSTNAME, LASTNAME, DOB, DOR, SEX, "
                    + "ACCOUNT_TYPE, U_IMAGE, STYPE, STUDENTID FROM PROFILES WHERE SYNC=?");
            ps.setString(1, "NO");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                JSONObject obj = new JSONObject();
                
                sn++;
                String username = rs.getString(1).trim();
                String password = rs.getString(2).trim();
                String firstname = rs.getString(3).trim();
                String lastname = rs.getString(4).trim();
                String dob = rs.getString(5).trim();
                String dor = rs.getString(6).trim();
                String sex = rs.getString(7).trim();
                String account_type = rs.getString(8).trim();
                byte[] bvv = rs.getBytes(9);
                String stype = rs.getString(10).trim();
                String studentID = rs.getString(11).trim();
                
                String base64Encoded = DatatypeConverter.printBase64Binary(bvv);
                
                obj.put("USERNAME", username);
                obj.put("PASSWORD", password);
                obj.put("FIRSTNAME", firstname);
                obj.put("LASTNAME", lastname);
                obj.put("DOB", dob);
                obj.put("DOR", dor);
                obj.put("SEX", sex);
                obj.put("ACCOUNT_TYPE", account_type);
                obj.put("IMAGE", base64Encoded);
                obj.put("STYPE", stype);
                obj.put("STUDENTID", studentID);
                
                
                ProfileViewArray.put(obj);
                
                res = sn+" results of unsynchronized data scanned and saved....";
            }
            rs.close();
            ps.close();
            
            Root.put("Profiles", ProfileViewArray);
            //pStream.append(Root.toString());
            
            //pStream.close();
            
            cFile(file);
            
        }
        catch(Exception nn){
            res ="Error performing task "+nn;
            System.out.println(res);
        }        
        return res;
    }
        
        //CREATE FILE..
        
        public void cFile(String file){
            
            try{
            File f = new File(file);
            FileOutputStream fos = new FileOutputStream(f,true);
            PrintStream pStream = new PrintStream(fos);
            
            pStream.append(Root.toString());
            
            pStream.close();
            
            }
            catch(Exception nn){
                System.out.println("ERROR CREATING FILE--"+nn);
            }
            
        }
        
        //done with sync..
        
        public int syncComplete(){
            
            int res = 1;
            
            try{
                Statement st = cn.createStatement();
                
                String Q1 = "UPDATE PROFILES SET SYNC='YES'";
                String Q2 = "UPDATE LOGS SET SYNC='YES'";
                String Q3 = "UPDATE QUIZ SET SYNC='YES'";
                String Q4 = "UPDATE COURSEVIEW SET SYNC='YES'";
                
                st.execute(Q1);
                st.execute(Q2);
                st.execute(Q3);
                st.execute(Q4);
                
                res = 0;
                
            }
            catch(Exception nn){
                System.out.println("Error updating status "+nn);
                
                res = 1;
            }
            
            return res;
        }
        
        
    
}
