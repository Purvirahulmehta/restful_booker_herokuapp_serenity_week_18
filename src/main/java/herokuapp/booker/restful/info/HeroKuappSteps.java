package herokuapp.booker.restful.info;

import herokuapp.booker.restful.constants.EndPoint;
import herokuapp.booker.restful.model.Pojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class HeroKuappSteps {
        @Step("Creating booking with firstname : {0}, lastname: {1}, totalprice : {2}, depositpaid : {3}, bookingdates : {4} and additionalneeds : {5}")
        public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, Boolean depositpaid, HashMap<Object, Object> bookingdates,
                                                 String additionalneeds) {
          Pojo pojo = new Pojo();
            pojo.setFirstname(firstname);
            pojo.setLastname(lastname);
            pojo.setTotalprice(totalprice);
            pojo.setDepositpaid(depositpaid);
            pojo.setBookingdates(bookingdates);
            pojo.setAdditionalneeds(additionalneeds);

            return SerenityRest.given()
                    .header("Content-Type", "application/json")
                    .auth().preemptive().basic("admin","password123")
                    .body(pojo)
                    .when()
                    .post(EndPoint.CREATE_BOOKING_BY_ID)
                    .then();
        }

    @Step("Creating booking with bookingID {0}, firstname : {1}, lastname: {2}, totalprice : {3}, depositpaid : {4}, bookingdates : {5} and additionalneeds : {6}")
    public ValidatableResponse updateBooking(int bookingID,String firstname, String lastname, int totalprice, Boolean depositpaid, HashMap<Object, Object> bookingdates,
                                             String additionalneeds) {
      Pojo pojo = new Pojo();
        pojo.setFirstname(firstname);
        pojo.setLastname(lastname);
        pojo.setTotalprice(totalprice);
        pojo.setDepositpaid(depositpaid);
        pojo.setBookingdates(bookingdates);
        pojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .auth().preemptive().basic("admin","password123")
                .body( pojo)
                .pathParam("bookingID",bookingID)
                .auth().preemptive().basic("admin","password123")
                .when()
                .put(EndPoint.UPDATE_BOOKING_BY_ID)
                .then();
    }
    @Step("Getting the booking information with bookingID : {0}")
    public HashMap<String, Object> getBookingInfoByID(int bookingID){
        HashMap<String, Object> bookingMap = SerenityRest.given().log().all().
                when()
                .pathParam("bookingID", bookingID)
                .auth().preemptive().basic("admin","password123")
                .get(EndPoint.GET_SINGLE_BOOKING_BY_ID)
                .then()
                .statusCode(200)
                .extract().path("");
        return bookingMap;
    }

    @Step("Deleting booking information with userID : {0}")
    public ValidatableResponse deleteBooking(int bookingID){
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("bookingID", bookingID)
                .auth().preemptive().basic("admin","password123")
                .when()
                .delete(EndPoint.DELETE_BOOKING_BY_ID)
                .then();
    }
    @Step("Getting booking information with userID: {0}")
    public ValidatableResponse getBookingById(int bookingID){
        return SerenityRest.given().log().all()
                .auth().preemptive().basic("admin","password123")
                .pathParam("bookingID", bookingID)
                .when()
                .get(EndPoint.GET_SINGLE_BOOKING_BY_ID)
                .then();
    }

}
