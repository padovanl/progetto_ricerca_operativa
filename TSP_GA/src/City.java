/*
* City.java
* Models a city
*/

public class City {
    int x;
    int y;
    int id;

    // Constructs a randomly placed city
    public City(){
        this.x = (int)(Math.random()*200);
        this.y = (int)(Math.random()*200);
    }

    // Constructs a city at chosen x, y location
    public City(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }


    // Gets city's x coordinate
    public int getX(){
        return this.x;
    }

    // Gets city's y coordinate
    public int getY(){
        return this.y;
    }

    // Gets the distance to given city
    public double distanceTo(City city){
        double xDistance = Math.abs(getX() - city.getX());
        double yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );

        return distance;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        //return getX()+", "+getY();
        return String.valueOf(id);
    }
}