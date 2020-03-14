import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;

public class qrCodeGenerator {

    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";
    private final String url= "www.anarq.live/join/room/%d";
    final int width = 350;
    final int height = 350;

    public void generateQRCodeImage(int roomCode, String filePath) throws WriterException, IOException {

        String text = String.format(url, roomCode);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    /**
     * This method takes the text to be encode and returns the QR Code in the form of a byte array.
     *
     * return - the QR Code image as a response to an http request.
     * You can return the byte array in the body of your http response.
     */

     public byte[] getQRCodeImage(int roomCode) throws WriterException, IOException {

        String text = String.format(url, roomCode);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    /**
     * This method takes the text to be encoded and returns the QR Code in the form of a String
     * as MongoDB has removed support for byte arrays.
     *
     * return - the QR Code image encoded in a string
     * Convert string to byte array in the body of the http response
     */

    public String getQRCodeImageString(int roomCode) throws WriterException, IOException {

        String text = String.format(url, roomCode);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        String qrCode = Base64.getEncoder().encodeToString(pngData);
        return qrCode;
    }
}

