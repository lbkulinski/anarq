package com.anarq.qr;

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

/**
 * A utility class for generating a QR Code for the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class QrCodeGenerator {
    /**
     * The URL for this QR Code generator.
     */
    private final String url= "www.anarq.live/join/room/%d";

    /**
     * The URL for this QR Code generator.
     */
	private final String urlS= "www.anarq.live/join/room/%s";

    /**
     * The width for this QR Code generator.
     */
    final int width = 350;

    /**
     * The height for this QR Code generator.
     */
    final int height = 350;

    /**
     * Generates an image of a QR Code from this generator.
     *
     * @param roomCode the room code to be used in the operation
     * @param filePath the file path to be used in the operation
     * @throws WriterException if a writer error occurs
     * @throws IOException if an I/O error occurs
     */
    public void generateQRCodeImage(int roomCode, String filePath) throws WriterException, IOException {
        String text = String.format(url, roomCode);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    } //generateQRCodeImage

    /**
     * Returns the QR Code image of this generator.
     *
     * @param roomCode the room code to be used in the operation
     * @return the QR Code image of this generator
     * @throws WriterException if a writer error occurs
     * @throws IOException if an I/O error occurs
     */
    public byte[] getQRCodeImage(int roomCode) throws WriterException, IOException {
        String text = String.format(url, roomCode);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        return pngOutputStream.toByteArray();
    } //getQRCodeImage

    /**
     * Returns the QR Code image of this generator.
     *
     * @param roomCode the room code to be used in the operation
     * @return the QR Code image of this generator
     * @throws WriterException if a writer error occurs
     * @throws IOException if an I/O error occurs
     */
    public byte[] getQRCodeImage(String roomCode) throws WriterException, IOException {
        String text = String.format(urlS, roomCode);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

         return pngOutputStream.toByteArray();
    } //getQRCodeImage

    /**
     * Returns the QR Code of this generator as a {@code String}.
     *
     * @param roomCode the room code to be used in the operation
     * @return the QR Code of this generator as a {@code String}
     * @throws WriterException if a writer error occurs
     * @throws IOException if an I/O error occurs
     */
    public String getQRCodeImageString(int roomCode) throws WriterException, IOException {
        String text = String.format(url, roomCode);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        byte[] pngData = pngOutputStream.toByteArray();

        return Base64.getEncoder()
                     .encodeToString(pngData);
    } //getQRCodeImageString

    /**
     * Returns the QR Code of this generator as a {@code String}.
     *
     * @param roomCode the room code to be used in the operation
     * @return the QR Code of this generator as a {@code String}
     * @throws WriterException if a writer error occurs
     * @throws IOException if an I/O error occurs
     */
	public String getQRCodeImageString(String roomCode) throws WriterException, IOException {
        String text = String.format(urlS, roomCode);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        byte[] pngData = pngOutputStream.toByteArray();

        return Base64.getEncoder()
                     .encodeToString(pngData);
    } //getQRCodeImageString
}