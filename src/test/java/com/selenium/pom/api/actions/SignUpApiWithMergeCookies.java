package com.selenium.pom.api.actions;

import com.selenium.pom.objects.User;
import com.selenium.pom.utils.ConfigLoader;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class SignUpApiWithMergeCookies {
    private Cookies cookies = new Cookies(); // tek jar
    public Cookies getCookies() { return cookies; }

    private Cookies merge(Cookies a, Cookies b) {
        Map<String, io.restassured.http.Cookie> map = new HashMap<>();
        if (a != null) a.asList().forEach(c -> map.put(c.getName(), c));
        if (b != null) b.asList().forEach(c -> map.put(c.getName(), c));
        return new Cookies(map.values().toArray(new io.restassured.http.Cookie[0]));
    }

    private String fetchRegisterNonceValueUsingJsoup() {
        Response response = getAccount();
        Document doc = Jsoup.parse(response.getBody().asString());
        Element element = doc.selectFirst("#woocommerce-register-nonce");
        if (element == null) {
            throw new IllegalStateException("nonce yok");
        }
        return element.attr("value");
    }

    public Response getAccount() {
        Response response = given()
                .baseUri(ConfigLoader.getInstance().getBaseUrl())
                .cookies(cookies) // mevcut jar ile git
                .get("/account");
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to fetch /account, code: " + response.getStatusCode());
        }
        cookies = merge(cookies, response.getDetailedCookies()); // jar’ı güncelle
        return response;
    }

    public Response register(User user) {
        Header h = new Header("Content-Type", "application/x-www-form-urlencoded");
        Headers headers = new Headers(h);
        Map<String, String> formParams = new HashMap<>();
        formParams.put("username", user.getUserName());
        formParams.put("password", user.getPassword());
        formParams.put("email", user.getEmail());
        formParams.put("woocommerce-register-nonce", fetchRegisterNonceValueUsingJsoup());
        formParams.put("register", "Register");

        Response response = given()
                .baseUri(ConfigLoader.getInstance().getBaseUrl())
                .headers(headers)
                .formParams(formParams)
                .cookies(cookies) // aynı jar
                .post("/account");
        if (response.getStatusCode() != 302) {
            throw new RuntimeException("Failed to register, code: " + response.getStatusCode());
        }
        cookies = merge(cookies, response.getDetailedCookies()); // login çerezleri eklendi
        return response;
    }
}
