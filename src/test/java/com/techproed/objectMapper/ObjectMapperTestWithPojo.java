package com.techproed.objectMapper;

import apiTesting.TestBase;
import com.techproed.pojos.Booking;
import com.techproed.pojos.Bookingdates;
import com.techproed.utilities.JsonUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ObjectMapperTestWithPojo extends TestBase {

    @Test
    public void javaToJson()
    {
        //POJO Class

        Bookingdates bookingdates=new Bookingdates("2020-10-10","2020-10-12");
        Booking booking=new Booking("Eric","Brown",436,false,bookingdates,"Breakfast");

        System.out.println(booking);
//com.techproed.pojos.Booking@51c668e3[firstname=Eric,lastname=Brown,totalprice=436,depositpaid=false,bookingdates=com.techproed.pojos.Bookingdates@2e6a8155[checkin=2020-10-10,checkout=2020-10-12],additionalneeds=Breakfast]

        //bookingdates java object'ini JSON'a convert ettik-serialization
        String jsonToJava= JsonUtil.convertJavaToJson(booking);//calısmıyor
        System.out.println(jsonToJava);

        ObjectMapper objectMapper=new ObjectMapper();
        try {
            String jsonToJava2=objectMapper.writeValueAsString(booking);
            System.out.println(jsonToJava2);
        } catch (IOException e) {
            e.printStackTrace();
        }
//{"firstname":"Eric","lastname":"Brown","totalprice":436,"depositpaid":false,"bookingdates":{"checkin":"2020-10-10","checkout":"2020-10-12","additionalProperties":{}},"additionalneeds":"Breakfast","additionalProperties":{}}

    }



    @Test
    public void jsonToJava()
    {
        Response response=given().
                spec(requestSpecification01).
                when().
                get("booking/3");

        response.prettyPrint();
        /*
        {
            "firstname": "Eric",
            "lastname": "Brown",
            "totalprice": 436,
            "depositpaid": false,
            "bookingdates": {
                "checkin": "2015-07-12",
                "checkout": "2018-11-25"
            },
            "additionalneeds": "Breakfast"
        }
         */

        //assertion of JSON
        response.
                then().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON);


        //JSON DATA -> Booking Pojo Class
        Booking bookingJsontPojo= JsonUtil.convertJsonToJava(response.asString(), Booking.class);
        System.out.println(bookingJsontPojo);
        //com.techproed.pojos.Booking@3fcdcf[firstname=Eric,lastname=Brown,totalprice=436,depositpaid=false,bookingdates=com.techproed.pojos.Bookingdates@7668d560[checkin=2015-07-12,checkout=2018-11-25],additionalneeds=Breakfast]

        //ASSERTION
        Bookingdates bookingdates=new Bookingdates("2015-07-12","2018-11-25");
        Booking booking=new Booking("Eric","Brown",436,false,bookingdates,"Breakfast");

        Assert.assertEquals(bookingJsontPojo.getFirstname(),booking.getFirstname());
        Assert.assertEquals(bookingJsontPojo.getLastname(),booking.getLastname());
        Assert.assertEquals(bookingJsontPojo.getTotalprice(),booking.getTotalprice());
        Assert.assertEquals(bookingJsontPojo.getDepositpaid(),booking.getDepositpaid());
        Assert.assertEquals(bookingJsontPojo.getBookingdates().getCheckin(),booking.getBookingdates().getCheckin());
        Assert.assertEquals(bookingJsontPojo.getBookingdates().getCheckout(),booking.getBookingdates().getCheckout());
        Assert.assertEquals(bookingJsontPojo.getAdditionalneeds(),booking.getAdditionalneeds());
    }
}
