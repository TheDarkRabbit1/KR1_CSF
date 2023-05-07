package org.example.souvenir;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.manufacturer.ManufacturerProps;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Souvenir implements Serializable {
    String name;
    ManufacturerProps props;
    LocalDate manufacturingDate;
    int price;
}
