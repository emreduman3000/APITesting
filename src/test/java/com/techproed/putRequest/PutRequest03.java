package com.techproed.putRequest;

import apiTesting.TestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class PutRequest03 extends TestBase {

    /*
  put yaptıktan sonra statuscodu ve
  dataları verify et
   */
    @Test
    public void putRequest01()
    {
        requestSpecification01.pathParam("bookingid","1");

        //retrieve the json data
        Response response=given().
                spec(requestSpecification01).//https://restful-booker.herokuapp.com/
                //accept(ContentType.JSON).
                when().
                get("booking/{bookingid}");

        //printing out
        response.prettyPrint();
        /*
           {
                "firstname": "Susan",
                "lastname": "Brown",
                "totalprice": 800,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2017-12-23",
                    "checkout": "2020-10-03"
                },
                "additionalneeds": "Breakfast"
            }
         */

        JSONObject jsonBookingDatesObject=new JSONObject();//bunu kullan
        jsonBookingDatesObject.put("checkin","2018-03-30");
        jsonBookingDatesObject.put("checkout", "2019-11-24");

        //put() kullanılırken tüm veriler update edilir
        //JsonObject jsonObject=new JsonObject(); bunu kullanma
        JSONObject jsonBookingObject=new JSONObject();//bunu kullan
        jsonBookingObject.put("firstname","Mary2");
        jsonBookingObject.put("lastname","Jones2");//id yi updateliyemiyoruz bu endPointte
        jsonBookingObject.put("totalprice", 226);
        jsonBookingObject.put("depositpaid", false);
        jsonBookingObject.put("bookingdates", jsonBookingDatesObject);
        jsonBookingObject.put("additionalneeds", "wifi");

        System.out.println(jsonBookingObject.toString());
//{"firstname":"Mary2","additionalneeds":"wifi","bookingdates":{"checkin":"2018-03-30","checkout":"2019-11-24"},"totalprice":226,"depositpaid":false,"lastname":"Jones2"}



        Response responseAfterPut=given().
                        spec(requestSpecification01).
                        contentType(ContentType.JSON).//I'm a teapot hatası veriyor
                       //contentType'ın tastiklenmesi şarttır!!!
                        auth().basic("admin","password123").
                        body(jsonBookingObject.toString()).
                        when().
                        put("booking/{bookingid}");

        responseAfterPut.prettyPrint();


        given().
                //accept(ContentType.JSON).
                spec(requestSpecification01).
                when().
                get("booking/{bookingid}").prettyPrint();
        //get request yaptıgım güncellenmiş bir sekilde gelmiyor
        //api adresiyle alakalı bir sey


        //status codu-ContentType verify
        responseAfterPut.
                then().
                assertThat().
                statusCode(201).//201 olarak da ayarlanmıs olabilir
                contentType(ContentType.JSON);


        //dataları verify et
        JsonPath jsonPath=responseAfterPut.jsonPath();
        SoftAssert softAssert=new SoftAssert();

        softAssert.assertEquals(jsonPath.getString("firstname"), jsonBookingObject.getString("firstname"));
        softAssert.assertEquals(jsonPath.getString("lastname"),jsonBookingObject.getString("lastname"));
        softAssert.assertEquals(jsonPath.getInt("totalprice"),jsonBookingObject.getInt("totalprice"));
        softAssert.assertEquals(jsonPath.getBoolean("depositpaid"),jsonBookingObject.getBoolean("depositpaid"));
        softAssert.assertEquals(jsonPath.getJsonObject("bookingdates.checkin"),new JSONObject(jsonBookingObject.getJSONObject("bookingdates").toString()).getString("checkin"));
        softAssert.assertEquals(jsonPath.getJsonObject("bookingdates.checkout"),new JSONObject(jsonBookingObject.getJSONObject("bookingdates").toString()).getString("checkout"));
        softAssert.assertEquals(jsonPath.getString("additionalneeds"),jsonBookingObject.getString("additionalneeds"));

        softAssert.assertAll();


    }
}
