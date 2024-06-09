package kg.inai.inventoring.controller;

import kg.inai.inventoring.entity.Invents;
import kg.inai.inventoring.service.InventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/admin/invent")
@Validated
public class InventController {

    private final InventService inventService;

    public InventController(InventService inventService) {
        this.inventService = inventService;
    }

    @GetMapping
    public List<Invents> getAllInvents(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return inventService.getAllInvents(pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<Resource> saveInvent(@RequestBody Invents invents) throws Exception {
        Invents createdInvent = inventService.createInvent(invents);

        String qrCodePath = createdInvent.getQr();
        File qrCodeFile = new File(qrCodePath);

        Resource resource = new FileSystemResource(qrCodeFile);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + qrCodeFile.getName() + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(qrCodeFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invents> getInventById(@PathVariable Long id) {
        Optional<Invents> invent = inventService.getInventById(id);
        return invent.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invents> updateInvent(@PathVariable Long id, @RequestBody Invents updatedInvent) {
        Invents invents = inventService.updateInvent(id, updatedInvent);
        return (invents != null) ? new ResponseEntity<>(invents, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadInvents(
            HttpServletResponse response,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String client) {

        List<Invents> inventsList;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortField);
        if (category != null || location != null || client != null) {
            inventsList = inventService.findByFilters(category, location, client, pageable).getContent();
        } else {
            inventsList = inventService.getAllInvents(pageable);
        }

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=invents.xlsx");

            writeToExcel(response.getOutputStream(), inventsList);
            response.flushBuffer();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Excel file");
        }

        return ResponseEntity.ok("Excel file created successfully");
    }

    private void writeToExcel(ServletOutputStream outputStream, List<Invents> inventsList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invents");

        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Category");
        headerRow.createCell(3).setCellValue("Location");
        headerRow.createCell(4).setCellValue("Client");
        headerRow.createCell(5).setCellValue("Date");

        for (Invents invent : inventsList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(invent.getId());
            row.createCell(1).setCellValue(invent.getName());
            row.createCell(2).setCellValue(invent.getCategory().toString());
            row.createCell(3).setCellValue(invent.getLocation().toString());
            row.createCell(4).setCellValue(invent.getClient());
        }

        workbook.write(outputStream);
        workbook.close();
    }
}