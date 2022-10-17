package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.zip.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TestFile {

    ClassLoader cl = TestFile.class.getClassLoader();
    String zipFile = "testFiles.zip";


    @DisplayName("Csv")
    @Test
    void zipCsvTest() throws Exception {
        try (ZipFile zipF= new ZipFile(new File("src/test/resources/"+zipFile))) {
            ZipInputStream is = new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream(zipFile)));
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    try (InputStream inputStream = zipF.getInputStream(entry)) {
                        CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
                        List<String[]> content = reader.readAll();
                        String[] row = content.get(1);
                        assertThat(row[0]).isEqualTo("Gleb");
                        assertThat(row[1]).isEqualTo("26");
                    }
                }
            }
        }
    }

    @DisplayName("Pdf")
    @Test
    void zipPdfTest() throws Exception {
        try (ZipFile zipF = new ZipFile(new File("src/test/resources/"+zipFile))) {
            ZipInputStream is = new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream(zipFile)));
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().contains(".pdf")) {
                    try (InputStream inputStream = zipF.getInputStream(entry)) {
                        PDF pdf = new PDF(inputStream);
                        assertThat(pdf.text).contains("PDF for test");
                    }
                }
            }
        }
    }

    @DisplayName("Xlsx")
    @Test
    void zipXlsxTest() throws Exception {
        try (ZipFile zipF = new ZipFile(new File("src/test/resources/"+zipFile))) {
            ZipInputStream is = new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream(zipFile)));
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if (entry.getName().contains(".xlsx")) {
                    try (InputStream inputStream = zipF.getInputStream(entry)) {
                        XLS xls = new XLS(inputStream);
                        assertThat(
                                xls.excel.getSheetAt(0)
                                        .getRow(1)
                                        .getCell(0)
                                        .getStringCellValue()
                        ).isEqualTo("test");
                        assertThat(
                                xls.excel.getSheetAt(0)
                                        .getRow(2)
                                        .getCell(0)
                                        .getStringCellValue()
                        ).isEqualTo("yes");
                    }
                }
            }
        }
    }

}
