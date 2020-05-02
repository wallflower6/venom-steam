package ai.preferred.crawler.steam.master;

import org.jsoup.nodes.Document;
import org.json.*;

public class SteamJSONParser {
    public static void parse(Document document) {
        // assuming document is obtained from query link

        String data = document.select("body").first().text();
        JSONObject json = new JSONObject(data);
        String results = json.getString("results_html");
        results = results.replaceAll("[\r,\n,\t]", "");
        // System.out.println(results);
        // discount, original price, discounted price, title, genre, type, type, type

        String[] resultsSplit = results.split("</div>");
    }
}