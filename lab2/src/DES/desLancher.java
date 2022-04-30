//package DES;
//
//import org.junit.Test;
//import org.junit.Assert;
//import java.util.Arrays;
//
//public class desLancher {
//    @Test
//    public void testIp() {
//        Message m = new Message();
//        m.Update();
//        byte[] aby = new byte[64];
//        System.arraycopy(m.getBitM(), 0, aby, 0 ,64);
//
//        m.setBitM(DesCrypt.permute(m.getBitM(), m.getBitM().length, Data.IP));
//        m.setBitM(DesCrypt.permute(m.getBitM(), m.getBitM().length, Data.IPReverse));
//
//        Assert.assertArrayEquals(m.getBitM(), aby);
//    }
//    @Test
//    public void testLeftShift() {
//        Key k = new Key();
//        int k0 = k.getBitM()[0];
//        int k27 = k.getBitM()[27];
//        int k28 = k.getBitM()[28];
//        int k55 = k.getBitM()[55];
//
//        DesCrypt.LeftShift(k, 1);
//        Assert.assertEquals(k0, k.getBitM()[27]);
//        Assert.assertEquals(k27, k.getBitM()[0]);
//        Assert.assertEquals(k28, k.getBitM()[55]);
//        Assert.assertEquals(k55, k.getBitM()[28]);
//    }
//
//    @Test
//    public void testXOR() {
//        byte[] a = new byte[]{0, 1, 0, 1, 0, 1};
//        byte[] b = new byte[]{1, 0, 1, 0, 0, 1};
//
//        byte[] res = DesCrypt.XOR(a, b);
//        System.out.println(Arrays.toString(res));
//    }
//
//    @Test
//    public void testToBinary() {
//        // input a digit and check the six digit outcome( check the array)
//        byte[] test1 = new byte[] {0,1,0,1,0,1};
//        byte[] test2 = new byte[] {1,0,0,0,1,0};
//        byte[] test4 = new byte[] {1,0,0,0,0,0};
//        Assert.assertEquals(DesCrypt.ToBinary(test1, 0), 21);
//        Assert.assertEquals(DesCrypt.ToBinary(test2, 0), 34);
//        Assert.assertEquals(DesCrypt.ToBinary(test4, 0), 32);
//        byte[] test3 = new byte[] {0,1,0,1,0,1,1,0,0,0,1,0,0,1,0,1,0,1,1,0,0,0,1,0 };
//        Assert.assertEquals(DesCrypt.ToBinary(test3, 0), 21);
//        Assert.assertEquals(DesCrypt.ToBinary(test3, 3), 34);
//    }
//
//    @Test
//    public void testSBoxConvert() {
//        byte[] ans1 = new byte[] {1,1,1,1};
//        byte[] ans2 = new byte[] {1,0,1,0};
//        byte[] ans4 = new byte[] {1,0,0,0};
//        byte[] test = new byte[4];
//        DesCrypt.SBoxConvert(test, 15, 0);
//        Assert.assertArrayEquals(test, ans1);
//        DesCrypt.SBoxConvert(test, 10, 0);
//        Assert.assertArrayEquals(test, ans2);
//        DesCrypt.SBoxConvert(test, 8, 0);
//        Assert.assertArrayEquals(test, ans4);
//
//        byte[] test2 = new byte[16];
//        byte[] ans3 = new byte[] {
//           1,1,1,1, 1,1,1,1 ,1,0,1,0, 1,0,1,0
//        } ;
//        DesCrypt.SBoxConvert(test2, 15, 0);
//        DesCrypt.SBoxConvert(test2, 15, 1);
//        DesCrypt.SBoxConvert(test2, 10, 2);
//        DesCrypt.SBoxConvert(test2, 10, 3);
//        Assert.assertArrayEquals(test2, ans3);
//    }
//
//    @Test
//    public void testDtoB() {
//        Message m = new Message();
//        m.Update(); // bitM has abcdefg bitwise code
//        char[] test = m.BinaryToChar();
//        Assert.assertArrayEquals(new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}, test);
//
//    }
//
//
//
//
//}
