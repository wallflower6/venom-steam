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

    public static FinalResult parse(VResponse response) {
        final Document document = response.getJsoup();
        return new FinalResult(
            parseListing(document),
            parseNextPage(document)
        );
    }

    static List<Game> parseListing(Document document) {
        final ArrayList<Game> gameList = new ArrayList<>();

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

            gameList.add(new Game(title, "Action", gameURL, types, finalPrice, discount));
        }
        System.out.println(gameList);
        return gameList;
    }

    private static String parseNextPage(Document document) {
        final Element nextPage = document.select("a.prev-next.test-pagination-next").first();
        if (nextPage == null) {
          return null;
        }
        return nextPage.attr("abs:href");
    }

    public static class FinalResult {

        private final List<Game> games;
    
        private final String nextPage;
    
        private FinalResult(List<Game> games, String nextPage) {
          this.games = games;
          this.nextPage = nextPage;
        }
    
        public List<Game> getGames() {
          return games;
        }
    
        public String getNextPage() {
          return nextPage;
        }
    }
}