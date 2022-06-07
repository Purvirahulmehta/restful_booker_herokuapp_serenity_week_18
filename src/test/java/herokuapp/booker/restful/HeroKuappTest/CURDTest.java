package herokuapp.booker.restful.HeroKuappTest;

import herokuapp.booker.restful.info.HeroKuappSteps;
import herokuapp.booker.restful.testBase.TestBase;
import herokuapp.booker.restful.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CURDTest extends TestBase {
    static String firstname = "Jim" + TestUtils.getRandomValue();
    static String lastName = "Brown" + TestUtils.getRandomValue();
    static int totalPrice = 111;
    static Boolean depositpaid = true;
    static String additionalneeds = "Breakfast";
    static int bookingID;
    @Steps
    HeroKuappSteps heroKuappSteps;

    @Title("This will create a new booking")
    @Test
    public void test001() {
        HashMap<Object, Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkOut", "2019-01-01");
        ValidatableResponse response = heroKuappSteps.createBooking(firstname, lastName, totalPrice, depositpaid, bookingdates, additionalneeds);
        response.log().all().statusCode(200);
        bookingID = response.log().all().extract().path("bookingid");
        System.out.println(bookingID);
    }
    @Title("Verify if the booking was added to the list")
    @Test
    public void test002() {
        HashMap<String, Object> bookingMap = heroKuappSteps.getBookingInfoByID(bookingID);
        Assert.assertThat(bookingMap, hasValue(firstname));
        System.out.println(bookingMap);

    }

    @Title("Update the store information and verify updated information")
    @Test
    public void test003() {
        HashMap<Object, Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");

        firstname = "James";
        depositpaid = false;
        heroKuappSteps.updateBooking(bookingID,firstname,lastName,totalPrice,depositpaid,dates,additionalneeds).log().all().statusCode(200);
        HashMap<String, Object> bookingMap = heroKuappSteps.getBookingInfoByID(bookingID);
        Assert.assertThat(bookingMap, hasValue(firstname));
    }

    @Title("Delete the booking and verify if the list is deleted!")
    @Test
    public void test004() {
        heroKuappSteps.deleteBooking(bookingID).statusCode(201);
        heroKuappSteps.getBookingById(bookingID).statusCode(404);
    }

}
