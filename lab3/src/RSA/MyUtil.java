package RSA;
import java.util.Random;

public class MyUtil {

    private static final long l = 1000;
    private static final long r = 10000;

    /**
     *
     * @param a 底数
     * @param n 指数
     * @param m 模数
     * @return a^n 在模m下的结果
     */
    public static long fastPow(long a, long n, long m) {
        long ans = 1L, t = a;
        while (n != 0 ) {
            if ( (n & 1) != 0) {
                ans = ans * t % m;
            }
            n >>= 1;
            t = t*t % m;
        }
        return ans;
    }
    // 生成8位的奇数

    /**
     *
     * @param rand 随机变量
     * @return 随机返回一个8位的奇数
     */
    public static long getOdd(Random rand) {
        long t = 2;
        while ((t & 1) == 0) {
            t = uniform(rand, l, r);
        }
        return t;
    }

    // 获取 a ->b 的随机整数

    /**
     *
     * @param random 随机数
     * @param a 下限
     * @param b 上限
     * @return 随机返回一个在[a, b)的整数
     */
    public static long uniform(Random random, long a, long b) {
        if ((b <= a) || (b - a >= Long.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + RandomUtils.uniform(random, b - a);
    }

    // 素数判断器 miller-r 算法
    public static boolean isPrime(long n, Random rand) {
        long a, m = n - 1, b, k = 0;
        while( (m & 1) == 0) {
            m >>= 1;
            k++;
        }
        a = MyUtil.uniform(rand, 2, n-1);
        b = MyUtil.fastPow(a, m, n);
        if (b == 1) {
            return true;
        }
        for (int i = 0; i < k; i++) {
            if ( b == n-1) {
                return true;
            }
            else {
                b = b*b % n;
            }
        }
        return false;
    }

    public static long gcd(long a, long b) {
        if (b == 0) {
            return b;
        }
        return exgcd(b, a % b);
    }

    public static long getPublicKey(Random rand, long phiN) {
        for (long i = 2; i < phiN; i++) {
            if (gcd(i, phiN) == 1) {
                return i;
            }
        }
        return 1;
    }

    public static Long exgcd (long a, long n){
        long x1 = 1, x2 = 0, x3 = n;
        long y1 = 0, y2 = 1, y3 = a;
        while (true) {
            if (y3 == 0 ) {
                return 0L;
            }
            if (y3 == 1) {
                y2 %= n;
                y2 = y2 > 0 ? y2 : y2 + n;
                return y2;
            }
            long q = x3 / y3;
            long t1 = x1 - q*y1, t2 = x2 - q*y2, t3 = x3 - q*y3;
            x1 = y1; x2 = y2; x3 = y3;
            y1 = t1; y2 = t2; y3 = t3;
        }
    }

    public static void main(String[] args) {
        System.out.println(exgcd(67, 119));
        System.out.println(exgcd(5, 11));
        System.out.println(fastPow(10063  ,11, 17154043));
    }
}
