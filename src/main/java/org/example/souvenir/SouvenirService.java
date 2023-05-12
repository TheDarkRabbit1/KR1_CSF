package org.example.souvenir;

import org.example.manufacturer.ManufacturerProps;

import java.util.List;

public class SouvenirService {
    private static final SouvenirDao souvenirDao = SouvenirDao.getInstance();
    private static SouvenirService instance;
    public static SouvenirService getInstance(){
        instance=instance!=null?instance:new SouvenirService();
        return instance;
    }

    private SouvenirService() {}

    public List<Souvenir> getSouvenirs(){
        return souvenirDao.getSouvenirs();
    }
    public void addSouvenir(Souvenir souvenir){
        souvenirDao.addSouvenir(souvenir);
    }
    public List<Souvenir> getSouvenirsByManufacturerName(String mName){
        return souvenirDao.getSouvenirs().stream().filter(s->s.props.getName().equals(mName)).toList();
    }
    public List<Souvenir> getSouvenirsByCountry(String countryName){
        return souvenirDao.getSouvenirs().stream().filter(s->s.props.getCountry().equals(countryName)).toList();
    }
    public List<Souvenir> getSouvenirsBeforePrice(int n){
        return souvenirDao.getSouvenirs().stream().filter(s->s.price<n).toList();
    }
    public List<Souvenir> getSouvenirsByProps(ManufacturerProps manufacturerProps){
        return souvenirDao.getSouvenirs().stream().filter(s->s.props.equals(manufacturerProps)).toList();
    }
    public void removeAllSouvenirsByProps(ManufacturerProps props){
        souvenirDao.getSouvenirs().removeIf(s->s.props.equals(props));
    }
    public void removeSouvenir(String name, ManufacturerProps props){
        if (!souvenirDao.removeSouvenir(name, props))
            System.out.println("no such souvenir to remove");
    }

    public List<Souvenir> getSouvenirsByYear(int year) {
        return souvenirDao.getSouvenirs().stream().filter(s -> s.getYear()==year).toList();
    }

    public void editSouvenir(String name, ManufacturerProps props, Souvenir newSouvenir) {
        Souvenir souvenirToUpdate = souvenirDao.getSouvenirs().stream()
                .filter(s->s.name.equals(name)&&s.props.equals(props))
                .findFirst()
                .orElseThrow();
        souvenirToUpdate.setName(newSouvenir.getName());
        souvenirToUpdate.getProps().setName(newSouvenir.getProps().getName());
        souvenirToUpdate.getProps().setCountry(newSouvenir.getProps().getCountry());
        souvenirToUpdate.setManufacturingDate(newSouvenir.getManufacturingDate());
        souvenirToUpdate.setPrice(newSouvenir.getPrice());

    }
}
