package RSA;

public class Encrypt {
    Key public_key, private_key;
    public Encrypt() {
        RSA rsa = new RSA();
        public_key = new Key(rsa.N, rsa.e);
        private_key = new Key(rsa.N, rsa.d);
    }

    public static long convertBinaryToDecimai(int l, int r, byte[] m) {
        long ans = 0;
        for (int i = l; i < r; i++) {
            ans = ans << 1 | m[i];
        }
        return ans;
    }

    public static void convertDecimalToBinary(long t, byte[] m, int round, int base) {
        for( int i = (base-1), j = 0 ; i >= 0 ; i--, j++ ) {
            m[ round*base+j ] = (byte)(t >> i & 1);
        }
    }

    private void phrase_encode_input(long[] encode, byte[] arr) {
        for (int i = 0; i < 4; i++) {
            encode[i] = convertBinaryToDecimai(i*16, (i+1)*16, arr);
        }
    }

    private void encode(long[] n, Message m, byte arr[], int round, StringBuilder sb) {
        for (int i = 2*round; i < 2*(round+1); i++) {
            n[i] = MyUtil.fastPow(n[i], public_key.base, public_key.n);
            convertDecimalToBinary(n[i], arr, i%2, 32);
        }
        m.setBitM(arr);
        for ( char ch : m.encodeOutput() ) {
            sb.append(ch);
        }
    }
    public void encode(Message m, StringBuilder sb) {

        while (!m.encode_checkFull()) {
            m.encode_update();
            byte[] arr = m.getBitM();
            long[] n = new long[4];
            phrase_encode_input(n, arr);
            encode(n, m, arr, 0, sb);
            encode(n, m, arr, 1, sb);
        }
        System.out.println(sb.toString());
    }

    private void decode(Message m, byte[] arr, StringBuilder sb) {
        long t1 = convertBinaryToDecimai(0, 32, arr);
        long t2 = convertBinaryToDecimai(32, 64, arr);
        t1 = MyUtil.fastPow(t1, private_key.base, private_key.n);
        t2 = MyUtil.fastPow(t2, private_key.base, private_key.n);
        convertDecimalToBinary(t1, arr, 0, 16);
        convertDecimalToBinary(t2, arr, 1, 16);
        m.setBitM(arr);
        for ( char ch : m.decodeOutput() ) {
            sb.append(ch);
        }
    }

    public void decode(Message m, StringBuilder sb) {

        while (!m.decode_checkFull()) {
            m.decode_update();
            byte[] arr = m.getBitM();
            decode(m, arr, sb);
        }
        System.out.println(sb.toString());

    }
    public class Key {
        long n;
        long base;

        public Key(long n, long base) {
            this.n = n;
            this.base = base;
        }

        @Override
        public String toString() {
            return "Key{" +
                    "n=" + n +
                    ", base=" + base +
                    '}';
        }
    }

    public static void main(String[] args){
        Message m = new Message("sassd".toCharArray());
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Encrypt en = new Encrypt();
        en.encode(m, sb1);
        en.decode(new Message(sb1.toString().toCharArray()), sb2);
        System.out.println(en.public_key);
        System.out.println(en.private_key);
    }

}
