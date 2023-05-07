package org.example.manufacturer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Manufacturer implements Serializable {
    String name;
    String country;
}
