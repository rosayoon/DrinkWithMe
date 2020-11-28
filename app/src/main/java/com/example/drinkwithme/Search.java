package com.example.drinkwithme;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Search {
    private Map<String, String> resultMap;

    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final JsonFactory JSON_FACTORY = new JacksonFactory();
    private final long NUMBER_OF_VIDEOS_PERTURNED = 25;
    private YouTube youtube;
    private ArrayList<SearchResult> searchResultList;

    public Search() {
        resultMap = new HashMap<String, String>();
    }

    public void searchKeyword() {

        try {
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {

                public void initialize(HttpRequest arg0) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search-sample").build();

            String queryTerm = getInputQuery();

            YouTube.Search.List search = youtube.search().list("id,snippet");

            // API KEY
            search.setKey("AIzaSyCUriG-LgMYQ4nv2eArlYuIA-jQHvTd3NA");
            search.setQ(queryTerm);

            /*
             * 비디오만 검색하도록 한다.
             */
            search.setType("video");

            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_PERTURNED);
            SearchListResponse searchResponse = search.execute();

            searchResultList = (ArrayList<SearchResult>) searchResponse.getItems();

            if (searchResultList != null) {
                prettyPrint(searchResultList.iterator(), queryTerm);
            }

        } catch (GoogleJsonResponseException e) {
            System.err.println(
                    "There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private String getInputQuery() throws IOException {

        String inputQuery = "";
        System.out.println("Please enter a search term : ");
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        inputQuery = bReader.readLine();

        if (inputQuery.length() < 1) {

            inputQuery = "Youtube Developers Live";
        }

        return inputQuery;
    }

    private void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {
        System.out.println("\n====================================================================");
        System.out.println("   First " + NUMBER_OF_VIDEOS_PERTURNED + " videos for search on \"" + query + "\".");
        System.out.println("====================================================================\n");

        int count = 1;
        while (iteratorSearchResults.hasNext()) {
            System.out.println("=======================" + count + "=======================");

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

                System.out.println(" Video Id  :" + rId.getVideoId());
                System.out.println(" Title     : " + singleVideo.getSnippet().getTitle());

                System.out.println(" Thumbnail : " + thumbnail.getUrl());
                System.out.println("====================================================================\n");

                resultMap.put(singleVideo.getSnippet().getTitle(), rId.getVideoId());

                count += 1;
            }
        }
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }

    public void initResutMap() {
        this.resultMap.clear();
    }

    public String getVideoKey(int key) {
        Iterator<String> iterator = resultMap.keySet().iterator();
        String data = null;
        for (int i = 0; i < key; ++i) {
            data = (String) iterator.next();
        }

        //https://www.youtube.com/watch?v=_rI-E9TnCQA
        //result.put(data, "https://www.youtube.com/watch?v=_"+resultMap.get(data));

        return data;
    }
}

