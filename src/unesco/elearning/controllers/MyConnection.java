/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.controllers;

import java.sql.Connection;
import unesco.elearning.database.DatabaseConn;

/**
 *
 * @author SAMSON KAYODE
 */
public class MyConnection {
    
    DatabaseConn dbcon = new DatabaseConn();
    
    public void loadConnection()
    {
        try{
            Connection cn = dbcon.Connection();
        }
        catch(Exception ee){
            System.out.println("Error loading connection --"+ee);
        }
    }
    
}
