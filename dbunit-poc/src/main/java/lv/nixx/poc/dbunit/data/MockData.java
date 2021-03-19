package lv.nixx.poc.dbunit.data;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import javax.sql.DataSource;

public abstract class MockData {

    private final DataSource dataSource;

    public MockData(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    abstract void load( IDatabaseConnection connection) throws Exception ;

    public void load() throws Exception {
        load(new DatabaseConnection(dataSource.getConnection()));
    }
}
