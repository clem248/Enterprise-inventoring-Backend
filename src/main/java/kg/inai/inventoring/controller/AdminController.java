package kg.inai.inventoring.controller;

import kg.inai.inventoring.dto.ClientDTO;
import kg.inai.inventoring.entity.Client;
import kg.inai.inventoring.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kg.inai.inventoring.entity.Category;
import kg.inai.inventoring.entity.Quality;
import kg.inai.inventoring.entity.Location;
import kg.inai.inventoring.service.CategoryService;
import kg.inai.inventoring.service.QualityService;
import kg.inai.inventoring.service.LocationService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LocationService locationService;
    @Autowired
    private QualityService qualityService;

    @Autowired
    private ClientService clientService;

    // Другие методы контроллера

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/qualities")
    public ResponseEntity<List<Quality>> getAllQualities() {
        List<Quality> qualities = qualityService.getAllQualities();
        return ResponseEntity.ok(qualities);
    }

    @GetMapping("/AllClients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }


    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location createdLocation = locationService.createLocation(location);
        return ResponseEntity.ok(createdLocation);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok().build();
    }
}
