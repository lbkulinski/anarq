package com.anarq.qr;

import java.io.*;
import java.util.Base64;

public class byteToImgDEMO {
    public static void main(String args[]) throws Exception {

        qrCodeGenerator c = new qrCodeGenerator();
        byte[]bytes = c.getQRCodeImage(111111);     // Byte array to store the encoded image
        String str = c.getQRCodeImageString(111111);  // String variable to store the encoded image
        byte [] string = Base64.getDecoder().decode(str);   // Convert String to byte Array
        String pathString = "./outputString.png";
        String pathByte = "./outputByte.png";

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(pathString))) { // Convert byte to img
            out.write(string);
        }   catch (Exception e ){
            System.out.println("couldn't convert");
        }

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(pathByte))) { // Convert byte to img
            out.write(bytes);
        }   catch (Exception e ){
            System.out.println("couldn't convert");
        }
    }
}