package by.grsu.artproject2.model;

/**
 * Created by Алина on 13.12.2017.
 */

public class Genre {
    private int id;
    private String name;

    public Genre(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
