package com.selenium.pom.api.actions;

import com.selenium.pom.utils.ConfigLoader;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CartApiWithMergeCookies {
    private Cookies cookies;

    public CartApiWithMergeCookies() {}
    public CartApiWithMergeCookies(Cookies cookies) { this.cookies = cookies; }
    public Cookies getCookies() { return cookies; }

    private Cookies merge(Cookies a, Cookies b) {
        Map<String, io.restassured.http.Cookie> map = new HashMap<>();
        if (a != null) a.asList().forEach(c -> map.put(c.getName(), c));
        if (b != null) b.asList().forEach(c -> map.put(c.getName(), c));
        return new Cookies(map.values().toArray(new io.restassured.http.Cookie[0]));
    }

    public Response addToCart(int productId, int quantity) {
        Header h = new Header("Content-Type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(h);
        Map<String, Object> formParams = Map.of(
                "product_sku", "",
                "product_id", productId,
                "quantity", quantity
        );

        Response response = given()
                .baseUri(ConfigLoader.getInstance().getBaseUrl())
                .headers(headers)
                .formParams(formParams)
                .cookies(cookies != null ? cookies : new Cookies()) // dışarıdan gelen jar’ı kullan
                .post("/?wc-ajax=add_to_cart")
                .then().extract().response();

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("add_to_cart failed, code: " + response.getStatusCode());
        }

        cookies = merge(cookies, response.getDetailedCookies()); // sepet çerezleri eklendi
        return response;
    }
}
