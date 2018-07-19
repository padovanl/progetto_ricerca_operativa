/*
* GA.java
* Manages algorithms for evolving population
*/

import java.util.ArrayList;

public class GA {

    /* GA parameters */
    private static final double mutationRate = 0.0015; //0.015
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), false);

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveTour(0, pop.getFittest());
            elitismOffset = 1;
        }

        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            // Select parents
            Tour parent1 = tournamentSelection(pop);
            Tour parent2 = tournamentSelection(pop);
            // Crossover parents
            Tour child = rendiAmmissibile(AggiustaPartenza(crossover(parent1, parent2)));
            //Tour child = crossover(parent1, parent2);
            // Add child to new population
            newPopulation.saveTour(i, child);
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            //newPopulation.saveTour(i,rendiAmmissibile(AggiustaPartenza(mutate(newPopulation.getTour(i)))));
            mutate2(newPopulation.getTour(i));
        }

        return newPopulation;
    }


    public static Tour AggiustaPartenza(Tour route){
        ArrayList<City> temp = route.getCities();

        if(route.getCity(0).getId() != 0) {
            //cerco il magazzino
            boolean trovato = false;
            int indiceMagazzino = -1;
            for (int i = 0; i < temp.size() && !trovato; i++) {
                City citta = temp.get(i);
                if (citta.getId() == 0) {
                    trovato = true;
                    indiceMagazzino = i;
                }
            }

            ArrayList<City> nuovaRoute = new ArrayList<>();
            nuovaRoute.add(temp.get(indiceMagazzino));
            //temp.remove(indiceMagazzino);
            nuovaRoute.addAll(temp.subList(indiceMagazzino + 1, temp.size()));
            nuovaRoute.addAll(temp.subList(0, indiceMagazzino));

            return new Tour(nuovaRoute);
        }else
            return new Tour(temp);
    }

    public static Tour rendiAmmissibile(Tour soluzione){
        ArrayList<City> listaCitta = soluzione.getCities();
        boolean valida = false;
        ArrayList<City> list_temp = listaCitta;

        for(int i = 0; i <listaCitta.size() && !valida; i++){
            City temp = listaCitta.get(i);
            if(!(temp.getId() == 0) && temp.getId() % 2 == 0 && temp.getId() != 0){
                for(int j = i + 1; j < listaCitta.size(); j++){
                    String temp2 = String.valueOf(listaCitta.get(j).getId());
                    int temp3 = Integer.parseInt(temp2);
                    if(temp3 == temp.getId() - 1) {
                        //swap
                        City cittaTemp = listaCitta.get(i);
                        list_temp.set(i, listaCitta.get(j));
                        list_temp.set(j, cittaTemp);
                        break;
                    }
                }
            }
        }
        Tour routeAmmissibile = new Tour(list_temp);
        return routeAmmissibile;
    }



    // Applies crossover to a set of parents and creates offspring
    public static Tour crossover(Tour parent1, Tour parent2) {
        // Create new child tour
        Tour child = new Tour();

        // Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent1.tourSize());
        int endPos = (int) (Math.random() * parent1.tourSize());

        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < child.tourSize(); i++) {
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCity(i, parent1.getCity(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.tourSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsCity(parent2.getCity(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    // Mutate a tour using swap mutation
    private static void mutate(Tour tour) {
        // Loop through tour cities
        for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){
            // Apply mutation rate
            if(Math.random() < mutationRate){
                // Get a second random position in the tour
                int tourPos2 = (int) (tour.tourSize() * Math.random());

                // Get the cities at target position in tour
                City city1 = tour.getCity(tourPos1);
                City city2 = tour.getCity(tourPos2);

                // Swap them around
                tour.setCity(tourPos2, city1);
                tour.setCity(tourPos1, city2);
            }
        }
        //if(tour.getCity(0).getId() != 0)
        //    tour = AggiustaPartenza(tour);
        //tour = rendiAmmissibile(tour);

        //return tour;
    }

    private static void mutate2(Tour tour) {
        // Loop through tour cities
        for(int tourPos1=0; tourPos1 < tour.tourSize(); tourPos1++){
            for(int tourPos2=0; tourPos2 < tour.tourSize(); tourPos2++){
                if(tourPos1 != tourPos2 && tour.getCity(tourPos1).getId() % 2 != 0 && tour.getCity(tourPos2).getId() % 2 != 0 && tour.getCity(tourPos2).getId() != 0 && tour.getCity(tourPos2).getId() != 0){
                    // Apply mutation rate
                    if(Math.random() < mutationRate){
                        // Get a second random position in the tour
                        //int tourPos2 = (int) (tour.tourSize() * Math.random());

                        // Get the cities at target position in tour
                        City pickup1 = tour.getCity(tourPos1);
                        City pickup2 = tour.getCity(tourPos2);

                        int indexDelivery1 = tour.getIndexById(pickup1.getId() + 1);
                        int indexDelivery2 = tour.getIndexById(pickup2.getId() + 1);

                        City delivery1 = tour.getCity(tour.getIndexById(pickup1.getId() + 1));
                        City delivery2 = tour.getCity(tour.getIndexById(pickup2.getId() + 1));

                        // Swap them around
                        tour.setCity(tourPos2, pickup1);
                        tour.setCity(tourPos1, pickup2);

                        tour.setCity(indexDelivery1, delivery2);
                        tour.setCity(indexDelivery2, delivery1);
                    }
                }
            }

        }
        //if(tour.getCity(0).getId() != 0)
        //    tour = AggiustaPartenza(tour);
        //tour = rendiAmmissibile(tour);

        //return tour;
    }

    // Selects candidate tour for crossover
    private static Tour tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.saveTour(i, pop.getTour(randomId));
        }
        // Get the fittest tour
        Tour fittest = tournament.getFittest();
        return fittest;
    }
}