package lv.nixx.poc.dbunit.config;

import org.hsqldb.cmdline.SqlFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;

@Configuration
public class AppConfiguration {

    @Bean
    protected DataSource getDataSource() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        dataSource.setUrl("jdbc:hsqldb:mem:TestDB");
        dataSource.setUsername("test");
        dataSource.setPassword("test");

        try {
            SqlFile sf = new SqlFile(new File("./src/main/resources/schema.sql"));
            sf.setConnection(dataSource.getConnection());
            sf.execute();

        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

        return dataSource;
    }


}
