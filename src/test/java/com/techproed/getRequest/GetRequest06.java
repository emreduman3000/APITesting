package com.techproed.getRequest;

import apiTesting.TestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;


public class GetRequest06 extends TestBase {

    //Among the data, there arer somenones whose first name is "Susan"
    @Test
    public void getRequest01()
    {
        Response response = given().
                                spec(requestSpecification01).
                            when().
                                get("booking?firstname=Susan&depositpaid=true");

        response.prettyPrint();
        /*
            [
                {
                    "bookingid": 2
                },
                {
                    "bookingid": 8
                }
            ]
        */

        assertTrue(response.getBody().asString().contains("bookingid"));

        response.
        then().
            assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("firstname", Matchers.hasSize(1));

    }


    @Test
    public void getRequest02()
    {
        //requestSpecification01 -> "https://restful-booker.herokuapp.com/"
        requestSpecification01.queryParam("firstname","Susan");
        requestSpecification01.queryParam("depositpaid",true);

        //requestSpecification01.queryParam() -> "https://restful-booker.herokuapp.com/booking?firstname=Susan"
        //when().get() queryParam'dan daa önce calısır.

        Response response = given().
                spec(requestSpecification01).
                when().
                get("booking");//"booking?firstname=Susan"

        response.prettyPrint();
        /*
            [
                {
                    "bookingid": 7
                }
            ]
         */
        /*
            "https://restful-booker.herokuapp.com/booking/7"
            {
                "firstname": "Susan",
                "lastname": "Smith",
                "totalprice": 613,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2019-10-07",
                    "checkout": "2020-05-03"
                },
                "additionalneeds": "Breakfast"
            }
         */


        assertTrue(response.getBody().asString().contains("bookingid"));


    }



    @Test
    public void getRequest03()
    {
        requestSpecification01.queryParams("firstname","Susan",
                                     "depositpaid",true);

        Response response = given().
                spec(requestSpecification01).
                when().
                get("booking");//"booking?firstname=Susan&depositpaid=true"

        response.prettyPrint();
        System.out.println(response.asString());//the same result


        assertTrue(response.getBody().asString().contains("bookingid"));
        //SUSAN and true olan bir değer varsa id cıkar bodyde

        System.out.println(response.asString());


    }
}
