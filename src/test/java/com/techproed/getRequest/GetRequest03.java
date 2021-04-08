package com.techproed.getRequest;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;


public class GetRequest03 {

    /*
    positive Scenario:
    When I send a GET REQUEST to REST API END POINT
    "https://restful-booker.herokuapp.com/booking/1"
    and Accept-type is "applicaiton/json"
    then
    HTTP Status-Code is 200
    and Response Fromat is "application/json"
    and firstname is "Susan"
    and lastname is "Ericsson"
    and "checkin": "2018-04-13",
    and "checkout": "2019-12-22"
     */
    @Test
    public void getRequest01()
    {
        Response response= given().
                accept("application/json").
                when().//http request methods kullanılır when()'den sonra
                    get("https://restful-booker.herokuapp.com/booking/1");

        response.prettyPrint();

        response.
            then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("firstname", Matchers.equalTo("Susan")).
                body("lastname", Matchers.equalTo("Ericsson")).
                body("totalprice", Matchers.equalTo(464)).
                body("depositpaid", Matchers.equalTo(false)).
                body("bookingdates.checkin", Matchers.equalTo("2018-04-13")).
                body("bookingdates.checkout", Matchers.equalTo("2019-12-22"));


        //not to repeat body() method again and again
        response.
                then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                body("firstname", Matchers.equalTo("Susan"),
                        "lastname", Matchers.equalTo("Ericsson"),
                        "totalprice", Matchers.equalTo(464),
                        "depositpaid", Matchers.equalTo(false),
                        "bookingdates.checkin", Matchers.equalTo("2018-04-13"),
                        "bookingdates.checkout", Matchers.equalTo("2019-12-22"));



    }
}
