package lv.nixx.poc.dbunit.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Service
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Collection<Person> getAllPersons() {
        return jdbcTemplate.query("Select * from Person_tbl", new PMapper());
    }

    static class PMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Person()
                    .setId(resultSet.getInt("id"))
                    .setName(resultSet.getString("name"))
                    .setSurname(resultSet.getString("surname"));
        }
    }

}
