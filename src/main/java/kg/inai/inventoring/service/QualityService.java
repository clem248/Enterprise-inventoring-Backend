package kg.inai.inventoring.service;

import kg.inai.inventoring.entity.Quality;
import kg.inai.inventoring.repository.QualityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityService {
    @Autowired
    private QualityRepository qualityRepository;

    public List<Quality> getAllQualities() {
        return qualityRepository.findAll();
    }
}
