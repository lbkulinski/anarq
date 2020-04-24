package com.anarq.qr;

import java.io.*;
import java.util.Base64;

/**
 * A demo for converting bytes to images.
 *
 * @version April 24, 2020
 */
public class ByteToImgDEMO {
    /**
     * Runs the demo.
     *
     * @param args the command line arguments
     * @throws Exception if an exception occurs.
     */
    public static void main(String[] args) throws Exception {
        QrCodeGenerator c = new QrCodeGenerator();
        byte[]bytes = c.getQRCodeImage(111111);     // Byte array to store the encoded image
        String str = c.getQRCodeImageString(111111);  // String variable to store the encoded image
        byte[] string = Base64.getDecoder().decode(str);   // Convert String to byte Array
        String pathString = "./outputString.png";
        String pathByte = "./outputByte.png";

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(pathString))) { // Convert byte to img
            out.write(string);
        }   catch (Exception e ){
            System.out.println("couldn't convert");
        } //end try catch

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(pathByte))) { // Convert byte to img
            out.write(bytes);
        }   catch (Exception e ){
            System.out.println("couldn't convert");
        } //end try catch
    } //main
}