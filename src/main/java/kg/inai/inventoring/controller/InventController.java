package kg.inai.inventoring.controller;

import kg.inai.inventoring.dto.InventsDTO;
import kg.inai.inventoring.entity.*;
import kg.inai.inventoring.service.InventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
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
    public List<Invents> getAllInvents() {
        return inventService.getAllInvents();
    }

    @PostMapping("/save")
    public ResponseEntity<Resource> saveInvent(@ModelAttribute @Valid Invents invents, @RequestParam("file") MultipartFile file) throws Exception {
        Invents createdInvent = inventService.createInvent(invents, file);

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
    public ResponseEntity<Resource> downloadInvents() {
        List<Invents> inventsList = inventService.getAllInvents();

        log.info("Number of invents: {}", inventsList.size());

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            writeToExcel(byteArrayOutputStream, inventsList);
            ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invents.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (IOException e) {
            log.error("Failed to create Excel file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void writeToExcel(ByteArrayOutputStream outputStream, List<Invents> inventsList) throws IOException {
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
        headerRow.createCell(6).setCellValue("Quality");

        for (Invents invent : inventsList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(Optional.ofNullable(invent.getId()).orElse(0L));
            row.createCell(1).setCellValue(Optional.ofNullable(invent.getName()).orElse("null"));
            row.createCell(2).setCellValue(Optional.ofNullable(invent.getCategory()).map(c -> c.getCategoryName()).orElse("null"));
            row.createCell(3).setCellValue(Optional.ofNullable(invent.getLocation()).map(l -> l.getLocationName()).orElse("null"));
            row.createCell(4).setCellValue(Optional.ofNullable(invent.getClient()).orElse("null"));
            row.createCell(5).setCellValue(LocalDateTime.now());
            row.createCell(6).setCellValue(Optional.ofNullable(invent.getQuality()).map(l -> l.getQualityName()).orElse("null"));
        }

        workbook.write(outputStream);
        workbook.close();
    }
}
