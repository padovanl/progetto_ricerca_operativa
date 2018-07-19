/*
* TSP_GA.java
* Create a tour and evolve a solution
*/

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TSP_GA {

    public static void main(String[] args) {

        // Create and add our cities
        /*City city = new City(60, 200);
        TourManager.addCity(city);
        City city2 = new City(180, 200);
        TourManager.addCity(city2);
        City city3 = new City(80, 180);
        TourManager.addCity(city3);
        City city4 = new City(140, 180);
        TourManager.addCity(city4);
        City city5 = new City(20, 160);
        TourManager.addCity(city5);
        City city6 = new City(100, 160);
        TourManager.addCity(city6);
        City city7 = new City(200, 160);
        TourManager.addCity(city7);
        City city8 = new City(140, 140);
        TourManager.addCity(city8);
        City city9 = new City(40, 120);
        TourManager.addCity(city9);
        City city10 = new City(100, 120);
        TourManager.addCity(city10);
        City city11 = new City(180, 100);
        TourManager.addCity(city11);
        City city12 = new City(60, 80);
        TourManager.addCity(city12);
        City city13 = new City(120, 80);
        TourManager.addCity(city13);
        City city14 = new City(180, 60);
        TourManager.addCity(city14);
        City city15 = new City(20, 40);
        TourManager.addCity(city15);
        City city16 = new City(100, 40);
        TourManager.addCity(city16);
        City city17 = new City(200, 40);
        TourManager.addCity(city17);
        City city18 = new City(20, 20);
        TourManager.addCity(city18);
        City city19 = new City(60, 20);
        TourManager.addCity(city19);
        City city20 = new City(160, 20);
        TourManager.addCity(city20);*/

        readCitiesFile();
        //readSolution();

        // Initialize population
        Population pop = new Population(50, true); //50
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations
        long inizio = System.currentTimeMillis();
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 200000; i++) {
            pop = GA.evolvePopulation(pop);
        }

        long fine = System.currentTimeMillis();

        long durata = fine - inizio;

        String durataStr = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(durata),
                        TimeUnit.MILLISECONDS.toSeconds(durata) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durata))
        );

        System.out.println("Durata: " + durataStr + "\n");
        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
    }



    public static void readCitiesFile(){
        try {
            File f = new File("/home/luca/Scrivania/progetto_ricerca_operativa/TSP_GA/src/dati.dat");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            //System.out.println("Reading file using Buffered Reader");
            //PrintWriter writer = new PrintWriter("/home/luca/workspace/TSPSimulatedAnnealing/src/datiModificati.dat", "UTF-8");
            //writer.println("The first line");
            //writer.println("The second line");
            //writer.close();
            ArrayList<City> temp = new ArrayList<City>();
            while ((readLine = b.readLine()) != null) {
                String[] s = readLine.trim().split("\\s+");
                //writer.println(s[0] + " " + s[1] + " " + s[2] + " " + s[9]);
                TourManager.addCity(new City(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
                //temp.add(new City(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2])));
            }

            b.close();
            //writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<City> readCities(){
        try {
            ArrayList<City> returnValue = new ArrayList<>();
            File f = new File("/home/luca/Scrivania/progetto_ricerca_operativa/TSPSimulatedAnnealing/src/dati.dat");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            //System.out.println("Reading file using Buffered Reader");
            //PrintWriter writer = new PrintWriter("/home/luca/workspace/TSPSimulatedAnnealing/src/datiModificati.dat", "UTF-8");
            //writer.println("The first line");
            //writer.println("The second line");
            //writer.close();
            ArrayList<City> temp = new ArrayList<City>();
            while ((readLine = b.readLine()) != null) {
                String[] s = readLine.trim().split("\\s+");

                //TourManager.addCity(new City(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
                returnValue.add(new City(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
            }

            b.close();
            //writer.close();
            return returnValue;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void readSolution(){
        String[] solution = {"0", "1", "2", "21", "3", "22", "4", "23", "5", "24", "6", "25", "7", "26", "8", "27", "49", "28", "50", "29", "51", "30", "52", "31", "53", "32", "54", "33", "55", "34", "56", "35", "57", "36", "58", "37", "59", "38", "60", "39", "41", "40", "42", "61", "83", "62", "84", "63", "85", "64", "86", "65", "87", "66", "88", "67", "89", "68", "90", "69", "91", "70", "92", "71", "93", "72", "94", "73", "95", "74", "96", "75", "97", "76", "98", "77", "99", "78", "100", "79", "81", "80", "82", "43", "44", "45", "46", "47", "48", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        ArrayList<City> cities = readCities();
        for(int i = 0; i < solution.length; i++){
            String id = solution[i];
            boolean trovato = false;
            for(int j = 0; j < cities.size() && !trovato; j++){
                if(cities.get(j).getId() == Integer.parseInt(id)){
                    TourManager.addCity(new City(cities.get(j).getId(), cities.get(j).getX(), cities.get(j).getY()));
                    trovato = true;
                }
            }
        }

    }
}