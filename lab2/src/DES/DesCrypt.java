package DES;
import java.util.Arrays;

public class DesCrypt {

    // 对 size大小的 bits矩阵（实际是一维数组）进行 对应的置换
    public static byte[] permute(byte[] bits, int size_t, int[] permuteMatrix) {
        int size = permuteMatrix.length;
        byte[] copy = new byte[size_t];
        System.arraycopy(bits, 0, copy, 0, size_t);

        bits = new byte[size];
        for( int i = 0 ; i < size ; i ++) {
            bits[i] = copy[permuteMatrix[i]-1];
        }
        return bits;
    }
    // 对密匙进行左移
    public static void LeftShift(Key k, int shift) {
        byte[] m = k.getBitM();
        if( shift == 1 ) {
            byte a0 = m[0], a28 = m[28];
            System.arraycopy(m, 1, m, 0,27);
            m[27] = a0;
            System.arraycopy(m,29,m,28,27);
            m[55] = a28;
        }
        if( shift == 2 ) {
            byte a0 = m[0], a1 = m[1];
            byte a28 = m[28], a29 = m[29];
            System.arraycopy(m, 2, m, 0,26);
            m[27] = a1; m[26] = a0;
            System.arraycopy(m,30,m,28,26);
            m[55] = a29; m[54] = a28;
        }
    }

    // 第round的密匙生成函数
    public static void generateKey(Key k) {

        for( int i = 0; i < 16 ; i++ ) {
            int shift = Data.DesRotations[i];
            LeftShift(k, shift);
            Data.deskey[i] = permute(k.getBitM(), k.getBitM().length, Data.PC2);
        }
    }

    // 返回两个整形数组（大小相等）的异或结果
    public static byte[] XOR(byte[] m, byte[] DesKey) {
        if( m.length != DesKey.length ) {
            System.out.println(m.length + ' ' + DesKey.length);
            return null;
        }
        int size = m.length;
        byte[] res = new byte[size];
        for( int i = 0 ; i < size ; i ++ ) {
            res[i] = (byte)(m[i]^DesKey[i]);
        }
        return res;
    }
    // 将48位分成 8 个 6位
    public static int ToBinary(byte[] RPT, int round) {
        int sixBit = 0;
        for( int i = round*6 ; i < round*6 + 6 ; i ++ ) {
                sixBit = sixBit << 1 | RPT[i];
        }
        return sixBit;
    }
    // 将s盒读取的输入转化为 4位2 进制表示
    public static void SBoxConvert(byte[] RPT_32, int num, int round) {
        for( int i = 3, j = 4*round ; i >= 0 ; i --, j++ ) {
            RPT_32[j] = (byte)((num >> i) & 1);
        }
    }
    // s盒 将48位 -> 32位
    public static byte[] SBox (byte[] RPT) {

        byte []RPT_32 = new byte[32];
        for( int i = 0 ; i < 8 ; i ++ ) {
            int sixBit = ToBinary(RPT, i);
            SBoxConvert(RPT_32, Data.S[i][sixBit], i);
        }
        return RPT_32;
    }

    // f函数
    public static byte[] F(byte[] RPT, byte[] DesKey ) {

        RPT = permute(RPT, RPT.length, Data.E);
        RPT = XOR(RPT, DesKey);
        RPT = SBox(RPT);
        RPT = permute(RPT, RPT.length, Data.P_Box);
        return RPT;

    }
    // 加密完成 将 合并 左32位 和右32位
    public static byte[] Merge( byte[] a, byte[] b) {
        byte []t = new byte[64];
        System.arraycopy(a, 0, t, 0, 32);
        System.arraycopy(b, 0, t, 32, 32);
        return t;
    }
    //  加密主函数
    public static void encrypt(mess m, Key k, boolean encrypt) {
        DesCrypt.generateKey(k);
        m.setBitM(permute(m.getBitM(), m.getBitM().length, Data.IP));

        byte[] LPT_old = new byte[32];
        byte[] RPT_old = new byte[32];
        byte[] desKey;
        System.arraycopy(m.getBitM(), 0, LPT_old, 0, 32);
        System.arraycopy(m.getBitM(), 32, RPT_old, 0, 32);

        for ( int i = 0 ; i < 16 ; i++ ) {
            if( encrypt )
                desKey = Data.deskey[i];
            else
                desKey = Data.deskey[15-i];

            byte[] LPT_new = Arrays.copyOf(RPT_old, RPT_old.length);
            byte[] RPT_new = XOR( LPT_old, F(RPT_old, desKey));
            LPT_old = LPT_new;
            RPT_old = RPT_new;


        }
        m.setBitM(Merge(RPT_old, LPT_old));
        m.setBitM(permute(m.getBitM(), m.getBitM().length, Data.IPReverse));
    }

}

