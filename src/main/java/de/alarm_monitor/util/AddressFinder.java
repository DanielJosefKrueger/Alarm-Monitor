package de.alarm_monitor.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.processing.FaxProzessorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;


public class AddressFinder {


    private final static String api_key = "AIzaSyAx-W1Y0Mv3l0liFCNZhavr0-vPKwU0Scc";
    private static final Logger logger = LogManager.getLogger(FaxProzessorImpl.class);

    private final MainConfiguration mainConfiguration;

    @Inject
    AddressFinder(final Provider<MainConfiguration> provider) {
        this.mainConfiguration = provider.get();
    }


    private static LatLng getCordsAdress(final String address) {

        final StringBuilder adressbuilder = new StringBuilder();
        Arrays.stream(address.split("\n"))
                .filter(e -> e.startsWith("Str") || e.startsWith("Ort"))
                .forEach(e -> adressbuilder.append(e.replaceAll(".tra.e", "")
                        .replaceAll("Ort", "")
                        .replaceAll("Haus-Nr", "")
                        .replaceAll("\\.", "")
                        .replaceAll("=", "")
                        .replaceAll(":", "")
                ));

        final GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(api_key)
                .build();

        logger.trace("Fetching the kords from Google");
        logger.trace("Fetch-String is:" + adressbuilder);
        final GeocodingResult[] results = GeocodingApi.geocode(context, adressbuilder.toString()).awaitIgnoreError();
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        logger.trace("Received following coordinates from Google" + gson.toJson(results[0].geometry.location));
        return results[0].geometry.location;

    }

    //48.39979700000001, 12.7468121
    //https://www.google.de/maps/dir/Freiwillige+Feuerwehr+Markt+Gangkofen,+Jahnstraße,+Gangkofen/48.45397,12.54189
    //https://www.google.de/maps/place/48.39979700000001, 12.7468121

    private String createLinkFromCoordinates(final LatLng kords) {
        final String begin = mainConfiguration.getRoutingLinkBegin();

        return begin + kords.lat + "," + kords.lng;
    }

    public String createLink(final String address) {
        try {
            final LatLng kords = getCordsAdress(address);
            return createLinkFromCoordinates(kords);

        } catch (final Exception e) {
            if (address.lastIndexOf(" ") > -1) {
                logger.info("Couldnt get link from google, retry");
                return createLink(address.substring(0, address.lastIndexOf(" ")));
            } else {
                return null;
            }
        }
    }

}
