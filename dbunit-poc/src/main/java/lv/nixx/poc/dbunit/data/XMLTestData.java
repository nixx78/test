package lv.nixx.poc.dbunit.data;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class XMLTestData {

    private DataSource dataSource;

    @Autowired
    public XMLTestData(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void load() throws Exception {
        FlatXmlDataSet testData = new FlatXmlDataSetBuilder().build(getClass()
                .getClassLoader()
                .getResourceAsStream("test_data/xml_test_data.xml"));

        IDatabaseConnection con = new DatabaseConnection(dataSource.getConnection());
        DatabaseOperation.CLEAN_INSERT.execute(con, testData);

    }

}
