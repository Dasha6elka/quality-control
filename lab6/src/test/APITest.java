package lab6.src.test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class APITest {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String API = "http://52.136.215.164:9000/api/";
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    @NotNull
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

    private void asserts(String categoryId, String title, String content,
                         String price, String oldPrice, String keywords,
                         String description, String alias, String hit) throws IOException {
        Product[] products = getProducts();
        int size = products.length - 1;

        assertEquals(categoryId, products[size].category_id);
        assertEquals(title, products[size].title);
        assertEquals(content, products[size].content);
        assertEquals(price, products[size].price);
        assertEquals(oldPrice, products[size].old_price);
        assertEquals(keywords, products[size].keywords);
        assertEquals(description, products[size].description);
        assertEquals(alias, products[size].alias);
        assertEquals(hit, products[size].hit);
    }

    private PostResponse createProduct() throws IOException {
        JsonObject payload = getJsonObject("1", "Dasha Watches", "Dasha Watches Content",
                "1000", "500", "Dasha,Watches", "Dasha Watches Description");

        var request = new Request.Builder()
                .url(API + "addproduct")
                .post(RequestBody.create(payload.toString(), JSON))
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            return gson.fromJson(body.charStream(), PostResponse.class);
        }
    }

    private Product[] getProducts() throws IOException {
        var request = new Request.Builder()
                .url(API + "products")
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            return gson.fromJson(body.charStream(), Product[].class);
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

    @Test
    public void getListOfProducts_returnsListOfProducts() throws IOException {
        Product[] products = getProducts();
        assertTrue(products.length != 0,
                "Массив продуктов не должен быть пустым");
    }

    @Test
    public void createProduct_product_returnsPostResponse() throws IOException {
        PostResponse product = createProduct();

        asserts("1", "Dasha Watches", "Dasha Watches Content",
                "1000", "500", "Dasha,Watches",
                "Dasha Watches Description", "dasha-watches", "0");
        assertEquals(1, product.status,
                "Если продукт создан, то приходит статус, равный 1");

        deleteProduct(product.id);
    }

    @Test
    public void createProduct_productWithOverTopCategoryId_returnsStatusZero() throws IOException {
        JsonObject payload = getJsonObject("15", "Pasha Watches", "Pasha Watches Content",
                "3000", "600", "Pasha,Watches", "Pasha Watches Description");

        var request = new Request.Builder()
                .url(API + "editproduct")
                .post(RequestBody.create(payload.toString(), JSON))
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            var json = gson.fromJson(body.charStream(), PostResponse.class);

            assertEquals(0, json.status,
                    "Продукт не может создаться с превышающей category_id, " +
                            "поэтому должен вернуться статус, равный нулю");
        }
    }

    @Test
    public void createEqualsProducts_products_returnsAliasWith0() throws IOException {
        PostResponse firstProduct = createProduct();
        PostResponse secondProduct = createProduct();
        Product[] products = getProducts();

        assertEquals("dasha-watches",
                products[products.length - 2].alias);
        assertEquals("dasha-watches-0",
                products[products.length - 1].alias,
                "Если создано два продукта с одинаковыми title, " +
                        "то alias у второго будет иметь вконце -0");

        deleteProduct(firstProduct.id);
        deleteProduct(secondProduct.id);
    }

    @Test
    public void createProduct_product_returnsProductInListOfProducts() throws IOException {
        PostResponse product = createProduct();
        Product[] products = getProducts();
        assertEquals(String.valueOf(product.id), products[products.length - 1].id,
                "Если продукт создан, то он должен быть в списке продуктов");

        deleteProduct(product.id);
    }

    @Test
    public void deleteProduct_productId_returnsDeleteResponse() throws IOException {
        PostResponse postResponse = createProduct();
        DeleteResponse deleteResponse = deleteProduct(postResponse.id);

        assertEquals(1, deleteResponse.status,
                "Если продукт удалился, то должен прийти статус, равный 1");
    }

    @Test
    public void deleteProduct_notProductId_returnsNotDeletedProduct() throws IOException {
        var request = new Request.Builder()
                .url(API + "deleteproduct?id=")
                .build();


        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            var json = gson.fromJson(body.charStream(), DeleteResponse.class);
            assertEquals(0, json.status,
                    "Продукт не может удалиться без productId");
        }
    }

    @Test
    public void editProduct_product_returnsPostResponse() throws IOException {
        PostResponse postResponse = createProduct();

        JsonObject payload = getJsonObject("1", "Masha Watches", "Masha Watches Content",
                "1000", "500", "Masha,Watches", "Masha Watches Description");
        payload.addProperty("id", String.valueOf(postResponse.id));

        var request = new Request.Builder()
                .url(API + "editproduct")
                .post(RequestBody.create(payload.toString(), JSON))
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            var json = gson.fromJson(body.charStream(), PostResponse.class);

            asserts("1", "Masha Watches", "Masha Watches Content",
                    "1000", "500", "Masha,Watches",
                    "Masha Watches Description", "masha-watches", "0");
            assertEquals(1, json.status,
                    "Если продукт создан, то приходит статус, равный 1");
        }

        deleteProduct(postResponse.id);
    }

    @Test
    public void editProduct_productWithoutId_returnsNotEditProduct() throws IOException {
        var payload = new JsonObject();
        payload.addProperty("category_id", "3");
        payload.addProperty("title", "Misha Watches");

        var request = new Request.Builder()
                .url(API + "editproduct")
                .post(RequestBody.create(payload.toString(), JSON))
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            var json = gson.fromJson(body.charStream(), PostResponse.class);

            assertEquals(0, json.status,
                    "Продукт не будет редактироваться без productId");
        }
    }

    private static class DeleteResponse {
        int status;
    }

    private static class PostResponse {
        int id;
        int status;
    }

    private static class Product {
        String id;
        String category_id;
        String title;
        String alias;
        String content;
        String price;
        String old_price;
        String status;
        String keywords;
        String description;
        String hit;
    }
}
