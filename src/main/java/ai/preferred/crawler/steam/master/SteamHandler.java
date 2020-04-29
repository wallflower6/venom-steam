package ai.preferred.crawler.steam.master;

import java.util.ArrayList;
import java.util.List;
import ai.preferred.crawler.steam.entity.Game;
import ai.preferred.venom.Handler;
import ai.preferred.venom.Session;
import ai.preferred.venom.Worker;
import ai.preferred.venom.job.Scheduler;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.request.VRequest;
import ai.preferred.venom.response.VResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.*;
import java.io.IOException;
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

        // intermediate_result = parseIntermediary
        // finalResult = parseFinal(arg intermediate_result.getWhatever())

        Elements genres = document.select("#genre_flyout > div.popup_body > div.popup_menu > a[href~=/tags/]");
        for (Element genre : genres) {
            String genreURL = genre.attr("abs:href");
            Document genrePage = null;
            try {
                genrePage = Jsoup.connect(genreURL).get();
            } catch (IOException e) {
                System.out.println("Page not found");
            }

            SteamParser.GenreResult genreResult = SteamParser.parseGenre(genrePage);
            System.out.println("-----------");
            List<Game> games = genreResult.getGames();
            for (Game game : games) {
                System.out.println(game.getTitle());
            }
            System.out.println("-----------");
        }

        // // parser class
        // final SteamParser.FinalResult finalResult = SteamParser.parse(response);

        // // Crawl another page if there's a next page
        // if (finalResult.getNextPage() != null) {
        //     final String nextPageURL = finalResult.getNextPage();
    
        //     // Schedule the next page
        //     scheduler.add(new VRequest(nextPageURL), this);
        // }
    }
}