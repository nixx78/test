package lv.nixx.poc.assertj.db;

import lv.nixx.poc.assertj.TransactionDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;


@Service
public class TransactionDAO {

    private final JdbcTemplate jdbcTemplate;

    public TransactionDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void saveTransaction(TransactionDTO txn) {
        jdbcTemplate.update("");
    }

}
