package com.techproed.deleteRequest;

import apiTesting.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteRequest01 extends TestBase {


        @Test
        public void delete01() {

            Response responseBeforeDelete = given().
                    spec(requestSpecification03).
                    when().
                    get("198");

            responseBeforeDelete.
                    then().
                    assertThat().
                    statusCode(200);

            responseBeforeDelete.prettyPrint();
            /*
            {

                "userId": 10,
                "id": 198,
                "title": "quis eius est sint explicabo",
                "completed": true
            }
            */


            Response responseAfterDelete = given().
                    spec(requestSpecification03).
                    when().
                    delete("/198");

            responseAfterDelete.
                    then().
                    assertThat().
                    statusCode(200);

            responseAfterDelete.prettyPrint();
            /*
                {

                }
             */


            Response getResponseAfterDelete = given().
                    spec(requestSpecification03).
                    when().
                    get("/198");

            getResponseAfterDelete.prettyPrint();
            /*
            {
                "userId": 10,
                "id": 198,
                "title": "quis eius est sint explicabo",
                "completed": true
            }
             */




        }
}
