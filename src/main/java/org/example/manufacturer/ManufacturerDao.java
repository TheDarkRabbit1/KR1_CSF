package org.example.manufacturer;

import lombok.SneakyThrows;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class ManufacturerDao {
    private static ManufacturerDao instance;
    private static List<Manufacturer> manufacturers;

    public static ManufacturerDao getInstance() {
        instance = instance != null ? instance : new ManufacturerDao();
        return instance;
    }
    public void addManufacturer(Manufacturer m){
        manufacturers.add(m);
    }
    public boolean removeManufacturer(String name) {
        return manufacturers.removeIf(m->m.name.equals(name));
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    @SneakyThrows
    public ManufacturerDao() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("manufacturer.sv"))) {
            manufacturers = (List<Manufacturer>) in.readObject();
        } catch (FileNotFoundException e){
            manufacturers = Collections.emptyList();
        }
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
