package DES;

public class Message_file implements mess{
    final byte[] COMPLEMENT = new byte[]{0,1,2,3,4,5,6,7};
    /**
     *  储存全部信息
     */
    byte[] data;

    /**
     * 当前一轮的 信息
     */
    byte[] buf;
    /**
     *  储存当前信息的二进制位
     */
    private byte[] bits;
    /**
     *  指示信息的下标
     */
    int idx;

    /**
     *  加密的总轮数
     */
    boolean isfull;

    Message_file() {
        data = new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 1, 2,3};
        buf = new byte[8];
        bits = new byte[64];
        idx = 0;
        isfull = false;
    }

    Message_file(byte[] b) {
        data = b;
        buf = new byte[8];
        bits = new byte[64];
        idx = 0;
        isfull = false;
    }

    @Override
    public byte[] getBitM() {
        return bits;
    }

    @Override
    public void setBitM(byte[] bit) {
        bits = bit;
    }

    public void load() {
        if( !IsFull()) {
            if (data.length - idx >= 8) { // 如果剩下的message 字符数 大于等于 8
                System.arraycopy(data, idx, buf, 0, 8);
                idx += 8;
                if(idx >= data.length) isfull = true;
            } else { // 如果剩下的字符小与等于 8
                isfull = true;
                int left = data.length - idx ;
                int j = 0;
                for (int i = idx; i < data.length ; i++, j++) {
                    buf[j] = data[i];  //
                }
                while( j < buf.length ) {
                    buf[j++] = COMPLEMENT[8 - left];
                }
                idx = data.length;
            }
        }
    }

    private void byteToBinary(int ch, int idx) {

        for( int i = 7, j = 0; i >= 0 ; i--, j++ ) {
            bits[idx*8 + j] = (byte)((ch >> i) & 1);
        }
    }

    public void phrase_file_input() {
        for (int i = 0; i < 8; i++) {
            byteToBinary(buf[i], i);
        }
    }

    private byte BinaryToDecimal(int l, int r) {
        byte decimal = 0;
        for( int i = l ; i < r ; i ++ ) {
            decimal = (byte)(decimal << 1 | bits[i]);
        }
        return decimal;
    }

    public byte[] BinaryTobyte() {
        byte[] t = new byte[8];
        for (int i = 0; i < 8; i++) {
            byte ch = BinaryToDecimal(8*i, 8*i+8);
            t[i] = ch;
        }
        return t;
    }

    public void Update() {
        if( !IsFull() ) {
            load();
            phrase_file_input();
        }
    }

    public boolean IsFull() {
        return isfull;
    }

    @Override
    public void print() {
        for( int i = 0 ; i < 8 ; i ++) {
            for( int j = 0 ; j < 8 ; j ++ ) {
                System.out.printf("%d ",bits[i*8 + j] );
            }
            if( i == 3 || i == 7)
                System.out.print("\n");
            else
                System.out.print(" ");
        }
        System.out.println();
    }

}