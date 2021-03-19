package lv.nixx.poc.dbunit.data;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;

@Service
public class CSVTestData extends MockData{

    @Autowired
    public CSVTestData(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    void load(IDatabaseConnection connection) throws Exception {
        CsvDataSet testData = new CsvDataSet(new File("./src/main/resources/test_data/csv"));
        DatabaseOperation.CLEAN_INSERT.execute(connection, testData);
    }
}
