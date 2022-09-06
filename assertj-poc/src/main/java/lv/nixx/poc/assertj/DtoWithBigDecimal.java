package lv.nixx.poc.assertj;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class DtoWithBigDecimal {
    private BigDecimal amount;
}
