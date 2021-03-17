package lv.nixx.poc.dbunit.controller;

import lv.nixx.poc.dbunit.data.CSVTestData;
import lv.nixx.poc.dbunit.data.ExcelTestData;
import lv.nixx.poc.dbunit.data.XMLTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDataController {

    private final XMLTestData xmlTestData;
    private final ExcelTestData excelTestData;
    private final CSVTestData csvTestData;

    @Autowired
    public TestDataController(XMLTestData xmlTestData, ExcelTestData excelTestData, CSVTestData csvTestData) {
        this.xmlTestData = xmlTestData;
        this.excelTestData = excelTestData;
        this.csvTestData = csvTestData;
    }

    @GetMapping("/person/load/xml")
    public void loadDataFromXML() throws Exception {
        xmlTestData.load();
    }

    @GetMapping("/person/load/excel")
    public void loadDataFromExcel() throws Exception {
        excelTestData.load();
    }

    @GetMapping("/person/load/csv")
    public void loadDataFromCSV() throws Exception {
        csvTestData.load();
    }
}
