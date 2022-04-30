package DES;

public class Key extends Message {

    private byte[] bitM;

    public Key() {
        super();
        bitM = new byte[64];
    }
    public Key(char[] t) {
        super(t);
        bitM = new byte[64];
    }

    private void load() {
        if(data.length >= 4)
            System.arraycopy(data, 0, buf, 0, 4);
        // 修改编码方式后需要修改长度
        else
            System.arraycopy(data, 0, buf, 0, data.length);
    }

    public void Update() {
        load();
        phrase_encode_message();
        // 密匙不足 以0补齐
        if( data.length <= 4 ) {
            for( int i =  data.length*16 ; i < bitM.length ; i++ ) {
                bitM[i] = (byte)0;
            }
        }
        this.setBitM(DesCrypt.permute(bitM, bitM.length, Data.PC1));

    }


    public void print() {
        for( int i = 0 ; i < 8 ; i ++) {
            System.out.printf("第%d - %d位： ", i*7+1, i*7+7) ;
            for( int j = 0 ; j < 7 ; j ++ ) {
                System.out.printf("%d", bitM[i*7 + j] );
            }
            System.out.print(" ");
        }
        System.out.println();
    }
}
