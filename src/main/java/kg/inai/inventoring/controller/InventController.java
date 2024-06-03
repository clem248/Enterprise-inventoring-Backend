package kg.inai.inventoring.controller;

import kg.inai.inventoring.entity.Client;
import kg.inai.inventoring.entity.Invents;
import kg.inai.inventoring.excel.ExcelHandler;
import kg.inai.inventoring.service.InventService;
import kg.inai.inventoring.service.QRCodeGenerator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/invent")
@Validated
public class InventController {

    private final InventService inventService;
    private final ExcelHandler excelHandler;

    public InventController(InventService inventService, ExcelHandler excelHandler) {
        this.inventService = inventService;
        this.excelHandler = excelHandler;
    }

    @GetMapping
    public List<Invents> getAllInvents(){
        return inventService.getAllInvents();
    }
    @PostMapping("/save")
    public ResponseEntity<Invents> saveInvent(@RequestBody Invents invents){
        Invents createdInvent = inventService.createInvent(invents);
        return new ResponseEntity<>(createdInvent, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Invents> getInventById(@PathVariable Long id){
        Optional<Invents> invent = inventService.getInventById(id);
        return invent.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invents> updateInvent(@PathVariable Long id, @RequestBody Invents updatedInvent){
        Invents invents = inventService.updateInvent(id, updatedInvent);
        return (invents != null) ? new ResponseEntity<>(invents, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping("/download")
//    public ResponseEntity<String> downloadInvents(
//            HttpServletResponse response,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "name") String sortField,
//            @RequestParam(defaultValue = "asc") String sortOrder,
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) String location,
//            @RequestParam(required = false) String client,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {
//
//        List<Invents> inventsList;
//
//        if (category != null || location != null || client != null || startDate != null || endDate != null) {
//            inventsList = inventService.findByFilters(category, location, client, startDate, endDate,
//                    PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortField)).getContent();
//            try {
//                response.setContentType("application/vnd.ms-excel");
//                response.setHeader("Content-Disposition", "attachment; filename=invents.xlsx");
//
//                excelHandler.writeToExcel(response.getOutputStream(), inventsList);
//                response.flushBuffer();
//            } catch (IOException e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Excel file");
//            }
//        } else {
//            inventsList = inventService.getAllInvents(PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortField)).getContent();
//            try {
//                response.setContentType("application/vnd.ms-excel");
//                response.setHeader("Content-Disposition", "attachment; filename=invents.xlsx");
//
//                excelHandler.writeToExcel(response.getOutputStream(), inventsList);
//                response.flushBuffer();
//            } catch (IOException e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Excel file");
//            }
//        }
//
//        return ResponseEntity.ok("Excel file created successfully");
//    }
}
