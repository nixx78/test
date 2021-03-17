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
public class ExcelTestData {

    private DataSource dataSource;

    @Autowired
    public ExcelTestData(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void load() throws Exception {
        IDatabaseConnection con = new DatabaseConnection(dataSource.getConnection());

        XlsDataSet testData = new XlsDataSet(new File("./src/main/resources/test_data/test_data_from_excel.xls"));
        DatabaseOperation.CLEAN_INSERT.execute(con, testData);

    }

}
