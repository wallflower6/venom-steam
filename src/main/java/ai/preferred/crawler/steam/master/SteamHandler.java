package ai.preferred.crawler.steam.master;

import java.util.ArrayList;
import ai.preferred.venom.Handler;
import ai.preferred.venom.Session;
import ai.preferred.venom.Worker;
import ai.preferred.venom.job.Scheduler;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.response.VResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SteamHandler implements Handler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SteamHandler.class);

    // // Log when there's activity
    // LOGGER.info("Processing {}", request.getUrl());

    @Override
    public void handle(Request request, VResponse response, Scheduler scheduler, Session session, Worker worker) {
        final String html = response.getHtml();
        final Document document = response.getJsoup();

        // extract links
        Elements links = document.select("#NewReleasesRows > a");
        for (Element link : links) {
            System.out.println(link.attr("abs:href"));

            // extract title
            String title = link.select("div.tab_item_content > div.tab_item_name").first().text();
            System.out.println(title);

            // extract final price
            // with discount: discount_block tab_item_discount > discount_prices > discount_original_price > discount_final_price
            // w/o discount: discount_block tab_item_discount no_discount > discount_prices > discount_final_price
            String finalPrice = link.select("div.tab_item_discount > div > div.discount_final_price").first().text();
            System.out.println(finalPrice);

            // extract types
            ArrayList<String> types = new ArrayList<>();
            Elements top_tags = link.select("div.tab_item_content > div.tab_item_details > div");
            for (Element top_tag : top_tags) {
                types.add(top_tag.text());
            }
            System.out.println(types);

            // extract discount
            try {
                String discount = link.select("div.tab_item_discount > div.discount_pct").first().text();
                System.out.println(discount);
            } catch (NullPointerException e) {
                System.out.println(0);
            }

            System.out.println();
        }
    }
}