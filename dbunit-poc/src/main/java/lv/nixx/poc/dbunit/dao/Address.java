package lv.nixx.poc.dbunit.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Address {
    private int address_id;
    private String line;
}
