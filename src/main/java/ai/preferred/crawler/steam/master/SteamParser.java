package ai.preferred.crawler.steam.master;

import ai.preferred.crawler.steam.entity.Game;
import ai.preferred.venom.response.VResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SteamParser {
    private SteamParser() {
        throw new UnsupportedOperationException();
    }

    static List<Game> parseListing(Document document) {
        final ArrayList<Game> gameList = new ArrayList<>();

        // extract genre
        String[] genreHeader = document.select("div.content_hub > h2.pageheader").first().text().split(" ");
        String genre = genreHeader[1];
        if (genre.equals("Massively")) {
            genre = genre + " " + genreHeader[2];
        }

        // extract links
        Elements links = document.select("#NewReleasesRows > a");
        for (Element link : links) {
            String gameURL = link.attr("abs:href");

            // extract title
            String title = link.select("div.tab_item_content > div.tab_item_name").first().text();

            // extract final price
            // with discount: discount_block tab_item_discount > discount_prices > discount_original_price > discount_final_price
            // w/o discount: discount_block tab_item_discount no_discount > discount_prices > discount_final_price
            String finalPrice = link.select("div.tab_item_discount > div > div.discount_final_price").first().text();

            // extract types
            ArrayList<String> types = new ArrayList<>();
            Elements top_tags = link.select("div.tab_item_content > div.tab_item_details > div");
            for (Element top_tag : top_tags) {
                types.add(top_tag.text());
            }

            // extract discount
            String discount = "0";
            try {
                discount = link.select("div.tab_item_discount > div.discount_pct").first().text();
            } catch (NullPointerException e) {
            }

            gameList.add(new Game(title, genre, gameURL, types, finalPrice, discount));
        }

        return gameList;
    }

    public static GenreResult parseGenre(Document document) {
        return new GenreResult(parseListing(document));
    }

    public static class GenreResult {
        private final List<Game> games;

        private GenreResult(List<Game> games) {
            this.games = games;
        }

        public List<Game> getGames() {
            return games;
        }
    }
}