package kg.inai.inventoring.service;

import java.util.List;

import ch.qos.logback.classic.Logger;
import kg.inai.inventoring.controller.InventController;
import kg.inai.inventoring.entity.Category;
import kg.inai.inventoring.entity.Client;
import kg.inai.inventoring.entity.Invents;
import kg.inai.inventoring.entity.Location;
import kg.inai.inventoring.entity.Quality;
import kg.inai.inventoring.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j

@Service
public class InventService {
    private final InventRepository inventRepository;
    private final CategoryRepository categoryRepository;
    private final QualityRepository qualityRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;
    private final QRCodeGenerator qrCodeGenerator;
    Logger logger = (Logger) LoggerFactory.getLogger(InventController.class);


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
        // Предварительно сохраняем связанные сущности
        categoryRepository.save(invent.getCategory());
        qualityRepository.save(invent.getQuality());
        locationRepository.save(invent.getLocation());
        clientRepository.save(invent.getClient());

        // Сохраняем основную сущность
        Invents savedInvent = inventRepository.save(invent);

        // Генерируем QR-код с URL
        String qrText = "http://localhost:8080/api/invent/scan?inventId=" + savedInvent.getId();
        String qrPath = qrCodeGenerator.generateQRCodeWithUrl(qrText, savedInvent.getName(), 350, 350);
        savedInvent.setQr(qrPath);

        // Обновляем сущность с QR-кодом
        return inventRepository.save(savedInvent);
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
