package ai.preferred.crawler.steam.entity;
import java.util.ArrayList;
// import org.apache.commons.lang3.builder.ToStringBuilder;

public class Game {
    private String title;
    private String genre;
    private String url;
    private ArrayList<String> types;
    private String price;
    private String discount;

    public Game (String title, String genre, String url, ArrayList<String> types, String price, String discount) {
        this.title = title;
        this.genre = genre;
        this.url = url;
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

    public String getURL() {
        return this.url;
    }

    public ArrayList<String> getTypes() {
        return this.types;
    }

    public String getPrice() {
        return this.price;
    }

    public String getDiscount() {
        return this.discount;
    }

    // @Override
    // public String toString() {
    //   return ToStringBuilder.reflectionToString(this);
    // }
}