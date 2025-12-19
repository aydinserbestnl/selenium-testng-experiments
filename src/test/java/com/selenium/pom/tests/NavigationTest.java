package com.selenium.pom.tests;

import com.selenium.pom.base.BaseTest;
import com.selenium.pom.pages.HomePage;
import com.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {
    @Test
    public void navigationFromHomeToStoreMenuUsingMainMenu() {
        StorePage storePage = new HomePage(getDriver()).
                load()
                .navigateToStoreUsingMenu();
        Assert.assertEquals(
                storePage.getResultsHeadingText(), "Store");
    }
}
