package decode;

public class Key extends matrix {

    private int demsion;

    public Key( int d ) {
        super(d);
        this.demsion = d;
    }

    public int getDemsion() {
        return demsion;
    }

    public void setDemsion(int demsion) {
        this.demsion = demsion;
    }

    public Key initKey() {
        Key t = new Key(this.getDemsion());
        t.arr[0] = new int[]{17, 17, 5};
        t.arr[1] = new int[]{21, 18, 21};
        t.arr[2] = new int[]{2, 2, 19};

        return t;
    }

    public void init(String[] data) {
        for (int i = 0 ; i < this.getDemsion() ; i++) {
            for (int j = 0; j < this.getDemsion() ; j++) {
                arr[i][j] = Integer.parseInt(data[i*this.getDemsion()+j]);
            }
        }
    }

    public static Key revereKey() {
        Key c = new Key(3);
        c.arr[0] = new int[]{4, 9, 15};
        c.arr[1] = new int[]{15, 17, 6};
        c.arr[2] = new int[]{24, 0, 17};
        return c;
    }


    public matrix multiply(matrix m2, int mod) {
        matrix temp = new matrix(this.getRow(), m2.getCol());
        for ( int i = 0 ; i < this.getRow() ; i ++) {
            for ( int j = 0 ; j < m2.getCol() ; j ++) {
                int ans = 0;
                for( int k = 0 ; k < this.getRow() ; k ++ ) {
                    ans += arr[i][k]*m2.arr[k][j];
                }
                temp.arr[i][j] = ans % 26;
            }
        }
        return temp;
    }
}
