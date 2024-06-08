package kg.inai.inventoring.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import kg.inai.inventoring.entity.Category;
import kg.inai.inventoring.entity.Invents;
import kg.inai.inventoring.entity.Location;
import kg.inai.inventoring.entity.Quality;
import kg.inai.inventoring.repository.*;
import kg.inai.inventoring.service.QRCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class InventService {
    private final InventRepository inventRepository;
    private final CategoryRepository categoryRepository;
    private final QualityRepository qualityRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;
    private final QRCodeGenerator qrCodeGenerator;
    public InventService(InventRepository inventRepository,
                         CategoryRepository categoryRepository,
                         QualityRepository qualityRepository,
                         LocationRepository locationRepository,
                         ClientRepository clientRepository,
                         QRCodeGenerator qrCodeGenerator) {
        this.inventRepository = inventRepository;
        this.categoryRepository = categoryRepository;
        this.qualityRepository = qualityRepository;
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }
    public List<Invents> getAllInvents(Pageable pageable) {
        Page<Invents> page = inventRepository.findAll(pageable);
        return page.getContent();
    }


    public Invents createInvent(Invents invent) throws Exception {
        // Найти связанные сущности по именам
        Category category = categoryRepository.findByCategoryName(invent.getCategory().getCategoryName())
                .orElseThrow(() -> new Exception("Category not found"));
        Quality quality = qualityRepository.findByQualityName(invent.getQuality().getQualityName())
                .orElseThrow(() -> new Exception("Quality not found"));
        Location location = locationRepository.findByLocationName(invent.getLocation().getLocationName())
                .orElseThrow(() -> new Exception("Location not found"));

        invent.setCategory(category);
        invent.setQuality(quality);
        invent.setLocation(location);

        Invents savedInvent = inventRepository.save(invent);

        // Сериализация объекта в строку JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String inventJson = objectMapper.writeValueAsString(savedInvent);

        // Создание QR-кода из JSON-строки
        String qrPath = generateQRCodeWithUrl(inventJson, savedInvent.getName(), 350, 350);
        savedInvent.setQr(qrPath);

        return inventRepository.save(savedInvent);
    }


    private static final String QR_CODE_IMAGE_DIR = "./";

    public String generateQRCodeWithUrl(String text, String fileName, int width, int height) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        String uniqueID = UUID.randomUUID().toString();

        String qrCodeFileName = fileName + "1_" + uniqueID + ".png";
        String qrCodeImagePath = QR_CODE_IMAGE_DIR + qrCodeFileName;

        Path path = FileSystems.getDefault().getPath(qrCodeImagePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return qrCodeImagePath;
    }
    public Optional<Invents> getInventByQr(String qr) {
        return inventRepository.findByQr(qr);
    }

    public Invents updateInventStatus(Long id, String status) {
        Optional<Invents> existingInvent = inventRepository.findById(id);
        if (existingInvent.isPresent()) {
            Invents invent = existingInvent.get();
            invent.setStatus(status);
            return inventRepository.save(invent);
        } else {
            return null;
        }
    }

    public Optional<Invents> getInventById(Long id){
        return inventRepository.findById(id);
    }

    public Invents updateInvent(Long id, Invents updatedInvent){
        Optional<Invents> existingInvent = inventRepository.findById(id);
        if(existingInvent.isPresent()){
            Invents invents = existingInvent.get();
            invents.setCategory(updatedInvent.getCategory());
            invents.setClient(updatedInvent.getClient());
            invents.setName(updatedInvent.getName());
            invents.setLocation(updatedInvent.getLocation());
            invents.setPicture(updatedInvent.getPicture());
            invents.setQr(updatedInvent.getQr());
            invents.setQuality(updatedInvent.getQuality());
            return inventRepository.save(invents);
        }else{
            return null;
        }
    }
}
