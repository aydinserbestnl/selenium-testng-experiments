package com.selenium.pom.steps;

import com.selenium.pom.pages.CartPage;
import com.selenium.pom.pages.StorePage;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;

import static java.sql.DriverManager.getDriver;

public class CartSteps {
    public StorePage loadStorePage(WebDriver driver) {
        return Allure.step("Load Store page", () -> new StorePage(driver).load());
    }

    public void addProductToCart(StorePage storePage, String productName) {
        Allure.step("Add product to cart: " + productName, () -> {
            storePage.getProductThumbnail().clickAddToCartFor(productName);
        });
    }

    public CartPage openCart(StorePage storePage) {
        return Allure.step("Open cart (View cart)", () ->
                storePage.getProductThumbnail().clickViewCart()
        );
    }
}
