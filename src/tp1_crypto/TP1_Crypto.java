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
//        int e = 3;
//        BigInteger be = BigInteger.valueOf(3);
//        Map<String, BigInteger> s = gen(be);
//        int m = 12345;
//        int o = 1;
//
//        BigInteger c1 = Enc(s.get("pk"), s.get("n"), BigInteger.valueOf(m));
//        BigInteger c2 = Enc(s.get("pk"), s.get("n"), BigInteger.valueOf(m + o));
//
//        System.out.println(BigInteger.valueOf((long) ((Math.pow(m + 1, 3) + 2 * Math.pow(m, 3) - 1) / (Math.pow(m + 1, 3) - Math.pow(m, 3) + 2))).mod(s.get("n")));
//
//        BigInteger nom = c2.pow(e).add(c1.pow(e).multiply(BigInteger.valueOf(2))).subtract(BigInteger.ONE);
//        BigInteger denom = c2.pow(e).subtract(c1.pow(e)).add(BigInteger.valueOf(2));
//
//        System.out.println(nom.multiply(be).divide(denom).mod(s.get("n")).add(BigInteger.valueOf(o)));
    
        Map<String, BigInteger> s = genPaillier();

//        for(int i=0;i<10;i++) {
//            int m = (int) (Math.random() * 1100000000);
//            System.out.println("Message "+m);
//            
//            BigInteger c = EncPaillier(s.get("n"), BigInteger.valueOf(m));
//            System.out.println(DecPaillier(s.get("n"), s.get("a"), c));
//            
//        }

        int m1 = 1234;
        BigInteger c1 = EncPaillier(s.get("n"), BigInteger.valueOf(m1));
        
        int m2= 4567;
        BigInteger c2 = EncPaillier(s.get("n"), BigInteger.valueOf(m2));
        
        BigInteger c3 = c1.multiply(c2).mod(s.get("n").pow(2));
        
        BigInteger m3 = DecPaillier(s.get("n"), s.get("a"), c3);
        
        BigInteger c4 = c1.modPow(BigInteger.valueOf(m2), s.get("n").pow(2));
        
        BigInteger m4 = DecPaillier(s.get("n"), s.get("a"), c4);
        
        
        System.out.println(m3);
        System.out.println(m4);
        
       
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

            if (e.compareTo(BigInteger.ZERO) == 0) {
                e = new BigInteger(16, 1, rand);
            }

        } while (!e.gcd(on).equals(BigInteger.ONE));

        BigInteger d = e.modInverse(on);

        values.put("n", n);
        values.put("pk", e);
        values.put("sk", d);

        return values;
    }

    
    static BigInteger EncPaillier(BigInteger n, BigInteger message) {
        Random rand = new Random(System.currentTimeMillis());
        BigInteger r = new BigInteger(512, rand).mod(n);
        return n.add(BigInteger.ONE).modPow(message, n.pow(2)).multiply(r.modPow(n, n.pow(2))).mod(n.pow(2));
    }

    static BigInteger DecPaillier(BigInteger n, BigInteger a, BigInteger message) {
        BigInteger r = message.modPow(a, n);
        return ((message.multiply(r.modPow(n.negate(), n.pow(2))).mod(n.pow(2))).subtract(BigInteger.ONE)).divide(n);
    }

    
    // https://fr.wikipedia.org/wiki/Cryptosyst%C3%A8me_de_Paillier
    static Map<String, BigInteger> genPaillier() {

        Random rand = new Random(System.currentTimeMillis());
        BigInteger n, on;

        Map<String, BigInteger> values = new HashMap<>();

        BigInteger p = new BigInteger(512, 1, rand);
        BigInteger q = new BigInteger(512, 1, rand);

        n = p.multiply(q);
        on = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        values.put("n", n);
        values.put("a", n.modInverse(on));

        return values;

    }

    static boolean primaire(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    static boolean primaireFermat(BigInteger n) {
        if (BigInteger.valueOf(2).modPow(n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE)) {
            return true;
        }
        return false;
    }

}
