package com.techproed.nasa;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NasaApiTesting {

    @Test(priority = 1)
    public void verifyValidAPIKey() {
        // RestAssured.baseURI = "https://api.nasa.gov";

        RequestSpecification spec01=new RequestSpecBuilder().
                setBaseUri("https://api.nasa.gov").//BASE URL
                build();

        spec01.queryParams("api_key", "WKvBmHPZbciOI6vGOg0NrD6jTTnYTEVE7TJjzJGo");

        //https://api.nasa.gov/planetary/apod?api_key=WKvBmHPZbciOI6vGOg0NrD6jTTnYTEVE7TJjzJGo
        Response response =given().
                spec(spec01).
                when().
                get("/planetary/apod");

        response.prettyPrint();

        response.
                then().
                assertThat().
                statusCode(200);

    }


    @Test(priority = 2)
    public void verifyDateandCopyright() {
        RestAssured.baseURI = "https://api.nasa.gov";
        RestAssured.basePath="/planetary/apod";

        given().
                param("api_key", "WKvBmHPZbciOI6vGOg0NrD6jTTnYTEVE7TJjzJGo").
                when().
                get().
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                body("date", equalTo("2021-02-14")).
                and().
                body("copyright", equalTo("Adam BlockTim Puckett"));
    }
    @Test(priority = 3)
    public void testDuplicateImageURLs() {
        RestAssured.baseURI = "https://api.nasa.gov";

        String url =
                given().
                        param("api_key", "WKvBmHPZbciOI6vGOg0NrD6jTTnYTEVE7TJjzJGo").
                        when().
                        get("/planetary/apod").
                        then().
                        statusCode(200).
                        extract().
                        path("url");

        System.out.println(url);
        //https://apod.nasa.gov/apod/image/2102/rosette_BlockPuckett_960.jpg

        String hdUrl =
                given().
                        queryParam("api_key", "WKvBmHPZbciOI6vGOg0NrD6jTTnYTEVE7TJjzJGo").
                        when().
                        get("/planetary/apod").
                        then().
                        statusCode(200).
                        extract().
                        path("hdurl");
        //https://apod.nasa.gov/apod/image/2102/rosette_BlockPuckett_2910.jpg

        System.out.println(hdUrl);

        Assert.assertNotEquals(url, hdUrl, "URL and HdURl Parameters are not the same");

    }
    @Test(priority = 4)
    public void verifyInvalidAPIKey() {
        RestAssured.baseURI = "https://api.nasa.gov";

        given().
                param("api_key", "1234VE7TJjzJGo").
                when().
                get("/planetary/apod").
                then().
                assertThat().
                statusCode(403);//böyle authontatication olmsdıgını 403 söyler

    }

    @Test(priority = 5)
    public void verifyNullAPIKey() {
        RestAssured.baseURI = "https://api.nasa.gov";

        given().
                param("api_key", "").
                when().
                get("/planetary/apod").
                then().
                assertThat().
                statusCode(403);//wrong authontativcation

    }

    @Test(priority = 6)//fails
    public void verifyInvalidDateandInvalidCopyright() {
        RestAssured.baseURI = "https://api.nasa.gov";

        Response response=given().
                param("api_key", "DEMO_KEY").
                when().
                get("/planetary/apod");


        response.prettyPrint();


        response.then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                body("date", equalTo("2019-01-13")).//2021-02-14
                and().
                body("copyright", equalTo("RoemmeltVenture Photography"));
    }


    @Test(priority = 7)//fails
    public void verifyValidDateandInvalidCopyright() {
        RestAssured.baseURI = "https://api.nasa.gov";

        HashMap <String, String>hashMap = new HashMap<>();
        hashMap.put("api_key", "WKvBmHPZbciOI6vGOg0NrD6jTTnYTEVE7TJjzJGo");
        hashMap.put("date","2019-01-14");

        given().
                queryParams(hashMap).
                //param("api_key", "WKvBmHPZbciOI6vGOg0NrD6jTTnYTEVE7TJjzJGo").
                //param("date","2019-01-14").
                when().
                get("/planetary/apod").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                body("date", equalTo("2019-01-14")).
                and().
                body("copyright", equalTo("1Nicholas RoemmeltVenture Photography"));
    }

    @Test(priority = 8)//fails
    public void verifyEmptyDateandCopyright() {
        RestAssured.baseURI = "https://api.nasa.gov";

        given().
                param("api_key", "WKvBmHPZbciOI6vGOg0NrD6jTTnYTEVE7TJjzJGo").
                when().
                get("/planetary/apod").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                body("date", equalTo(" ")).
                and().
                body("copyright", equalTo(" "));
    }

}
