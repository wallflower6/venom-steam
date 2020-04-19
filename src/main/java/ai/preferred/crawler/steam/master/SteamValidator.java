package ai.preferred.crawler.steam.master;

import org.slf4j.LoggerFactory;
import ai.preferred.venom.request.Request;
import ai.preferred.venom.response.Response;
import ai.preferred.venom.response.VResponse;
import ai.preferred.venom.validator.Validator;

public class SteamValidator implements Validator {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SteamValidator.class);

    @Override
    public Status isValid(Request request, Response response) {
        final VResponse vResponse = new VResponse(response);

        if (vResponse.getHtml().contains("Steam")) {
        return Status.VALID;
        }

        return Status.INVALID_CONTENT;
    }
}