package kg.inai.inventoring.service;

import kg.inai.inventoring.entity.Client;
import kg.inai.inventoring.entity.Invents;
import kg.inai.inventoring.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventService {
    private final InventRepository inventRepository;
    private final CategoryRepository categoryRepository;
    private final QualityRepository qualityRepository;
    private final LocationRepository locationRepository;
    private final ClientRepository clientRepository;

    public InventService(InventRepository inventRepository,
                         CategoryRepository categoryRepository,
                         QualityRepository qualityRepository,
                         LocationRepository locationRepository,
                         ClientRepository clientRepository) {
        this.inventRepository = inventRepository;
        this.categoryRepository = categoryRepository;
        this.qualityRepository = qualityRepository;
        this.locationRepository = locationRepository;
        this.clientRepository = clientRepository;
    }

    public Invents createInvent(Invents invent) {
        // Предварительно сохраняем связанные сущности
        categoryRepository.save(invent.getCategory());
        qualityRepository.save(invent.getQuality());
        locationRepository.save(invent.getLocation());
        clientRepository.save(invent.getClient());

        // Сохраняем основную сущность
        return inventRepository.save(invent);
    }
}
