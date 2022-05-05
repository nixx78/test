package lv.nixx.poc.assertj;

import org.assertj.db.type.Changes;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.db.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@TestInstance(PER_CLASS)
public class DBAssertionsTest {

    private static final Logger log = LoggerFactory.getLogger(DBAssertionsTest.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Test
    void tableSample() {

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

    @Test
    void requestFromTableSample() {

        // https://joel-costigliola.github.io/assertj/assertj-db-concepts.html#request

        Request s = new Request(dataSource, "select * from transactions where account_id=?", "XACC");
        assertThat(s).row(0)
                .value().isEqualTo(5)
                .value().isEqualTo("XACC")
                .value().isEqualTo(50.03);
    }

    @Test
    void changesInTableSample() {

        // https://github.com/assertj/assertj-examples/blob/main/assertions-examples/src/test/java/org/assertj/examples/db/ChangesAssertionExamples.java

        Changes changes = new Changes(new Table(dataSource, "transactions"));
        changes.setStartPointNow();

        jdbcTemplate.batchUpdate("INSERT INTO transactions(account_id, amount) VALUES (?,?)", List.of(
                new Object[]{"NEW_ACC_1", 10.01},
                new Object[]{"NEW_ACC_2", 10.02}
        ));

        changes.setEndPointNow();

        assertThat(changes).hasNumberOfChanges(2)
                .ofCreation().hasNumberOfChanges(2)
                .ofModification().hasNumberOfChanges(0)
                .ofDeletion().hasNumberOfChanges(0);

        assertThat(changes)
                .change()
                    .isCreation()
                    .hasPksNames("id")
                    .hasPksValues(6)
                .change()
                    .isCreation()
                    .hasPksNames("id")
                    .hasPksValues(7);

    }

    @BeforeAll
    void prepareData() {
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE transactions IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE transactions(" +
                "id SERIAL primary key," +
                "account_id VARCHAR(255)," +
                "amount decimal(10,2)" +
                ")");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> txns = List.of(
                new Object[]{"ACC1", 10.01},
                new Object[]{"ACC1", 20.02},
                new Object[]{"ACC2", 30.03},
                new Object[]{"ACC2", 40.03},
                new Object[]{"XACC", 50.03}
        );

        txns.forEach(t -> log.info("Inserting transactions record for {} {}", t[0], t[1]));
        jdbcTemplate.batchUpdate("INSERT INTO transactions(account_id, amount) VALUES (?,?)", txns);
    }

}


