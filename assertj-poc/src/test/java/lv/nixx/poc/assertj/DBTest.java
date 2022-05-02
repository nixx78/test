package lv.nixx.poc.assertj;

import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.db.api.Assertions.assertThat;

@SpringBootTest
public class DBTest {

    private static final Logger log = LoggerFactory.getLogger(DBTest.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Test
    void test() {

        prepareData();

        jdbcTemplate.query(
                "SELECT id, account_id, amount FROM transactions",
                (rs, rowNum) -> new TransactionDTO(
                        rs.getString("id"),
                        rs.getString("account_id"),
                        rs.getBigDecimal("amount"),
                        null)
        ).forEach(customer -> log.info(customer.toString()));

        Table table = new Table(dataSource, "transactions");

        assertThat(table).column("account_id")
                .value().isEqualTo("ACC1")
                .value().isEqualTo("ACC1")
                .value().isEqualTo("ACC2")
                .value().isEqualTo("ACC2");

        assertThat(table).row(0)
                .value().isEqualTo(1)
                .value().isEqualTo("ACC1")
                .value().isEqualTo(10.01);
    }

    private void prepareData() {
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE transactions IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE transactions(" +
                "id SERIAL," +
                "account_id VARCHAR(255)," +
                "amount decimal(10,2)" +
                ")");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> txns = List.of(
                new Object[]{"ACC1", 10.01},
                new Object[]{"ACC1", 20.02},
                new Object[]{"ACC2", 30.03},
                new Object[]{"ACC2", 40.03}
        );

        txns.forEach(t -> log.info(String.format("Inserting transactions record for %s %s", t[0], t[1])));
        jdbcTemplate.batchUpdate("INSERT INTO transactions(account_id, amount) VALUES (?,?)", txns);
    }
}


