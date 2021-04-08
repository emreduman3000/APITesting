package com.techproed.postRequest;

import apiTesting.TestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class PostRequest02 extends TestBase {

      /*
    POST SCENARIO:
    AcceptType : json
    when I post a request
    "https://restful-booker.herokuapp.com/"
    request body:

         {
            "firstname": "Sally",
            "lastname": "Brown",
            "totalprice": 383,
            "depositpaid": true,
            "bookingdates": {
                "checkin": "2018-11-30",
                "checkout": "2019-09-11"
            },
            "additionalneeds":"Wifi"
        }

        Then status code:200
        response body should match with request body

     */

    @Test
    public void postRequest01()
    {
        /*
          json obesi olusturmamıza yarar JSONObject Class'ı
          for instance:
               "bookingdates": {
                        "checkin": "2018-11-30",
                        "checkout": "2019-09-11"
                      }
          bu datayı bir json object yapmak istiyorum
         */

        JSONObject jsonBookingdatesObject=new JSONObject();
        jsonBookingdatesObject.put("checkin","2018-11-30");
        jsonBookingdatesObject.put("checkout","2019-09-11");

        System.out.println(jsonBookingdatesObject);
        //{"checkin":"2018-11-30","checkout":"2019-09-11"}


        JSONObject jsonRequestBody=new JSONObject();
        jsonRequestBody.put("firstname", "Sally");
        jsonRequestBody.put("lastname", "Brown");
        jsonRequestBody.put("totalprice", "383");
        jsonRequestBody.put("depositpaid", "true");
        jsonRequestBody.put("bookingdates",jsonBookingdatesObject);
        jsonRequestBody.put("additionalneeds","Wifi");


        requestSpecification01.pathParam("booking","booking");

        Response response= given().
                            contentType(ContentType.JSON).
                            spec(requestSpecification01).
                            auth().basic("admin","password123").
                            body(jsonRequestBody.toString())//body() her zaman String ister
                         .when()
                           .post("{booking}");

        response.prettyPrint();
        /*
        {
            "bookingid": 12,
            "booking": {
                "firstname": "Sally",
                "lastname": "Brown",
                "totalprice": 383,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2018-11-30",
                    "checkout": "2019-09-11"
                },
                "additionalneeds": "Wifi"
            }
        }
         */

        response.
            then().
                assertThat().
                    statusCode(200).
                    contentType(ContentType.JSON);


        JsonPath jsonPath = response.jsonPath();
        SoftAssert softAssert=new SoftAssert();

        //verify datas
        softAssert.assertEquals(jsonPath.getString("booking.firstname"),"Sally");
        softAssert.assertEquals(jsonPath.getString("booking.lastname"),"Brown");
        softAssert.assertEquals(jsonPath.getInt("booking.totalprice"),383);
        softAssert.assertEquals(jsonPath.getString("booking.totalprice"),"383");//üstteki ile aynı isi görür
        softAssert.assertEquals(jsonPath.getBoolean("booking.depositpaid"),true);
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),"2018-11-30");
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),"2019-09-11");
        softAssert.assertEquals(jsonPath.getString("booking.additionalneeds"),"Wifi");

        softAssert.assertAll();

    }

    @Test
    public void postRequest02()
    {

        //return Response Object
        createRequestBodyByJSONObjectClass().prettyPrint();

        //TestBase'deki respons'u createRequestBody() kullandı ve response'u return eder
        response.
            then().
                assertThat().
                    statusCode(200).
                    contentType(ContentType.JSON);

        //verify datas
        softAssert.assertEquals(jsonPath.getString("booking.firstname"),"Sally");
        softAssert.assertEquals(jsonPath.getString("booking.lastname"),"Brown");
        softAssert.assertEquals(jsonPath.getInt("booking.totalprice"),383);
        softAssert.assertEquals(jsonPath.getString("booking.totalprice"),"383");//üstteki ile aynı isi görür
        softAssert.assertEquals(jsonPath.getBoolean("booking.depositpaid"),true);
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),"2018-11-30");
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),"2019-09-11");
        softAssert.assertEquals(jsonPath.getString("booking.additionalneeds"),"Wifi");

        softAssert.assertAll();

    }
}
