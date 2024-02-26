package org.candyfrontend.form;

import lombok.Data;

@Data
public class Candy {
    private long id;
    private String name;
    private String manufacturingCompany;
    private int price;

}
