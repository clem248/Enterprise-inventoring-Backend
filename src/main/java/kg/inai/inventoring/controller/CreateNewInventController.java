package kg.inai.inventoring.controller;

import kg.inai.inventoring.entity.Invents;
import kg.inai.inventoring.repository.InventRepository;
import kg.inai.inventoring.service.InventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin

@RestController
@RequestMapping("/api/admin/createInvent")
public class CreateNewInventController {
    private final InventService inventsService;
    private final InventRepository inventRepository;
    public CreateNewInventController(InventService inventsService, InventRepository inventRepository) {
        this.inventsService = inventsService;
        this.inventRepository = inventRepository;
    }
    @PostMapping("/save")
    public ResponseEntity<Invents> saveInvent(@RequestBody Invents invents) throws Exception{
            Invents createdInvent = inventsService.createInvent(invents);
        return new ResponseEntity<>(createdInvent, HttpStatus.CREATED);
    }
    public Optional<Invents> getInventByQr(String qr) {
        return inventRepository.findByQr(qr);
    }

}
