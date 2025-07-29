package com.znaji.urlshortener.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;


public class UrlExistenceValidation {

    private static final Logger log = LoggerFactory.getLogger(UrlExistenceValidation.class);

    public static boolean urlExists(String urlString) {
        log.info("check if url {} actually exists.", urlString);

        try {
            URL url = new URI(urlString).toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            return responseCode >= 200 && responseCode < 400;
        } catch (Exception e) {
            log.error("error while connecting to url {}", urlString, e);
            return false;
        }
    }

    private UrlExistenceValidation(){}
}
