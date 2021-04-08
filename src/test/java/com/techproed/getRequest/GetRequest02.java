package com.techproed.getRequest;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class GetRequest02 {

    /*
    Positive Scenario
    when() a getRequest is sent to  given end-point
    "https://restful-booker.herokuapp.com/booking"

    And() Accept Type is "application/json"
    then() statusCode is 200
    and content-type is "application/json"
    */

    @Test
    public void getRequest01()
    {
        given().
            accept("application/json").//json format'ında data kllanmak istiyorum
        when().//http request methods kullanılır when()'den sonra
            get("https://restful-booker.herokuapp.com/booking").
        then().
            assertThat().
                statusCode(200).
                contentType("application/json");
    }

    /*
   Negative Scenario
   when() a getRequest is sent to  given end-point
   "https://restful-booker.herokuapp.com/booking/47"

   And() Accept Type is "application/json"
   then() statusCode is 404 ise content type yoktur

   */
    @Test
    public void getRequest02()
    {
        //getting data
        Response response = given().
            accept("application/json").
        when().
            get("https://restful-booker.herokuapp.com/booking/47");

        //printing
        response.prettyPrint();

        //assertion
        response.
        then().
            assertThat().
                statusCode(404);//Not Found
        //.contentType("application/json");data yoka content type ına bakılamaz
    }

    /*
     Negative Scenario
     when() a getRequest is sent to  given end-point
     "https://restful-booker.herokuapp.com/booking/1001"

     And() Accept Type is "application/json"
     then() statusCode is 404
     and() response body is "Not Found"
     and() "emre" is not available in response body

     */
    @Test
    public void getRequest03()
    {
        Response response=given().
                accept("application/json").
                when().
                get("https://restful-booker.herokuapp.com/booking/1001");

        response.prettyPrint();

        assertEquals(404,response.getStatusCode());
        assertTrue(response.asString().contains("Not Found"));
        //asString() converting into String object
        assertFalse(response.asString().contains("emre"));


        //response.then().assertThat().statusCode(404);
    }

}
