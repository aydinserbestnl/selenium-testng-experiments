package com.selenium.pom.dataProviders;

import com.selenium.pom.objects.Product;
import com.selenium.pom.utils.JacksonUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class MyDataProvider {
    @DataProvider(name = "getFeaturedProducts", parallel = false)
    public Product[] getFeaturedProducts() throws IOException {
        return JacksonUtils.deserializeJson("products.json", Product[].class);
    }
}
