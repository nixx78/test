package lv.nixx.poc.cucumber.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Collection;

@AllArgsConstructor
@Getter
@ToString
public class Transactions {

    final private Collection<Transaction> txns;
    final private BigDecimal total;

}
