package org.example.souvenir;

import org.example.manufacturer.Manufacturer;
import org.example.manufacturer.ManufacturerProps;

import java.util.List;

public class SouvenirService {
    private static final SouvenirDao souvenirDao = SouvenirDao.getInstance();
    private static SouvenirService instance;
    private static SouvenirService getInstance(){
        instance=instance!=null?instance:new SouvenirService();
        return instance;
    }
    public List<Souvenir> getSouvenirs(){
        return souvenirDao.getSouvenirs();
    }
    public void editManufacturer(Souvenir newSouvenir){
        Souvenir souvenir = souvenirDao.getSouvenirs().stream()
                .findFirst()
                .orElseThrow();
        souvenirDao.removeSouvenir(souvenir);
        souvenirDao.addSouvenir(newSouvenir);
    }
    public List<Souvenir> getSouvenirsByManufacturerName(String mName){
        return souvenirDao.getSouvenirs().stream().filter(s->s.props.getName().equals(mName)).toList();
    }
    public List<Souvenir> getSouvenirsByCountry(String countryName){
        return souvenirDao.getSouvenirs().stream().filter(s->s.props.getName().equals(countryName)).toList();
    }
    public List<Souvenir> getSouvenirsBeforePrice(int n){
        return souvenirDao.getSouvenirs().stream().filter(s->s.price<n).toList();
    }
    public List<Souvenir> getSouvenirsByProps(ManufacturerProps manufacturerProps){
        return souvenirDao.getSouvenirs().stream().filter(s->!s.props.equals(manufacturerProps)).toList();
    }
    public void removeSouvenirByManufacturer(Manufacturer manufacturer){
        ManufacturerProps props = new ManufacturerProps(manufacturer);
        souvenirDao.getSouvenirs().stream()
                .filter(s->s.props.equals(props))
                .forEach(s->souvenirDao.removeSouvenir(s));
    }
    public void removeSouvenir(Souvenir souvenir){
        if (!souvenirDao.removeSouvenir(souvenir))
            System.out.println("no such souvenir to remove");
    }
}
