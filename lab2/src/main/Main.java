package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static int oks = 0;
    private static int errors = 0;
    private static final String URL = "http://52.136.215.164";
    private static final Set<String> urls = new HashSet<>();

    public static void main(String[] args) throws IOException {
        getContentPage(URL + "/broken-links/");

        System.out.printf("%n%d%n%s%n", oks, new Date().toString());
        System.err.printf("%n%d%n%s%n", errors, new Date().toString());
    }

    private static void getContentPage(String url) throws IOException {
        HttpURLConnection http = (HttpURLConnection) new URL(url).openConnection();
        int statusCode = http.getResponseCode();

        try {
            StringBuilder sb = new StringBuilder();
            URLConnection connection = new URL(url).openConnection();

            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            char[] buffer = new char[256];
            int rc;

            while ((rc = reader.read(buffer)) != -1)
                sb.append(buffer, 0, rc);

            reader.close();

            oks++;

            System.out.printf("%s %d%n", url, statusCode);

            getLinks(sb.toString());
        } catch (Exception e) {
            errors++;

            System.err.printf("%s %d%n", url, statusCode);
        }
    }

    private static void getLinks(String content) throws IOException {
        Pattern pattern = Pattern.compile("<a.*href=\"(.*?)\".*>");
        Matcher matcher = pattern.matcher(content);

        int index = 0;
        while (matcher.find(index)) {
            index = matcher.end();

            String link = matcher.group(1);
            String url = (URL + "/broken-links/" + link);

            if (urls.contains(url) || urls.contains(link)) {
                continue;
            }

            urls.add(url);
            urls.add(link);

            if (link.startsWith("http")) {
                if (!link.contains(URL)) {
                    continue;
                }
                getContentPage(link);
            } else {
                getContentPage(url);
            }
        }
    }
}
