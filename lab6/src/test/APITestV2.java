package lab6.src.test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class APITestV2 {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String API = "http://52.136.215.164:9000/api/";

    @Parameter
    public String categoryId;
    @Parameter(1)
    public String title;
    @Parameter(2)
    public String content;
    @Parameter(3)
    public String price;
    @Parameter(4)
    public String oldPrice;
    @Parameter(5)
    public String keywords;
    @Parameter(6)
    public String description;

    private Integer id = null;

    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1", "title", "content", "1000", "500", "keywords", "description"},
        });
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (id != null) {
            deleteProduct(id);
        }
    }

    @Test
    public void test() throws IOException {
        JsonObject payload = getJsonObject(categoryId, title, content, price, oldPrice, keywords, description);

        var request = new Request.Builder()
                .url(API + "addproduct")
                .post(RequestBody.create(payload.toString(), JSON))
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            var json = gson.fromJson(body.charStream(), PostResponse.class);

            assertEquals(1, json.status, "Если продукт создан, то приходит статус, равный 1");

            id = json.id;
        }
    }

    private DeleteResponse deleteProduct(int id) throws IOException {
        var request = new Request.Builder()
                .url(API + "deleteproduct?id=" + id)
                .build();


        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            return gson.fromJson(body.charStream(), DeleteResponse.class);
        }
    }

    private JsonObject getJsonObject(String categoryId, String title, String content,
                                     String price, String oldPrice, String keywords,
                                     String description) {
        var payload = new JsonObject();
        payload.addProperty("category_id", categoryId);
        payload.addProperty("title", title);
        payload.addProperty("content", content);
        payload.addProperty("price", price);
        payload.addProperty("old_price", oldPrice);
        payload.addProperty("keywords", keywords);
        payload.addProperty("description", description);
        return payload;
    }

    private static class PostResponse {
        int id;
        int status;
    }

    private static class DeleteResponse {
        int status;
    }

}
