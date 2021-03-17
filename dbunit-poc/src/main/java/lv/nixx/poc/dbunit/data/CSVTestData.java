package lv.nixx.poc.dbunit.data;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.CsvDataFileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;

@Service
public class CSVTestData {

    private DataSource dataSource;

    @Autowired
    public CSVTestData(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void load() throws Exception {
        IDatabaseConnection con = new DatabaseConnection(dataSource.getConnection());

        CsvDataSet testData = new CsvDataSet(new File("./src/main/resources/test_data/csv"));
        DatabaseOperation.CLEAN_INSERT.execute(con, testData);
    }

}
