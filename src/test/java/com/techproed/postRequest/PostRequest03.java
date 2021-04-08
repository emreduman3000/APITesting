package com.techproed.postRequest;

import apiTesting.TestBase;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

import static io.restassured.RestAssured.given;

public class PostRequest03 extends TestBase {

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
          HashMap kullandık
          for instance:
               "bookingdates": {
                        "checkin": "2018-11-30",
                        "checkout": "2019-09-11"
                      }
          bu datayı HashMap'in içine koyduk
         */

        //JSONObject Class daha az kullanılıyormus
        Map<String, String> bookingDatesMap=new HashMap<>();
        bookingDatesMap.put("checkin", "2018-11-30");
        bookingDatesMap.put("checkout", "2019-09-11");

        Map<String, Object> requestBodyMap = new HashMap();
        requestBodyMap.put("firstname","Sally");
        requestBodyMap.put("lastname", "Brown");
        requestBodyMap.put("totalprice", 383);
        requestBodyMap.put("depositpaid", true);
        requestBodyMap.put("bookingdates", bookingDatesMap);
        requestBodyMap.put("additionalneeds","Wifi");

        requestSpecification01.pathParam("booking","booking");

        Response response= given().
                                contentType(ContentType.JSON).
                                spec(requestSpecification01).
                                auth().basic("admin","password123").
                                body(requestBodyMap)
                            .when()
                                .post("{booking}");

        response.prettyPrint();

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
        createRequestBodyByJSONObjectClass().prettyPrint();

        //TestBase'deki respons'u createRequestBodyByHashMap() kullandı ve response'u return eder
        response.
            then().
                assertThat().
                    statusCode(200).
                    contentType(ContentType.JSON);

        //verify datas
        softAssert.assertEquals(jsonPath.getString("booking.firstname"),"Sally");//hard-coding
        softAssert.assertEquals(jsonPath.getString("booking.firstname"),jsonRequestBody.getString("firstname"));//dynamic-code
        softAssert.assertEquals(jsonPath.getString("booking.firstname"),jsonRequestBody.get("firstname"));//dynamic-code

        softAssert.assertEquals(jsonPath.getString("booking.lastname"),"Brown");
        softAssert.assertEquals(jsonPath.getString("booking.lastname"),jsonRequestBody.get("lastname"));

        softAssert.assertEquals(jsonPath.getInt("booking.totalprice"),383);
        softAssert.assertEquals(jsonPath.getString("booking.totalprice"),"383");//üstteki ile aynı isi görür
        softAssert.assertEquals(jsonPath.getString("booking.totalprice"),jsonRequestBody.get("totalprice").toString());

        softAssert.assertEquals(jsonPath.getBoolean("booking.depositpaid"),true);
        softAssert.assertEquals(jsonPath.getBoolean("booking.depositpaid"),jsonRequestBody.get("depositpaid"));

        System.out.println(new JSONObject(jsonRequestBody.get("bookingdates")));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),"2018-11-30");
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),jsonBookingdatesObject.get("checkin"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),jsonBookingdatesObject.getString("checkin"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),new JSONObject(jsonRequestBody.get("bookingdates").toString()).getString("checkin"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),new JSONObject(jsonRequestBody.getJSONObject("bookingdates").toString()).get("checkin"));
        //new JSONObject(String string); kabul eder *******


        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),"2019-09-11");
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),new JSONObject(jsonRequestBody.getJSONObject("bookingdates").toString()).get("checkout"));

        softAssert.assertEquals(jsonPath.getString("booking.additionalneeds"),"Wifi");
        softAssert.assertEquals(jsonPath.getString("booking.additionalneeds"),jsonRequestBody.getString("additionalneeds"));


        softAssert.assertAll();

    }

    @Test//*****************************************************
    public void postRequest03()
    {
        createRequestBodyByHashMap().prettyPrint();
        //TestBase'deki respons'u createRequestBodyByHashMap() kullandı ve response'u return eder

        System.out.println(bookingDatesMap);
        System.out.println(requestBodyMap);

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON);

        //verify datas
        softAssert.assertEquals(jsonPath.getString("booking.firstname"),requestBodyMap.get("firstname"));
        softAssert.assertEquals(jsonPath.getString("booking.lastname"),requestBodyMap.get("lastname"));
        softAssert.assertEquals(jsonPath.getInt("booking.totalprice"),requestBodyMap.get("totalprice"));
        softAssert.assertEquals(jsonPath.getBoolean("booking.depositpaid"),requestBodyMap.get("depositpaid"));

        Map map=new HashMap<>(bookingDatesMap);//map formatındaki ojeyi direk mapin içine attım,yeni olustu
        System.out.println(map);//{checkin=2018-11-30, checkout=2019-09-11}

        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),bookingDatesMap.get("checkin"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),new HashMap<>((Map<?, ?>) requestBodyMap.get("bookingdates")).get("checkin"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),bookingDatesMap.get("checkout"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),new HashMap<>((Map<?, ?>) requestBodyMap.get("bookingdates")).get("checkout"));

        softAssert.assertEquals(jsonPath.getString("booking.additionalneeds"),requestBodyMap.get("additionalneeds"));

        softAssert.assertAll();

    }

    @Test//*****************************************************
    public void postRequest04()
    {

        createRequestBodyByHashMap(bookingDatesMap,requestBodyMap).prettyPrint();
        //TestBase'deki respons'u createRequestBodyByHashMap() kullandı ve response'u return eder

        System.out.println(bookingDatesMap);
        System.out.println(requestBodyMap);

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON);

        //verify datas
        softAssert.assertEquals(jsonPath.getString("booking.firstname"),requestBodyMap.get("firstname"));
        softAssert.assertEquals(jsonPath.getString("booking.lastname"),requestBodyMap.get("lastname"));
        softAssert.assertEquals(jsonPath.getInt("booking.totalprice"),requestBodyMap.get("totalprice"));
        softAssert.assertEquals(jsonPath.getBoolean("booking.depositpaid"),requestBodyMap.get("depositpaid"));

        Map map=new HashMap<>(bookingDatesMap);//map formatındaki ojeyi direk mapin içine attım,yeni olustu
        System.out.println(map);//{checkin=2018-11-30, checkout=2019-09-11}

        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),bookingDatesMap.get("checkin"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),new HashMap<>((Map<?, ?>) requestBodyMap.get("bookingdates")).get("checkin"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),bookingDatesMap.get("checkout"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),new HashMap<>((Map<?, ?>) requestBodyMap.get("bookingdates")).get("checkout"));

        softAssert.assertEquals(jsonPath.getString("booking.additionalneeds"),requestBodyMap.get("additionalneeds"));

        softAssert.assertAll();

    }

    @Test//*****************************************************
    public void postRequest05()
    {
        Map<String, String> bookingDatesMap=new HashMap<>();
        Map<String, Object> requestBodyMap=new HashMap<>();

        createRequestBodyByHashMap(bookingDatesMap,requestBodyMap).prettyPrint();
        //TestBase'deki respons'u createRequestBodyByHashMap() kullandı ve response'u return eder

        System.out.println(bookingDatesMap);
        System.out.println(requestBodyMap);

        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON);

        //verify datas
        softAssert.assertEquals(jsonPath.getString("booking.firstname"),requestBodyMap.get("firstname"));
        softAssert.assertEquals(jsonPath.getString("booking.lastname"),requestBodyMap.get("lastname"));
        softAssert.assertEquals(jsonPath.getInt("booking.totalprice"),requestBodyMap.get("totalprice"));
        softAssert.assertEquals(jsonPath.getBoolean("booking.depositpaid"),requestBodyMap.get("depositpaid"));

        Map map=new HashMap<>(bookingDatesMap);//map formatındaki ojeyi direk mapin içine attım,yeni olustu
        System.out.println(map);//{checkin=2018-11-30, checkout=2019-09-11}

        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),bookingDatesMap.get("checkin"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkin"),new HashMap<>((Map<?, ?>) requestBodyMap.get("bookingdates")).get("checkin"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),bookingDatesMap.get("checkout"));
        softAssert.assertEquals(jsonPath.getString("booking.bookingdates.checkout"),new HashMap<>((Map<?, ?>) requestBodyMap.get("bookingdates")).get("checkout"));

        softAssert.assertEquals(jsonPath.getString("booking.additionalneeds"),requestBodyMap.get("additionalneeds"));

        softAssert.assertAll();

    }


}
