package ai.preferred.crawler.steam.master;

import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;
import ai.preferred.crawler.steam.entity.Game;
import ai.preferred.venom.Crawler;
import ai.preferred.venom.fetcher.*;
import ai.preferred.venom.validator.*;
import ai.preferred.venom.Session;
import ai.preferred.venom.request.VRequest;

public class SteamCrawler {
    // to log to console
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SteamCrawler.class);

    // checks queue, tells fetcher what to crawl, where should response be processed
    public static Crawler createCrawler() {
        final Crawler crawler = Crawler.builder().build();
        return crawler;
    }

    // fetch a page given a request (URL)
    public static Fetcher createFetcher() {
        final Fetcher fetcher = AsyncFetcher.builder().setValidator(new EmptyContentValidator(), new StatusOkValidator(), new SteamValidator()).build();
        return fetcher;
    }

    // Create session keys for things you would like to retrieve in/from handler
    static final Session.Key<List<Game>> GAME_LIST_KEY = new Session.Key<>();

    // session store: exchange info/objects between main method and handler
    public static Session createSession(List<Game> games) {
        final Session session = Session.builder().put(GAME_LIST_KEY, games).build();
        return session;
    }

    // crawler with specified fetcher and session
    public static Crawler createCrawler(Fetcher fetcher, Session session) {
        final Crawler crawler = Crawler.builder().setFetcher(fetcher).setSession(session).build();
        return crawler;
    }

    public static void main(String[] args) throws Exception {
        final List<Game> games = new ArrayList<>();

        final String url = "https://store.steampowered.com/";
        final Fetcher fetcher = createFetcher();
        Session session = createSession(games);

        try (Crawler crawler = createCrawler(fetcher, session).start()) {
            LOGGER.info("Starting crawler...");
            crawler.getScheduler().add(new VRequest(url), new SteamHandler());
        } 
        catch (Exception e) {
            LOGGER.error("Could not run crawler: ", e);
        }
    }
}