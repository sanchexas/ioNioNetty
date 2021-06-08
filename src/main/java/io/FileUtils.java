package io;

        import java.io.*;
        import java.util.Random;
        import java.util.RandomAccess;
        import java.util.Scanner;

public class FileUtils {
    public static void main(String[] args) throws IOException {
        try (
                InputStream is = new FileInputStream("yo.JPG");
                OutputStream os = new FileOutputStream("yoyoyoyo.JPG")
        ) {
            int b = 0;
            byte[] buffer = new byte[81];
            while ((b = is.read(buffer)) != -1) {
                os.write(buffer, 0,b);
            }
        }
        Scanner scanner = new Scanner(new File("123.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("123.txt")));
        PrintWriter pr;
        BufferedWriter bw;
        RandomAccessFile raf = new RandomAccessFile("123.txt", "rw");


        byte[] len = new byte[4];
        DataInputStream is;

        ObjectInputStream ois = null;
        ObjectOutputStream oos;

        // FileObject o = (FileObject) ois.readObject();
        //oos.writeObject(new FileObject());


    }
}