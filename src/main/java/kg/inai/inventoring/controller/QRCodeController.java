package kg.inai.inventoring.controller;

import kg.inai.inventoring.service.QRCodeGenerator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/qr")
@Validated
public class QRCodeController {

    private final QRCodeGenerator qrCodeGenerator;

    public QRCodeController(QRCodeGenerator qrCodeGenerator) {
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @GetMapping("/generateQRCode")
    public String generateQRCode(@RequestParam String text) {
        try {
            qrCodeGenerator.generateQRCodeWithUrl(text,"test", 350, 350);
            return "QR Code generated successfully!";
        } catch (Exception e) {
            return "Failed to generate QR Code: " + e.getMessage();
        }
    }
}
