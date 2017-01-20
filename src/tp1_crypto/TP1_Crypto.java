/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1_crypto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
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
        
        // Gonna broke RSA
        int e = 3;
        BigInteger be = BigInteger.valueOf(3);
        Map<String, BigInteger> s = gen(be);
        int m = 12345;
        int o = 1;
        
        BigInteger c1 = Enc(s.get("pk"), s.get("n"), BigInteger.valueOf(m));        
        BigInteger c2 = Enc(s.get("pk"), s.get("n"), BigInteger.valueOf(m+o));
        
        System.out.println(BigInteger.valueOf((long) ((Math.pow(m+1, 3) + 2*Math.pow(m, 3)-1)/(Math.pow(m+1, 3) - Math.pow(m, 3) + 2))).mod(s.get("n")));
        
        BigInteger nom = c2.pow(e).add(c1.pow(e).multiply(BigInteger.valueOf(2))).subtract(BigInteger.ONE);
        BigInteger denom = c2.pow(e).subtract(c1.pow(e)).add(BigInteger.valueOf(2));
        
        System.out.println(nom.multiply(be).divide(denom).mod(s.get("n")).add(BigInteger.valueOf(o)));
        
        

    }
    
    static BigInteger Enc(BigInteger pk, BigInteger n, BigInteger message) {
        return message.modPow(pk, n);
    }
    
    static BigInteger Dec(BigInteger sk, BigInteger n, BigInteger message) {
        return message.modPow(sk, n);
    }
    
    static Map<String, BigInteger> gen(BigInteger e) {
        
        Random rand = new Random(System.currentTimeMillis());
        BigInteger n, on;
        
        Map<String, BigInteger> values = new HashMap<>();
        do {
            BigInteger p = new BigInteger(512, 1, rand);        
            BigInteger q = new BigInteger(512, 1, rand);

            n = p.multiply(q);
            on = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
            if(e.compareTo(BigInteger.ZERO) == 0) {
                e = new BigInteger(16,1,rand);
            }

        } while(!e.gcd(on).equals(BigInteger.ONE));
        
        BigInteger d = e.modInverse(on);

        values.put("n", n);
        values.put("pk", e);
        values.put("sk", d);
        
        return values;
        
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
