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
import java.util.function.Function;
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
            default -> System.out.println("no such option in menu");
        }
    }

    private static void addSouvenir() {
        System.out.println("""
                name
                props [name, country]
                date [01-01-1999]
                price [integer]
                """);
        String name= scanner.next();
        ManufacturerProps props = new ManufacturerProps(scanner.next(), scanner.next());
        int[] dateParams = Arrays.stream(scanner.next().split(" ")).mapToInt(Integer::valueOf).toArray();
        LocalDate date = LocalDate.of(dateParams[2],dateParams[1],dateParams[0]);
        int price = scanner.nextInt();
        souvenirService.addSouvenir(new Souvenir(name,props,date,price));
    }

    private static void addManufacturer(){
        System.out.println("input name and country:");
        manufacturerService.addManufacturer(new Manufacturer(scanner.next(),scanner.next()));
    }

    private static void viewAll(List<?> list){
        list.forEach(System.out::println);
    }

    private static void souvenirsByManufacturer() {
        souvenirService.getSouvenirsByProps(inputManufacturerProps()).forEach(System.out::println);
    }

    private static void souvenirsByCountry() {
        souvenirService.getSouvenirsByCountry(scanner.next()).forEach(System.out::println);
    }

    private static void manufacturerSouvenirsBeforePrice() {
        System.out.println("price:");
        souvenirService.getSouvenirsBeforePrice(scanner.nextInt()).forEach(System.out::println);
    }

    private static void manufacturerListWithTheirSouvenirs() {
        ManufacturerProps props = inputManufacturerProps();
        manufacturerService.getManufacturers().forEach(
                m-> souvenirService.getSouvenirsByProps(props).forEach(System.out::println));
    }

    private static void souvenirListByYear() {
        System.out.println("enter a year:");
        souvenirService.getSouvenirsByYear(scanner.nextInt()).forEach(System.out::println);

    }

    private static void byEachYearSouvenirs() {
        //todo probably gives 1 s by each year
        Map<Integer, Souvenir> map = souvenirService.getSouvenirs().stream()
                .collect(Collectors.toMap(Souvenir::getYear, Function.identity()));
        map.forEach((key, value) -> System.out.println(key + "\n" + value.toString()));
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