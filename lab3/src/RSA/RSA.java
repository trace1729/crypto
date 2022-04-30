package RSA;

import java.util.Random;


public class RSA {
    private final long seed = 33431223L;
    private final Random RANDOM = new Random(seed);
    public long N, e, d;

    public RSA() {
        long p = getPrime();
        long q = getPrime();
        long phiN = (p-1)*(q-1);
        this.N = p*q;
        this.e = MyUtil.getPublicKey(RANDOM, phiN);
        this.d = MyUtil.exgcd(e, phiN);
    }


    private long getPrime() {
        int cnt = 1000;
        boolean passed = false;
        long randomN = 0;
        while (!passed) {
            randomN = MyUtil.getOdd(RANDOM);
            for (int i = 0; i < cnt; i++) {
                passed = MyUtil.isPrime(randomN, RANDOM);
                if (!passed)
                    break;
            }
        }
        return randomN;
    }
}

