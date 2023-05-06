package org.example.souvenir;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Souvenir {
    String name;
    String props;
    LocalDate manufacturingDate;
    int price;
}
