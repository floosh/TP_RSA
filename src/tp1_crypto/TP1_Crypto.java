/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1_crypto;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author win
 */
public class TP1_Crypto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Random rand = new Random(System.currentTimeMillis());
        
        //z48
        for(int i=1;i<48;i++) {
            for(int j=1;j<48;j++) {
                if((i*j)%48==1) System.out.println(i + " " + j);
            }
        }
        
        //z53
        for(int i=1;i<53;i++) {
            if(54%i==0) System.out.println(i);
        }
        /*
        for(int i=100000000;i<200000000;i++) {
            if(primaireFermat(i)) {
                System.out.println("primaire " + i + ": " + primaireFermat(i));
            }
        }*/
        
        BigInteger p = new BigInteger(512, 1, rand);        
        BigInteger q = new BigInteger(512, 1, rand);
        
        System.out.println(p);
        System.out.println(q);
        
        System.out.println("primaire " + p + ": " + primaireFermat(p));
        System.out.println("primaire " + q + ": " + primaireFermat(q));
        
        BigInteger n = p.multiply(q);
        BigInteger on = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
       
        System.out.println(n);
        System.out.println(on);
        
        BigInteger e;
        do {
            e = new BigInteger(16,1,rand);
        } while(!e.gcd(on).equals(BigInteger.ONE));
        
        System.out.println(e);
        BigInteger d = e.modInverse(on);
        System.out.println(d);
        System.out.println(d.multiply(e).mod(on));
        
        for(int i = 0;i<10;i++) {
            BigInteger x = BigInteger.valueOf(i);
            BigInteger X = x.modPow(e, n);
            System.out.println(X.modPow(d, n));
        }
        
        
        
        
    }
    
    static boolean primaire(int n) {
        for(int i=2;i<n;i++) {
            if(n%i == 0) return false;
        }
        return true;
    }
    
    static boolean primaireFermat(BigInteger n) {
        if(BigInteger.valueOf(2).modPow(n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE)) {
            return true;
        }
        return false;
    }
    
}
