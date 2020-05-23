/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.model;

/**
 *
 * @author SAMSON KAYODE
 */
public class LogModel {
    
    int sno;
    String studentID;
    String date;
    String activity;
    String studentName;
    
    /*

    public LogModel(int sno, String studentID, String date, String activity, String studentName) {
        this.sno = sno;
        this.studentID = studentID;
        this.date = date;
        this.activity = activity;
        this.studentName = studentName;
    }
    */

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    
    
}
