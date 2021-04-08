package com.techproed.getRequest;

import apiTesting.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
//import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
//import static org.testng.AssertJUnit.assertEquals;
import static org.testng.Assert.assertEquals;

public class GetRequest07 extends TestBase {

    @Test
    public void getRequest01(){

        //requestSpecification01.queryParam("","");
        requestSpecification01.pathParam("bookingid",6);

        Response response=given().
                            spec(requestSpecification01).
                            //accept(ContentType.JSON).
                          when()
                            //.get("booking/6");
                             .get("booking/{bookingid}");

        String jsonData=given().
                            spec(requestSpecification01).
                            //accept(ContentType.JSON).
                            when().
                            get("booking/{bookingid}").asString();
        System.out.println(jsonData);
        //{"firstname":"Sally","lastname":"Jackson","totalprice":139,"depositpaid":false,"bookingdates":{"checkin":"2019-10-06","checkout":"2020-03-31"}}

        String jsonDataInResponse=given().
                spec(requestSpecification01).
                //accept(ContentType.JSON).
                        when().
                get("booking/{bookingid}").
                        then().
                        contentType(ContentType.JSON).
                        extract().
                        response().
                        asString();
        System.out.println(jsonDataInResponse);
        //{"firstname":"Sally","lastname":"Jackson","totalprice":139,"depositpaid":false,"bookingdates":{"checkin":"2019-10-06","checkout":"2020-03-31"}}

        String name=given().
                spec(requestSpecification01).
                        when().
                        get("booking/{bookingid}").then().
                        extract().
                        response().jsonPath().getString("firstname");

        System.out.println(name);//Sally



        String url=given().
                spec(requestSpecification01).
                when().
                get("booking/{bookingid}").then().
                extract().path("firstname");
        System.out.println(url);//Jim


        response.prettyPrint();//teapot
        System.out.println(response.getStatusCode());

        //json formatındaki dataların cinde gezebilmemizi saglar
        JsonPath jsonPath=response.jsonPath();

        System.out.println(jsonPath.getString("firstname")+"\n"+
                jsonPath.getString("lastname")+"\n"+
                jsonPath.getInt("totalprice")+"\n"+
                jsonPath.getBoolean("depositpaid")+"\n"+
                jsonPath.getString("bookingdates.checkin")+"\n"+
                jsonPath.getString("bookingdates.checkout")+"\n"+
                jsonPath.getString("bookingdates")
        );

        //System.out.println(jsonPath.get());
/*
        assertEquals(jsonPath.getString("firstname"),"Sally","firstname is not as expected");//testNG-assertEquals()
        assertEquals(jsonPath.getString("lastname"),"Brown","lastname is not as expected");
        assertEquals(jsonPath.getInt("totalprice"),383,"totalprice is not as expected");
        assertEquals(jsonPath.getBoolean("depositpaid"),true,"depositpaid is not as expected" );
        assertEquals(jsonPath.getString("bookingdates.checkin"),"2018-11-30","checkin is not as expected" );
        assertEquals(jsonPath.getString("bookingdates.checkout"),"2019-09-11","checkout is not as expected" );
        //assertEquals("checkout is not as expected","2019-09-11", jsonPath.getString("bookingdates.checkoout"));//JUnit-assertEquals

        /*
        {
            "firstname": "Sally",
            "lastname": "Brown",
            "totalprice": 383,
            "depositpaid": true,
            "bookingdates": {
                "checkin": "2018-11-30",
                "checkout": "2019-09-11"
            }
        }
         */
    }
}
