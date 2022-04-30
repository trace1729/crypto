package DES;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;
import java.util.Arrays;

public class Gui {
    JFrame frame;
    JPanel p0 = new JPanel();
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    JPanel p5 = new JPanel();
    String filename = null;


    JTextField tfM = null;
    JTextField tfK = null;
    JTextArea taOutput = null;

    public void constructPanel() {

        // p0
        JLabel MLabel = new JLabel("请输入明文/密文");
        JTextField tfMessage = new JTextField(20);

        p0.add(MLabel); p0.add(tfMessage);

        //p1


        //p2
        JLabel MKey = new JLabel("请输入密匙");
        JTextField tfKey = new JTextField(10);
        p2.add(MKey); p2.add(tfKey);

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

        Font font2 = new Font("MONOSPACED", Font.ITALIC, 10);
        JButton decode_file = new JButton("解密文件");
        JButton encode_file = new JButton("加密文件");
        decode.setFont(font2);
        encode.setFont(font2);

        decode.addActionListener(new decodeListener());
        encode.addActionListener(new encodeListener());
        decode_file.addActionListener(new decode_fileListener());
        encode_file.addActionListener(new encode_fileListener());

        p5.add(encode);
        p5.add(decode);
        p5.add(encode_file);
        p5.add(decode_file);

        //p4
        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
        p4.add(p0);  p4.add(p2); p4.add(p3); p4.add(p5);

        // menu ba
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem openmenuItem = new JMenuItem("open");
        openmenuItem.addActionListener(new openEventListener());
        menu.add(openmenuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);


        frame.getContentPane().add(BorderLayout.NORTH, p4);
    }

    public void setText() {
        tfM = (JTextField)p0.getComponent(1);
        tfK = (JTextField)p2.getComponent(1);
        taOutput = (JTextArea)p3.getComponent(1);
    }


    class openEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // create an object of JFileChooser class
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            JTextField tf = (JTextField) p0.getComponent(1);
            JTextArea ta = (JTextArea) p3.getComponent(1);
            // invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            // if the user selects a file
            if (r == JFileChooser.APPROVE_OPTION) {
                // set the label to the path of the selected file
                filename = j.getSelectedFile().getAbsolutePath();
                tf.setText(j.getSelectedFile().getAbsolutePath());
            }
            // if the user cancelled the operation
            else
                ta.setText("the user cancelled the operation");
        }


    }

    class encode_fileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField tfK = (JTextField)p2.getComponent(1);
            JTextArea tka = (JTextArea) p3.getComponent(1);
            if( filename != null ) {
                try {
                    // init
                    File input_file = new File(filename);

                    String t1 = filename.substring(0, filename.indexOf('.'));
                    String t2 = filename.substring(filename.indexOf('.'));

                    String outtfilename = t1 + "加密" + t2;

                    File outputFile = new File(outtfilename);
                    // read
                    FileInputStream os = new FileInputStream(input_file);
                    int t = (int)input_file.length();
                    byte[] input_bytes = new byte[t];
                    byte[] outputBytes = new byte[(int)(Math.ceil(t/8.0)*8)];
                    os.read(input_bytes);
                    // write
                    Message_file m = new Message_file(input_bytes);
                    Key k = new Key(tfK.getText().toCharArray());
                    int idx = 0;
                    System.out.printf("%d\t%d\n",input_bytes.length, outputBytes.length);
                    while( !m.IsFull() ) {
                        m.Update();
                        DesCrypt.encrypt(m, k, true);
                        for( byte b : m.BinaryTobyte() ) {
                            outputBytes[idx++] = b;
                        }
                    }
                    FileOutputStream outputStream = new FileOutputStream(outputFile);
                    outputStream.write(outputBytes);

                    os.close();
                    outputStream.close();

                    tka.setText("加密完成");


                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                tka.setText("请先打开一个文件");

            }

        }
    }

    class decode_fileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder st = new StringBuilder();
            JTextField tfK = (JTextField)p2.getComponent(1);
            JTextArea tka = (JTextArea) p3.getComponent(1);

            if( filename != null ) {
                try {
                    // init
                    File input_file = new File(filename);
                    String t1 = filename.substring(0, filename.indexOf('.'));
                    String t2 = filename.substring(filename.indexOf('.'));

                    String outtfilename = t1 + "解密" + t2;

                    File outputFile = new File(outtfilename);

                    // read
                    FileInputStream os = new FileInputStream(input_file);
                    byte[] input_bytes = new byte[(int) input_file.length()];
                    byte[] outputBytes = new byte[(int) input_file.length()];
                    os.read(input_bytes);


                    // write
                    Message_file m = new Message_file(input_bytes);
                    Key k = new Key(tfK.getText().toCharArray());
                    int idx = 0;
                    while( !m.IsFull() ) {
                        m.Update();
                        DesCrypt.encrypt(m, k, false);
                        for( byte b : m.BinaryTobyte() ) {
                            outputBytes[idx++] = b;
                        }
                    }
                    int t = outputBytes[idx-1];
                    byte[] output;
                    if (t >= 1 && t <= 9)
                        output = Arrays.copyOf(outputBytes, idx-1-  (int)outputBytes[idx-1]);
                    else
                        output = Arrays.copyOf(outputBytes, idx-1);

                    FileOutputStream outputStream = new FileOutputStream(outputFile );
                    outputStream.write(output);

                    os.close();
                    outputStream.close();
                    tka.setText("文件解密完成");
                    System.out.println("解密");
                    System.out.printf("%d\t%d\t%d\n", input_bytes.length, outputBytes.length, output.length);
                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            } else {
                tka.setText("请先打开一个文件");
            }

        }
    }

    class encodeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setText();
            StringBuilder st = new StringBuilder();
            Message m = new Message(tfM.getText().toCharArray());
            Key k = new Key(tfK.getText().toCharArray());
            k.Update();

            while( !m.encode_checkFull() ) {
                m.encode_update();
                DesCrypt.encrypt(m, k, true);
                for ( char ch : m.encodeOutput() ) {
                    st.append(ch);
                }
            }

            taOutput.setText("");
            taOutput.append(new String(st));
        }

    }

    class decodeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setText();
            StringBuilder st = new StringBuilder();
            System.out.print("");
            Message m = new Message(tfM.getText().toCharArray());
            Key k = new Key(tfK.getText().toCharArray());
            k.Update();

            while( !m.decode_checkFull() ) {
                m.decode_update();
                DesCrypt.encrypt(m, k, false);
                for ( char ch : m.decodeOutput() ) {
                    st.append(ch);
                }
            }

            taOutput.setText("");
            taOutput.append(new String(st));
        }
    }

    public void go() {

        frame = new JFrame("Des Encrypt");
        constructPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }


    public static void main (String[] args) {
        Gui gui = new Gui();
        gui.go();
    }
}