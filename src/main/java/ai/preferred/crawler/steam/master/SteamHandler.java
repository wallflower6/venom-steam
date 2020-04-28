package ai.preferred.crawler.steam.master;

import java.util.ArrayList;
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

        // parser class
        final SteamParser.FinalResult finalResult = SteamParser.parse(response);

        // Crawl another page if there's a next page
        if (finalResult.getNextPage() != null) {
            final String nextPageURL = finalResult.getNextPage();
    
            // Schedule the next page
            scheduler.add(new VRequest(nextPageURL), this);
        }
    }
}