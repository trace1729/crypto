package decode;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

public class GUI  {
    JFrame frame;
    JPanel p0 = new JPanel();
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    JPanel p5 = new JPanel();

    decode Decode = new decode();

    public void constructPanel() {

        // p0
        JLabel dimensionLabel = new JLabel("请输入矩阵的维度");
        JTextField tfDM = new JTextField(10);

        p0.add(dimensionLabel); p0.add(tfDM);
        //p1
        JLabel InputM = new JLabel("请输入矩阵密匙");
        JTextArea Matrix = new JTextArea(5,8);
        JLabel InverseM = new JLabel("逆矩阵");
        JTextArea Inverse = new JTextArea(5,8);


        p1.add(InputM); p1.add(Matrix); p1.add(InverseM); p1.add(Inverse);
        //p2
        JLabel MLabel = new JLabel("请输入明文/密文");
        JTextField Message = new JTextField(10);
        JButton confirm = new JButton("确认");
        p2.add(MLabel); p2.add(Message); p2.add(confirm);

        confirm.addActionListener(new textListener());
        //p3
        JLabel output = new JLabel("output");
        JTextArea outPut = new JTextArea(2,20);
        p3.add(output); p3.add(outPut);

        //p4
        Font font = new Font("MONOSPACED", Font.BOLD, 10);
        JButton decode = new JButton("解密");
        JButton encode = new JButton("加密");
        decode.setFont(font);
        encode.setFont(font);

        decode.addActionListener(new decodeListener());
        encode.addActionListener(new encodeListener());

        p5.add(encode);
        p5.add(decode);

        //p4
        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
        p4.add(p0); p4.add(p1); p4.add(p2); p4.add(p3); p4.add(p5);
        //p4.add(label);



        frame.getContentPane().add(BorderLayout.NORTH, p4);
    }

    class textListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField t1 = (JTextField)p0.getComponent(1);
            JTextArea t2 = (JTextArea)p1.getComponent(1);
            JTextField t3 = (JTextField)p2.getComponent(1);
            JTextArea t4 = (JTextArea)p1.getComponent(3);

            Key inverseKey = Key.revereKey();

            for( int i = 0 ; i < inverseKey.getDemsion() ; i ++ ) {
                for ( int j = 0 ; j < inverseKey.getDemsion() ; j ++ ){
                    Integer a = (Integer)(inverseKey.arr[i][j]);
                    t4.append(a.toString() + " ");
                }
                t4.append("\n");
            }

            Decode.k = new Key(Integer.parseInt(t1.getText()));

            String[] matrixData =
                    t2.getText().replace("\n", " ").split("\\s+");


            Decode.k.init(matrixData);
            Decode.info = t3.getText().replace(" ", "");

        }
    }

    class decodeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Decode.Encypt(Decode.k.revereKey(), Decode.info);
            JTextArea ta = (JTextArea) p3.getComponent(1);
            ta.setText("");
            ta.append(new String(Decode.codeBuff));
        }
    }
    class encodeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Decode.Encypt(Decode.k, Decode.info);
            JTextArea ta = (JTextArea) p3.getComponent(1);
            ta.setText("");
            ta.append(new String(Decode.codeBuff));
        }

    }
    public void go() {

        frame = new JFrame("Hill Encrypt");
        constructPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        //frame.getContentPane().add(BorderLayout.SOUTH, ta);
        frame.setVisible(true);
    }


    public static void main (String[] args) {
        GUI gui = new GUI();
        gui.go();
    }

}