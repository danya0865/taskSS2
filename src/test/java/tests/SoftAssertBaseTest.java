package tests;

import org.testng.asserts.SoftAssert;

public class SoftAssertBaseTest extends BaseTest {
    protected SoftAssert softAssert = new SoftAssert();

    protected void assertAll() {
        softAssert.assertAll();
    }
}