package kg.inai.inventoring.excel;

import kg.inai.inventoring.dto.InventsDTO;
import kg.inai.inventoring.entity.Invents;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelHandler {
    public void writeToExcel(OutputStream outputStream, List<Invents> invents) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        createHeaderRow(sheet);
        fillDataRow(sheet, invents);

        workbook.write(outputStream);
        workbook.close();
    }

    private void fillDataRow(Sheet sheet, List<Invents> invents) {
        int rowNum = 1;
        for (Invents invent : invents) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(invent.getId());
            dataRow.createCell(1).setCellValue(invent.getName());
            dataRow.createCell(2).setCellValue(invent.getPicture());
            dataRow.createCell(3).setCellValue(invent.getQr());
            dataRow.createCell(4).setCellValue(String.valueOf(invent.getCategory()));
            dataRow.createCell(5).setCellValue(String.valueOf(invent.getClient()));
            dataRow.createCell(6).setCellValue(String.valueOf(invent.getLocation()));
            dataRow.createCell(7).setCellValue(String.valueOf(invent.getQuality()));
        }
    }

    private Row createHeaderRow(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("Имя");
        row.createCell(2).setCellValue("Фото");
        row.createCell(3).setCellValue("QR");
        row.createCell(4).setCellValue("Категория");
        row.createCell(5).setCellValue("Клиент");
        row.createCell(6).setCellValue("Местоположение");
        row.createCell(6).setCellValue("Качество");
        return row;
    }

    public List<InventsDTO> readFromExcel(InputStream inputStream) throws IOException {
        List<InventsDTO> inventsList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            if (iterator.hasNext()) {
                iterator.next();
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                InventsDTO invents = InventsDTO.builder()
                        .name(getStringValue(currentRow.getCell(1)))
                        .picture(getStringValue(currentRow.getCell(2)))
                        .qr(getStringValue(currentRow.getCell(3)))
                        .category(getStringValue(currentRow.getCell(4)))
                        .client(getStringValue(currentRow.getCell(5)))
                        .location(getStringValue(currentRow.getCell(6)))
                        .quality(getStringValue(currentRow.getCell(7)))
                        .build();
                inventsList.add(invents);
            }
        }
        return inventsList;
    }

    private String getStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        CellType cellType = cell.getCellType();
        if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cellType == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return "";
        }
    }
}

