package kg.inai.inventoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private QualityService qualityService;

    @Autowired
    private LocationService locationService;

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

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }
}
