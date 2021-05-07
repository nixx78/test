package lv.nixx.poc.dbunit.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Collection<Person> getAllPersons() {
        List<Person> query = jdbcTemplate.query("Select p.id, p.name, p.surname, a.id as address_id, a.line" +
                " from PERSON_TBL p Left join ADDRESS_TBL a on p.id = a.person_id  ", new PMapper());

        return new HashSet<>(query);
    }

    static class PMapper implements RowMapper<Person> {

        Map<Integer, Person> personMap = new HashMap<>();

        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");

            Person person = personMap.get(id);
            if (person == null) {
                person = new Person()
                        .setId(id)
                        .setName(resultSet.getString("name"))
                        .setSurname(resultSet.getString("surname"));
                personMap.put(id, person);
            }

            int address_id = resultSet.getInt("address_id");
            if (address_id != 0) {
                person.addAddress(new Address()
                        .setAddress_id(address_id)
                        .setLine(resultSet.getString("line"))
                );
            }
            return person;
        }
    }

}
