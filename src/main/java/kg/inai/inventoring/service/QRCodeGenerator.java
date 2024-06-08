package kg.inai.inventoring.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class QRCodeGenerator {

    private static final String QR_CODE_IMAGE_DIR = "./";

    public String generateQRCodeWithUrl(String text, String fileName, int width, int height) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        String uniqueID = UUID.randomUUID().toString();
        String qrCodeFileName = fileName + "_" + uniqueID + ".png";
        String qrCodeImagePath = QR_CODE_IMAGE_DIR + qrCodeFileName;

        Path path = FileSystems.getDefault().getPath(qrCodeImagePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return qrCodeImagePath;
    }

}
