package lab6.src.test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class APITestV3 {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String API = "http://52.136.215.164:9000/api/";

    private Integer id = null;

    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    private static Stream<Arguments> createProduct_product_returnsPostResponse() {
        return Stream.of(
                Arguments.of("1", "Dasha Watches", "Dasha Watches Content",
                        "1000", "500", "Dasha,Watches",
                        "Dasha Watches Description", "dasha-watches", "0")
        );
    }

    private static Stream<Arguments> createProduct_productWithOverTopCategoryId_returnsStatusZero() {
        return Stream.of(
                Arguments.of("15", "Pasha Watches", "Pasha Watches Content",
                        "3000", "600", "Pasha,Watches", "Pasha Watches Description")
        );
    }

    private static Stream<Arguments> createEqualsProducts_products_returnsAliasWith0() {
        return Stream.of(
                Arguments.of("1", "Dasha Watches", "Dasha Watches Content",
                        "1000", "500", "Dasha,Watches", "Dasha Watches Description")
        );
    }

    private static Stream<Arguments> createProduct_product_returnsProductInListOfProducts() {
        return Stream.of(
                Arguments.of("1", "Dasha Watches", "Dasha Watches Content",
                        "1000", "500", "Dasha,Watches", "Dasha Watches Description",
                        "dasha-watches", "0")
        );
    }

    private static Stream<Arguments> deleteProduct_productId_returnsDeleteResponse() {
        return Stream.of(
                Arguments.of("1", "Dasha Watches", "Dasha Watches Content",
                        "1000", "500", "Dasha,Watches", "Dasha Watches Description",
                        "dasha-watches", "0")
        );
    }

    private static Stream<Arguments> editProduct_product_returnsPostResponse() {
        return Stream.of(
                Arguments.of("1", "Dasha Watches", "Dasha Watches Content",
                        "1000", "500", "Dasha,Watches", "Dasha Watches Description",
                        "dasha-watches", "0")
        );
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (id != null) {
            deleteProduct(id);
        }
    }

    @Test
    public void getListOfProducts_returnsListOfProducts() throws IOException {
        Product[] products = getProducts();
        assertTrue(products.length != 0,
                "Массив продуктов не должен быть пустым");
    }

    @ParameterizedTest
    @MethodSource
    public void createProduct_product_returnsPostResponse(String categoryId,
                                                          String title,
                                                          String content,
                                                          String price,
                                                          String oldPrice,
                                                          String keywords,
                                                          String description,
                                                          String alias,
                                                          String hit) throws IOException {
        JsonObject payload = getJsonObject(
                categoryId,
                title,
                content,
                price,
                oldPrice,
                keywords,
                description
        );

        var request = new Request.Builder()
                .url(API + "addproduct")
                .post(RequestBody.create(payload.toString(), JSON))
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            var json = gson.fromJson(body.charStream(), PostResponse.class);

            asserts(categoryId,
                    title,
                    content,
                    price,
                    oldPrice,
                    keywords,
                    description,
                    alias,
                    hit);
            assertEquals(
                    1,
                    json.status,
                    "Если продукт создан, то приходит статус, равный 1"
            );

            id = json.id;
        }
    }

    @ParameterizedTest
    @MethodSource
    public void createProduct_productWithOverTopCategoryId_returnsStatusZero(
            String categoryId,
            String title,
            String content,
            String price,
            String oldPrice,
            String keywords,
            String description) throws IOException {
        JsonObject payload = getJsonObject(
                categoryId,
                title,
                content,
                price,
                oldPrice,
                keywords,
                description
        );

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

    @ParameterizedTest
    @MethodSource
    public void createEqualsProducts_products_returnsAliasWith0(String categoryId,
                                                                String title,
                                                                String content,
                                                                String price,
                                                                String oldPrice,
                                                                String keywords,
                                                                String description) throws IOException {
        PostResponse firstProduct = createProduct(
                categoryId,
                title,
                content,
                price,
                oldPrice,
                keywords,
                description);
        PostResponse secondProduct = createProduct(
                categoryId,
                title,
                content,
                price,
                oldPrice,
                keywords,
                description
        );
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

    @ParameterizedTest
    @MethodSource
    public void createProduct_product_returnsProductInListOfProducts(String categoryId,
                                                                     String title,
                                                                     String content,
                                                                     String price,
                                                                     String oldPrice,
                                                                     String keywords,
                                                                     String description,
                                                                     String alias,
                                                                     String hit) throws IOException {
        PostResponse product = createProduct(
                categoryId,
                title,
                content,
                price,
                oldPrice,
                keywords,
                description
        );
        Product[] products = getProducts();
        asserts(categoryId,
                title,
                content,
                price,
                oldPrice,
                keywords,
                description,
                alias,
                hit);
        assertEquals(String.valueOf(product.id), products[products.length - 1].id,
                "Если продукт создан, то он должен быть в списке продуктов");

        id = product.id;
    }

    @ParameterizedTest
    @MethodSource
    public void deleteProduct_productId_returnsDeleteResponse(String categoryId,
                                                              String title,
                                                              String content,
                                                              String price,
                                                              String oldPrice,
                                                              String keywords,
                                                              String description) throws IOException {
        PostResponse postResponse = createProduct(
                categoryId,
                title,
                content,
                price,
                oldPrice,
                keywords,
                description
        );
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

    @ParameterizedTest
    @MethodSource
    public void editProduct_product_returnsPostResponse(String categoryId,
                                                        String title,
                                                        String content,
                                                        String price,
                                                        String oldPrice,
                                                        String keywords,
                                                        String description,
                                                        String alias,
                                                        String hit) throws IOException {

        PostResponse postResponse = createProduct("1", "Masha Watches", "Masha Watches Content",
                "1000", "500", "Masha,Watches",
                "Masha Watches Description");

        JsonObject payload = getJsonObject(
                categoryId,
                title,
                content,
                price,
                oldPrice,
                keywords,
                description
        );
        payload.addProperty("id", String.valueOf(postResponse.id));

        var request = new Request.Builder()
                .url(API + "editproduct")
                .post(RequestBody.create(payload.toString(), JSON))
                .build();

        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            assert body != null;
            var json = gson.fromJson(body.charStream(), PostResponse.class);

            asserts(categoryId,
                    title,
                    content,
                    price,
                    oldPrice,
                    keywords,
                    description,
                    alias,
                    hit);
            assertEquals(1, json.status,
                    "Если продукт создан, то приходит статус, равный 1");
        }
        id = postResponse.id;
    }

    @org.junit.jupiter.api.Test
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

    private PostResponse createProduct(String categoryId,
                                       String title,
                                       String content,
                                       String price,
                                       String oldPrice,
                                       String keywords,
                                       String description) throws IOException {
        JsonObject payload = getJsonObject(
                categoryId,
                title,
                content,
                price,
                oldPrice,
                keywords,
                description);

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

    private JsonObject getJsonObject(String categoryId,
                                     String title,
                                     String content,
                                     String price,
                                     String oldPrice,
                                     String keywords,
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

    private static class PostResponse {
        int id;
        int status;
    }

    private static class DeleteResponse {
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
