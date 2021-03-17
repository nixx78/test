package lv.nixx.poc.dbunit.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {
    private int id;
    private String name;
    private String surname;
}
