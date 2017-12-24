package by.grsu.artproject2.model;

/**
 * Created by Алина on 13.12.2017.
 */

public class Artist {
    private int id;
    private String name;
    private String information;

    public Artist(int id, String name, String information){
        this.id = id;
        this.name = name;
        this.information=information;
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
    public void setInformation(String information) {
        this.information = information;
    }
    public String getInformation() {
        return information;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
