package org.example.manufacturer;

import lombok.Data;

import java.io.Serializable;

//ідея пропсів в незалежності коду від розширення класу Manufacturer
@Data
public class ManufacturerProps implements Serializable {
    private String name;
    private String country;

    public ManufacturerProps(Manufacturer manufacturer) {
        this.name=manufacturer.name;
        this.country=manufacturer.country;
    }

    public ManufacturerProps(String name, String country) {
        this.name = name;
        this.country = country;
    }
}
