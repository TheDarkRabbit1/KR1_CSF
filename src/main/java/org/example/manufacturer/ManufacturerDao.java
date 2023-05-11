package org.example.manufacturer;

import lombok.SneakyThrows;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManufacturerDao {
    private static ManufacturerDao instance;
    private static List<Manufacturer> manufacturers;

    @SneakyThrows
    public ManufacturerDao() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("manufacturer.sv"))) {
            manufacturers = (List<Manufacturer>) in.readObject();
        } catch (FileNotFoundException e) {
            manufacturers = Collections.emptyList();
        }
        if (manufacturers.isEmpty()) {
            manufacturers = new ArrayList<>();
        }
    }

    public static ManufacturerDao getInstance() {
        instance = instance != null ? instance : new ManufacturerDao();
        return instance;
    }

    public void addManufacturer(Manufacturer m) {
        manufacturers.add(m);
    }
    public boolean removeManufacturer(Manufacturer manufacturer){
        return manufacturers.removeIf(manufacturer::equals);
    }
    public boolean removeManufacturer(ManufacturerProps props) {
        return manufacturers.removeIf(m -> m.name.equals(props.getName())
                && m.country.equals(props.getCountry()));
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    @SneakyThrows
    public void close() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("manufacturer.sv"))) {
            if (manufacturers == null)
                manufacturers = Collections.emptyList();
            out.writeObject(manufacturers);
        }
    }
}
