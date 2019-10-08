package main;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String URL = "http://52.136.215.164";
    private static final Set<String> urls = new HashSet<>();

    public static void main(String[] args) {
        getContentPage(URL + "/broken-links/");
    }

    private static void getContentPage(String url) {
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

            System.out.printf("%s %b%n", url, true);

            getLinks(sb.toString());
        } catch (Exception e) {
            System.out.printf("%s %b%n", url, false);
        }
    }

    private static void getLinks(String content) {
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
