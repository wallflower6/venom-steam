package ai.preferred.crawler.steam.master;

import org.jsoup.nodes.Document;
import org.json.*;
import java.util.ArrayList;
import ai.preferred.crawler.steam.entity.Game;

public class SteamJSONParser {
    public static ArrayList<String> listTypes(String types_string) {
        ArrayList<String> types = new ArrayList<>();
        for (String type : types_string.split("</span>")) {
            types.add(type.trim());
        }
        return types;
    }

    public static void parse(Document document) {
        String data = document.select("body").first().text();
        JSONObject json = new JSONObject(data);
        String results = json.getString("results_html");
        results = results.replaceAll("[\r,\n,\t]", "");
        // discount, original price, discounted price, title, genre, type, type, type

        String[] resultsSplit = results.split("</div>");
        ArrayList<String> game_details = new ArrayList<>();
        for (String result : resultsSplit) {
            game_details.add(result);
        }

        ArrayList<Game> games = new ArrayList<>();
        for (int i = 1; i < game_details.size();) {
            String discount = "";
            String o_price = "";
            String d_price = "";
            String types = "";
            String title = "";

            while (!game_details.get(i).contains("</a>")) {
                if (game_details.get(i).contains("%")) {
                    discount = game_details.get(i);
                    i++;
                } else if (game_details.get(i).contains("S$")) {
                    if (game_details.get(i+1).contains("S$")) {
                        o_price = game_details.get(i);
                        d_price = game_details.get(i + 1);
                        i += 2;
                    } else {
                        o_price = game_details.get(i);
                        i++;
                    }
                } else if (game_details.get(i).contains("</span")) {
                    types = game_details.get(i);
                    i++;
                } else if (!game_details.get(i).equals(" ") && !game_details.get(i).equals("")) {
                    title = game_details.get(i);
                    i++;
                } else {
                    i++;
                }
            }
            
            games.add(new Game(title.trim(), "Action", "", listTypes(types), (discount.contains("%") ? d_price.trim() : o_price.trim()), discount));
            i++;
        }

        for (Game game : games) {
            System.out.println(game);
        }
    }
}