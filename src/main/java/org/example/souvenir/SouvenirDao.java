package org.example.souvenir;


import lombok.SneakyThrows;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class SouvenirDao {
    public static SouvenirDao instance;
    public static List<Souvenir> souvenirs;

    public static SouvenirDao getInstance() {
        instance = instance != null ? instance : new SouvenirDao();
        return instance;
    }
    public List<Souvenir> getSouvenirs(){
        return souvenirs;
    }
    public void addSouvenir(Souvenir souvenir){
        souvenirs.add(souvenir);
    }
    public boolean removeSouvenir(Souvenir souvenir){
        return souvenirs.removeIf(s->s.name.equals(souvenir.name)&&s.props.equals(souvenir.props));
    }
    @SneakyThrows
    public SouvenirDao() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("souvenir.sv"))) {
            souvenirs = (List<Souvenir>) in.readObject();
        } catch (FileNotFoundException e) {
            souvenirs = Collections.emptyList();
        }
    }

    @SneakyThrows
    public static void close() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("souvenir.sv"))) {
            if (souvenirs==null)
                souvenirs=Collections.emptyList();
            out.writeObject(souvenirs);
        }
    }

}
