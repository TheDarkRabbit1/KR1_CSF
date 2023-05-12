package org.example;

import org.example.manufacturer.Manufacturer;
import org.example.manufacturer.ManufacturerDao;
import org.example.manufacturer.ManufacturerProps;
import org.example.manufacturer.ManufacturerService;
import org.example.souvenir.Souvenir;
import org.example.souvenir.SouvenirDao;
import org.example.souvenir.SouvenirService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ManufacturerService manufacturerService = ManufacturerService.getInstance();
    private static final SouvenirService souvenirService = SouvenirService.getInstance();

    public static void main(String[] args) {
        while (true) {
            mainMenu();
            switch (scanner.nextInt()) {
                case 1 -> managementMenu();
                case 2 -> souvenirsByManufacturer();
                case 3 -> souvenirsByCountry();
                case 4 -> manufacturerSouvenirsBeforePrice();
                case 5 -> manufacturerListWithTheirSouvenirs();
                case 6 -> souvenirListByYear();
                case 7 -> byEachYearSouvenirs();
                case 8 -> deleteManufacturerWithHisSouvenirs();
                case 0 -> {
                    saveAndClose();
                    return;
                }
                default -> System.out.println("No such option in menu");
            }
        }
    }

    private static void managementMenu() {
        System.out.print("""
                1 - Manufacturer
                2 - Souvenir
                """);
        int menuOption = scanner.nextInt();
        System.out.print("""
                1 - view all
                2 - add
                3 - edit
                4 - remove
                """);
        int manipulationOption = scanner.nextInt();
        switch(manipulationOption){
            case 1 -> viewAll(menuOption==1?manufacturerService.getManufacturers():souvenirService.getSouvenirs());
            case 2 -> {if (menuOption==1){addManufacturer();}else{addSouvenir();}}
            case 3 -> {if (menuOption==1){editManufacturer();}else{editSouvenir();}}
            case 4 -> {if (menuOption==1){removeManufacturer();}else {removeSouvenir();}}
            default -> System.out.println("no such option in menu");
        }
    }

    private static void removeManufacturer() {
        System.out.println("enter name and country of manufacturing:");
        manufacturerService.removeManufacturer(new ManufacturerProps(scanner.next(), scanner.next()));
    }

    private static void removeSouvenir() {
        System.out.println("enter souvenir name, manufacturer name and country");
        souvenirService.removeSouvenir(scanner.next(),new ManufacturerProps(scanner.next(), scanner.next()));
    }

    private static void editManufacturer(){
        System.out.println("enter props of Manufacturer");
        ManufacturerProps props  = new ManufacturerProps(scanner.next(), scanner.next());
        List<Manufacturer> manufacturers = manufacturerService.getManufacturersByProps(props);
        Manufacturer manufacturer;
        if (manufacturers.isEmpty()){
            System.out.println("no manufacturer was found");
            return;
        }else{
            System.out.println("pick manufacturer from the list:");
            for (int i = 0; i < manufacturers.size(); i++) {
                System.out.println(i+" - "+manufacturers.get(i));
            }
            manufacturer=manufacturers.get(scanner.nextInt());
        }
        System.out.println("enter new name or country of manufacturer (no changes if line`s '-' )");
        manufacturerService.editManufacturer(manufacturer,new ManufacturerProps(scanner.next(),scanner.next()));
    }
    private static void editSouvenir(){
        System.out.println("old souvenir`s name and props:");
        String name = scanner.next();
        ManufacturerProps props = new ManufacturerProps(scanner.next(), scanner.next());
        souvenirService.editSouvenir(name,props, manageSouvenir());
    }
    private static Souvenir manageSouvenir() {
        System.out.print("""
                name
                props [name, country]
                date [01-01-1999]
                price [integer]
                """);
        String name= scanner.next();
        ManufacturerProps props = new ManufacturerProps(scanner.next(), scanner.next());
        int[] dateParams = Arrays.stream(scanner.next().split("-")).mapToInt(Integer::valueOf).toArray();
        LocalDate date = LocalDate.of(dateParams[2],dateParams[1],dateParams[0]);
        int price = scanner.nextInt();
        return new Souvenir(name,props,date,price);
    }
    private static void addSouvenir(){
        souvenirService.addSouvenir(manageSouvenir());
    }

    private static void addManufacturer(){
        manufacturerService.addManufacturer(manageManufacturer());
    }
    private static Manufacturer manageManufacturer(){
        System.out.println("input name and country:");
        return new Manufacturer(scanner.next(),scanner.next());
    }

    private static void viewAll(List<?> list){
        list.forEach(System.out::println);
    }

    private static void souvenirsByManufacturer() {
        List<Souvenir> list = souvenirService.getSouvenirsByProps(inputManufacturerProps());
        if (list.isEmpty()){
            System.out.println("No souvenirs by this manufacturer props were found");
        }else{list.forEach(System.out::println);}
    }

    private static void souvenirsByCountry() {
        List<Souvenir> list = souvenirService.getSouvenirsByCountry(scanner.next());
        if (list.isEmpty()){
            System.out.println("no matching souvenirs were found");
        }else{list.forEach(System.out::println);}
    }

    private static void manufacturerSouvenirsBeforePrice() {
        System.out.println("price:");
        souvenirService.getSouvenirsBeforePrice(scanner.nextInt()).forEach(System.out::println);
    }

    private static void manufacturerListWithTheirSouvenirs() {
        Map<String, List<Souvenir>> souvenirsByManufacturer = souvenirService.getSouvenirs().stream()
                .collect(Collectors.groupingBy(s -> s.getProps().getName()+" "+s.getProps().getCountry()));
        souvenirsByManufacturer.forEach((manufacturer, souvenirs) -> {
            System.out.println("Manufacturer: " + manufacturer);
            souvenirs.forEach(souvenir -> System.out.println(souvenir.toString()));
        });
    }

    private static void souvenirListByYear() {
        System.out.println("enter a year:");
        souvenirService.getSouvenirsByYear(scanner.nextInt()).forEach(System.out::println);

    }

    private static void byEachYearSouvenirs() {
        Map<Integer, List<Souvenir>> map = souvenirService.getSouvenirs().stream()
                .collect(Collectors.groupingBy(Souvenir::getYear));
        map.forEach((key, value) -> {
            System.out.println(key);
            value.forEach(souvenir -> System.out.println(souvenir.toString()));
        });
    }

    private static void deleteManufacturerWithHisSouvenirs() {
        ManufacturerProps props = inputManufacturerProps();
        manufacturerService.removeManufacturer(props);
        souvenirService.removeAllSouvenirsByProps(props);
    }

    private static ManufacturerProps inputManufacturerProps() {
        System.out.println("Manufacturer country:");
        String country = scanner.next();
        System.out.println("Manufacturer name:");
        String name = scanner.next();
        return new ManufacturerProps(name, country);
    }
    private static void mainMenu(){
        System.out.print("""
                1 - manage all manufacturers and souvenirs
                2 - souvenirs by manufacturer
                3 - souvenirs by country of manufacturing
                4 - manufacturers where price less than...
                5 - manufacturers list and their souvenirs
                6 - souvenir list by year...
                7 - for each year out souvenir list
                8 - delete manufacturer and his souvenirs
                0 - exit
                """);
    }
    private static void saveAndClose() {
        SouvenirDao.getInstance().close();
        ManufacturerDao.getInstance().close();
    }
}