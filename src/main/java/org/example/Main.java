package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        while (true){
            main.mainMenu();
            switch (scanner.nextInt()){
                case 1 -> managementMenu();
                case 2 -> souvenirsByManufacturer();
                case 3 -> souvenirsByCountry();
                case 4 -> manufacturerSouvenirsBeforePrice();
                case 5 -> manufacturerListWithTheirSouvenirs();
                case 6 -> souvenirListByYear();
                case 7 -> byEachYearSouvenirs();
                case 8 -> deleteManufacturerWithHisSouvenirs();
                case 0 -> {return;}
                default -> System.out.println("No such option in menu");
            }
        }
    }

    private static void managementMenu() {

    }

    private static void souvenirsByManufacturer() {

    }

    private static void souvenirsByCountry() {

    }

    private static void manufacturerSouvenirsBeforePrice() {

    }

    private static void manufacturerListWithTheirSouvenirs() {

    }

    private static void souvenirListByYear() {

    }

    private static void byEachYearSouvenirs() {

    }

    private static void deleteManufacturerWithHisSouvenirs() {
    }

    private void mainMenu() {
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
}