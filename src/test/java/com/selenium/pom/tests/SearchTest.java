package com.selenium.pom.tests;

import com.selenium.pom.base.BaseTest;
import com.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {
    @Test
    public void searchWithPartialMatch() {
        String searchFor = "Blue";
        StorePage storePage = new StorePage(getDriver()).
                load().
                search(searchFor);
        Assert.assertEquals(
                storePage.getResultsHeadingText(), "Search results: “"+searchFor+"”");
    }
}
