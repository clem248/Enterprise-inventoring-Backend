package kg.inai.inventoring.controller;

import kg.inai.inventoring.entity.Invents;
import kg.inai.inventoring.service.InventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin

@RestController
@RequestMapping("/api/invent")
public class ScanController {
    private final InventService inventService;

    public ScanController(InventService inventService) {
        this.inventService = inventService;
    }

    @GetMapping("/scan")
    public ResponseEntity<String> scanQrCode(@RequestParam Long inventId) {
        Optional<Invents> optionalInvent = inventService.getInventById(inventId);

        if (optionalInvent.isPresent()) {
            Invents invent = optionalInvent.get();
            inventService.updateInventStatus(invent.getId(), "Present");
            return ResponseEntity.ok("Invent status updated to Present.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invent not found.");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Invents> saveInvent(@RequestBody Invents invents) {
        try {
            Invents createdInvent = inventService.createInvent(invents);
            return new ResponseEntity<>(createdInvent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
