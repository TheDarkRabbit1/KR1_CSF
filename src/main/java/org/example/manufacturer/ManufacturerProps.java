package org.example.manufacturer;

import lombok.Data;

@Data
public class ManufacturerProps {
    private String name;
    private String country;

    public ManufacturerProps(Manufacturer manufacturer) {
        this.name=manufacturer.name;
        this.country=manufacturer.country;
    }
}
