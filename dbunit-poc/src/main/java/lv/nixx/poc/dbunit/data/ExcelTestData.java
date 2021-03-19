package lv.nixx.poc.dbunit.data;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hsqldb.cmdline.SqlFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;

@Service
public class ExcelTestData extends MockData {

    @Autowired
    public ExcelTestData(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void load(IDatabaseConnection connection) throws Exception {
        XlsDataSet testData = new XlsDataSet(new File("./src/main/resources/test_data/test_data_from_excel.xls"));
        DatabaseOperation.CLEAN_INSERT.execute(connection, testData);
    }
}
