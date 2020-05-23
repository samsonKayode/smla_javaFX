/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unesco.elearning;

/**
 *
 * @author SAMSON KAYODE
 */
import java.util.*;

public class TryVector

{

Vector v;

public TryVector()

{

v = new Vector();

String a = "1#QUESTION ONE IS HERE#YES";
String b = "2#QUESTION TWO IS HERE#NO";
String c = "3#QUESTION THREE IS HERE#YES";
String d = "4#QUESTION FOUR IS HERE#NO";

v.add(a);
v.add(b);
v.add(c);
v.add(d);

System.out.println("----------------------------");

/*
String ques1 = String.valueOf(v.elementAt(0));
System.out.println("A IS :" + ques1);

String ques2 = String.valueOf(v.elementAt(1));
System.out.println("A IS :" + ques2);

String ques3 = String.valueOf(v.elementAt(2));
System.out.println("A IS :" + ques3);

String ques4 = String.valueOf(v.elementAt(3));
System.out.println("A IS :" + ques4);
        */

for(int i =0; i<v.size(); i++){
    String ax = String.valueOf(v.elementAt(i));
    
    System.out.println(ax);
}

System.out.println("----------------------------");

}

public static void main(String kl[])

{

new TryVector();

}
}


