package lv.nixx.poc.dbunit.controller;

import lv.nixx.poc.dbunit.data.CSVTestData;
import lv.nixx.poc.dbunit.data.ExcelTestData;
import lv.nixx.poc.dbunit.data.MockData;
import lv.nixx.poc.dbunit.data.XMLTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestDataController {

    private final Map<String, MockData> mockDataMap;

    @Autowired
    public TestDataController(XMLTestData xmlTestData, ExcelTestData excelTestData, CSVTestData csvTestData) {

        mockDataMap = Map.of(
                "xml", xmlTestData,
                "excel", excelTestData,
                "csv", csvTestData
        );
    }

    @GetMapping("/person/load/{source}")
    public void loadMockData(@PathVariable String source) throws Exception {
        MockData mockData = mockDataMap.get(source);

        if (mockData == null) {
            throw new IllegalArgumentException("Data source:" + source + " not exists");
        }

        mockData.load();
    }

}
