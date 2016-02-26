package com.njcets.maintenance.tool.handler;


import java.io.*;

/**
 * Created by gexin on 2/24/2016.
 */
public class FileEncryptAndDecrypt {

    public static void encrypt(String fileUrl, String key) throws Exception {
        File file = new File(fileUrl);
        String path = file.getPath();
        if(!file.exists()) {
            return;
        }

        int index = path.lastIndexOf("\\");
        String destFile = path.substring(0, index) + "\\" + "abc";
        File dest = new File(destFile);
        InputStream in = new FileInputStream(fileUrl);
        OutputStream out = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024];
        int r;
        byte[] buffer2 = new byte[1024];
        while( ( r = in.read(buffer) ) > 0 ){
            for(int i = 0; i < r; i++) {
                byte b = buffer[i];
                buffer2[i] = b == 255 ? 0 : ++b;
            }
            out.write(buffer2, 0, r);
            out.flush();
        }
        in.close();
        out.close();
        file.delete();
        dest.renameTo((new File(fileUrl)));
        appendMethodA(fileUrl, key);
        System.out.println("Encrypt Successfully");
    }

    public static String decrypt(String fileUrl, String tempUrl, int keyLength) throws Exception {
        File file = new File(fileUrl);
        if(!file.exists()) {
            return null;
        }

        File dest = new File(tempUrl);
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        InputStream in = new FileInputStream(fileUrl);
        OutputStream out= new FileOutputStream(tempUrl);

        byte[] buffer = new byte[1024];
        byte[] buffer2 = new byte[1024];
        byte bMax = (byte)255;
        long size = file.length() - keyLength;
        int mod = (int) (size % 1024);
        int div = (int) (size >> 10);
        int count = mod == 0 ? div : (div + 1);
        int k = 1, r;
        while( (k <= count && (r = in.read(buffer)) > 0)){
            if(mod != 0 && k == count) {
                r = mod;
            }

            for(int i = 0; i < r; i++) {
                byte b = buffer[i];
                buffer2[i]  = b == 0 ? bMax : --b;
            }
            out.write(buffer2, 0, r);
            k++;
        }
        out.close();
        in.close();

        return tempUrl;
    }

    public static void appendMethodA(String fileName, String content) {
        try{
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
            long fileLength = randomAccessFile.length();
            randomAccessFile.seek(fileLength);
            randomAccessFile.writeBytes(content);
            randomAccessFile.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Temp";
        // encrypt(filePath + File.separator + "user.properties", "password");         // encrypt file
        decrypt(filePath + File.separator + "user.properties", "C:\\Temp\\tmp.txt", "password".length());      // decrypt file
    }
}
