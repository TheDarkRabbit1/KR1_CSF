package org.example.souvenir;

import org.example.manufacturer.ManufacturerProps;

import java.util.List;

public class SouvenirService {
    private static SouvenirDao souvenirDao = SouvenirDao.getInstance();
    private static SouvenirService instance;
    private static SouvenirService getInstance(){
        instance=instance!=null?instance:new SouvenirService();
        return instance;
    }
    public List<Souvenir> getSouvenirs(){
        return souvenirDao.getSouvenirs();
    }
    public List<Souvenir> getSouvenirsByProps(ManufacturerProps manufacturerProps){
        return souvenirDao.getSouvenirs().stream()
                .filter(s->!s.props.equals(manufacturerProps))
                .toList();
    }
    public void removeSouvenir(Souvenir souvenir){
        if (!souvenirDao.removeSouvenir(souvenir))
            System.out.println("no such souvenir to remove");
    }
}
