package org.example.manufacturer;

import java.util.List;

public class ManufacturerService {
    private static final ManufacturerDao manufacturerDao = ManufacturerDao.getInstance();
    private static ManufacturerService instance;

    public static ManufacturerService getInstance() {
        instance=instance!=null?instance:new ManufacturerService();
        return instance;
    }
    public List<Manufacturer> getManufacturers(){
        return manufacturerDao.getManufacturers();
    }
    public void addManufacturer(Manufacturer manufacturer){
        if (manufacturerDao.getManufacturers().stream().findFirst().isPresent()){
            System.out.println("Manufacturer with this name already exist");
            return;
        }
        manufacturerDao.addManufacturer(manufacturer);
    }
    public void editManufacturer(Manufacturer newManufacturer){
        Manufacturer manufacturer = manufacturerDao.getManufacturers().stream()
                .findFirst()
                .orElseThrow();
        manufacturerDao.removeManufacturer(manufacturer.name);
        manufacturerDao.addManufacturer(manufacturer);
    }
    public void removeManufacturer(String manufacturer){
        if (!manufacturerDao.removeManufacturer(manufacturer))
            System.out.println("no such manufacturer found");
    }

}
