package kg.inai.inventoring.controller;

import kg.inai.inventoring.entity.Invents;
import kg.inai.inventoring.service.InventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/createInvent")
public class CreateNewInventController {
    private final InventService inventsService;
    public CreateNewInventController(InventService inventsService) {
        this.inventsService = inventsService;
    }
    @PostMapping("/save")
    public ResponseEntity<Invents> saveInvent(@RequestBody Invents invents){
        Invents createdInvent = inventsService.createInvent(invents);
        return new ResponseEntity<>(createdInvent, HttpStatus.CREATED);
    }
}
