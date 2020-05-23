/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning.language;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author SAMSON KAYODE
 */
public class Language {
    
    public List loadFiles(){

	List<String> results = new ArrayList<String>();
        
	try

{

    File[] files = new File("lang").listFiles();
    //If this pathname does not denote a directory, then listFiles() returns null. 

    for (File file : files) {
        if (file.isFile()) {
            String a = stripExtension(String.valueOf(file.getName()));
            results.add(a);
    }
}
}


catch(Exception nn){

System.out.println("ERRR");

}

    return results;
    
    }
    
    //Remove file extension..
    
    public String stripExtension (String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }
    
    
    public String translate(String file, String word){
        
        String rt="";
        
    File nfile = new File(file);
    Scanner scanner = null;

    try {
        scanner = new Scanner(nfile);
    } catch(FileNotFoundException e) { 
        
       //handle this
    }

    //now read the file line by line
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if(line.contains(word)) { 
            //System.out.println(line);
            String[] parts = line.split("#");
            
            rt = parts[1];
            
        }
        
        else{
            //rt= "NIL";
        }
        
    }
    scanner.close();
    
    return rt;
    }
    
    
    
}
