package ai.preferred.crawler.steam.entity;
import java.util.ArrayList;
// import org.apache.commons.lang3.builder.ToStringBuilder;

public class Game {
    private String title;
    private String genre;
    private ArrayList<String> types;
    private double price;
    private int discount;

    public Game (String title, String genre, ArrayList<String> types, double price, int discount) {
        this.title = title;
        this.genre = genre;
        this.types = types;
        this.price = price;
        this.discount = discount;
    }

    public String getTitle() {
        return this.title;
    }

    public String getGenre() {
        return this.genre;
    }

    public ArrayList<String> getTypes() {
        return this.types;
    }

    public double getPrice() {
        return this.price;
    }

    public int getDiscount() {
        return this.discount;
    }

    // @Override
    // public String toString() {
    //   return ToStringBuilder.reflectionToString(this);
    // }
}