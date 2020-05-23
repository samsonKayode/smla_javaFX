
package unesco.elearning.controllers;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Random;

public final class SessionIdentifierGenerator
{

  static SecureRandom random = new SecureRandom();

static Random rand = new Random();

  public static String nextSessionId()
  {
    return new BigInteger(40, random).toString(20);
  }

public static int getInv(){

int n = rand.nextInt(1999999)+107238;

return n;

}

/*
public static void main(String df[])

{

System.out.println (nextSessionId().toUpperCase());

System.out.println("INV "+getInv());

}*/
}
