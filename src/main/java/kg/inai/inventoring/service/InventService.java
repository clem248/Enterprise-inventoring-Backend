package kg.inai.inventoring.service;

import com.cloudinary.Cloudinary;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import kg.inai.inventoring.entity.*;
import kg.inai.inventoring.repository.InventRepository;
import kg.inai.inventoring.repository.CategoryRepository;
import kg.inai.inventoring.repository.QualityRepository;
import kg.inai.inventoring.repository.LocationRepository;
import kg.inai.inventoring.repository.ClientRepository;
import kg.inai.inventoring.service.QRCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
@Service
public class InventService {

    private final InventRepository inventRepository;
    private final CategoryRepository categoryRepository;
    private final QualityRepository qualityRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;
    private final QRCodeGenerator qrCodeGenerator;
    private final Cloudinary cloudinary;

    @Autowired
    FileService fileService;

    public InventService(InventRepository inventRepository,
                         CategoryRepository categoryRepository,
                         QualityRepository qualityRepository,
                         LocationRepository locationRepository,
                         ClientRepository clientRepository,
                         QRCodeGenerator qrCodeGenerator,
                         Cloudinary cloudinary) {
        this.inventRepository = inventRepository;
        this.categoryRepository = categoryRepository;
        this.qualityRepository = qualityRepository;
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
        this.qrCodeGenerator = qrCodeGenerator;
        this.cloudinary = cloudinary;
    }

    public List<Invents> getAllInvents() {
        return inventRepository.findAll();
    }

    public Invents createInvent(Invents invent, MultipartFile file) throws Exception {
        Category category = categoryRepository.findByCategoryName(invent.getCategory().getCategoryName())
                .orElseThrow(() -> new Exception("Category not found"));
        Quality quality = qualityRepository.findByQualityName(invent.getQuality().getQualityName())
                .orElseThrow(() -> new Exception("Quality not found"));
        Location location = locationRepository.findByLocationName(invent.getLocation().getLocationName())
                .orElseThrow(() -> new Exception("Location not found"));

        // Поиск или создание клиента
        Client client = findOrCreateClient(invent.getClient().getFullName());

        invent.setCategory(category);
        invent.setQuality(quality);
        invent.setLocation(location);
        invent.setClient(client);

        String cloudinaryUrl = fileService.storeFile(file);
        invent.setPicture(cloudinaryUrl);

        ObjectMapper objectMapper = new ObjectMapper();
        String inventJson = objectMapper.writeValueAsString(invent);

        String qrCloudinaryUrl = generateQRCodeWithUrl(inventJson, 350, 350);
        invent.setQr(qrCloudinaryUrl);

        return inventRepository.save(invent);
    }

    private Client findOrCreateClient(String fullName) {
        // Попытка найти клиента по полному имени
        Optional<Client> existingClient = clientRepository.findClientByFullName(fullName);
        if (existingClient.isPresent()) {
            return existingClient.get();
        } else {
            // Если клиент не найден, создаем нового
            Client newClient = new Client();
            newClient.setFullName(fullName); // Установка имени клиента
            // Здесь можно установить другие свойства клиента, если они есть
            return clientRepository.save(newClient);
        }
    }


    public String generateQRCodeWithUrl(String text, int width, int height) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        Path tempFile = Files.createTempFile("QRCode", ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", tempFile);

        Map uploadResult = cloudinary.uploader().upload(tempFile.toFile(), Map.of("public_id", UUID.randomUUID().toString()));
        Files.delete(tempFile);

        return uploadResult.get("url").toString();
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

    public Optional<Invents> getInventById(Long id) {
        return inventRepository.findById(id);
    }

    public Invents updateInvent(Long id, Invents updatedInvent) {
        Optional<Invents> existingInvent = inventRepository.findById(id);
        if (existingInvent.isPresent()) {
            Invents invents = existingInvent.get();
            invents.setCategory(updatedInvent.getCategory());
            invents.setClient(updatedInvent.getClient());
            invents.setName(updatedInvent.getName());
            invents.setLocation(updatedInvent.getLocation());
            invents.setPicture(updatedInvent.getPicture());
            invents.setQr(updatedInvent.getQr());
            invents.setQuality(updatedInvent.getQuality());
            return inventRepository.save(invents);
        } else {
            return null;
        }
    }

    public Page<Invents> findByFilters(String category, String location, String client, Pageable pageable) {
        return inventRepository.findByFilters(category, location, client, pageable);
    }
}
