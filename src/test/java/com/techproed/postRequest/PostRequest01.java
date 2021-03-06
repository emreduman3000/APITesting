package com.techproed.postRequest;

import apiTesting.TestBase;
import com.google.gson.JsonParser;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class PostRequest01 extends TestBase {

    /*
    Post Request icin;
        end-point sart
        request Body sart
        authorization sart
        accept type istege baglıdır
        content type istege baglıdır


    GET and POST farkı?
        get request icin endpoint yeterlidir-json data ceker
        post icin request body gerekır-data yollar

    NOTE:
        API developerlar olusturulacak datanın bazı bolumlerının
        NOT NULL, UNIQUE olması durumunda, post request olusturulurken
        kesinlikle o kısımlara uygun deger verilerek POST Request olusturulur.
        SQL'de CONSTRAINT GIBI DUSUN  PK NOT NULL BOS OLAMAZ

        YOKSA, 400 Bad Request http status code alırız
     */



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
        //1.Way - not good

        String jsonRequestBody="{\n" +
                        "\"firstname\": \"Sally\",\n" +
                        "\"lastname\": \"Brown\",\n" +
                        "\"totalprice\": 383,\n" +
                        "\"depositpaid\": true,\n" +
                        "\"bookingdates\": {\n" +
                        "\"checkin\": \"2018-11-30\",\n" +
                        "\"checkout\": \"2019-09-11\"\n" +
                        "},\n" +
                        "\"additionalneeds\":\"Wifi\"\n" +
                        "}";


        //requestSpecification01.pathParam("bookingid",5);' e post yapamayız put patch yapabiliriz
        //post yaparken kullanılmayan id atanır request body'e
        Response response =
                given().
                    contentType(ContentType.JSON).//get() request'te accept(ContentType.JSON) diyorduk
                    spec(requestSpecification01).//"https://restful-booker.herokuapp.com/"
                    auth().basic("admin","password123").
                    body(jsonRequestBody).
                    when().
                    post("booking");


        //yollanan request body yi görmek istiyoruz
        response.prettyPrint();

        /*
        {
            "bookingid": 22,
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


}
