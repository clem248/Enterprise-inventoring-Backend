package kg.inai.inventoring.service;

import kg.inai.inventoring.dto.InventsDTO;
import kg.inai.inventoring.entity.Client;
import kg.inai.inventoring.entity.Invents;
import kg.inai.inventoring.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public List<Invents> getAllInvents(){
        return inventRepository.findAll();
    }
//    public Page<InventsDTO> getInvents(int pageNumber, int pageSize, String sortField, String sortOrder) {
//        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
//        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
//        Page<Invents> paymentsPage = InventRepository.findAll(pageRequest);
//        return paymentsPage.map(payment -> paymentDTOMapper.apply(payment));
//    }
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
