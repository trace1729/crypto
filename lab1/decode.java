package decode;

import java.util.Scanner;

public class decode {


    public StringBuilder codeBuff;
    public String info;
    public Key k;

    Scanner scanner = new Scanner(System.in);

    public void Encypt(Key k, String st) {
        int r = k.getDemsion();
        matrix m = new matrix(r, 1);
        codeBuff = new StringBuilder(100);

        for ( int i = 0 ; i < st.length() / r ; i ++) {
            char[] buf = st.substring(i*r, i*r + r).toCharArray();
            m.readMess(buf);
            matrix code = k.multiply(m, 26);
            code.getCode(codeBuff);
        }
    }

}
