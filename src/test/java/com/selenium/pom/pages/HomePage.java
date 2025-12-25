package com.selenium.pom.pages;

import com.selenium.pom.base.BasePage;
import com.selenium.pom.components.MyHeader;
import com.selenium.pom.components.ProductThumbnail;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private MyHeader myHeader;
    private ProductThumbnail productThumbnail;

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public MyHeader getMyHeader() {
        return myHeader;
    }

    public HomePage(WebDriver driver) {
        super(driver);
    myHeader = new MyHeader(driver);
    productThumbnail = new ProductThumbnail(driver);}
    public HomePage load() {
        load("/");
        return this;
    }
}

