package com.selenium.pom.api.actions;

import com.selenium.pom.objects.User;
import com.selenium.pom.utils.FakerUtils;

public class DummyTest {
    public static void main(String[] args) {
        //we are first login and then add to cart
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User().
                setUserName(username).
                setPassword("2134").
                setEmail(username + "@askomdch.com");
        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        System.out.println("REGISTER COOKIES: " + signUpApi.getCookies());
        CartApi cartApi = new CartApi(signUpApi.getCookies());
        cartApi.addToCart(1215, 1);
        System.out.println("CART COOKIES: " + cartApi.getCookies());


        //without login,as anonym user, we are adding to cart
        /*
        CartApi cartApi = new CartApi();
        Response resp = cartApi.addToCart(1215, 1);

        // JSON’u parse et, fragments map’ini al
        JsonPath jp = new JsonPath(resp.asString());
        Map<String, String> fragments = jp.getMap("fragments");

        // Mini-cart HTML’i: item bilgisi HTML içinde gömülü
        // JSON’un içindeki asıl item bilgisi HTML olarak gömülü geliyor, bu yüzden parse ediyoruz
        String cartHtml = fragments.get("div.widget_shopping_cart_content");
        if (cartHtml == null) throw new IllegalStateException("cart_html yok: " + resp.asString());

        // HTML’i parse edip data-product_id taşıyan anchor’ı bul
        Document doc = Jsoup.parse(cartHtml);
        Element productLink = doc.selectFirst("a[data-product_id]");
        if (productLink == null) {
            throw new IllegalStateException("data-product_id içeren anchor bulunamadı: " + cartHtml);
        }

        // Kontroller (id ve adet)
        assertEquals(productLink.attr("data-product_id"), "1215");
        assertTrue(doc.text().contains("1 ×"));

         */
    }
}
