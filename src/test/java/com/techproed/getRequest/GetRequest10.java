package com.techproed.getRequest;

import apiTesting.TestBase;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GetRequest10 extends TestBase {

    /*
      GSON: 1)Json formatındaki dataları Java objectlerine dönüstürür. -DESERIALIZATION
              Map'e dönüştürür!!!


            //JSON FORMAT DATA
            {
                "userId": 10,
                "id": 200,
                "title": "ipsam aperiam voluptates qui",
                "completed": false
            }
            JAVA FORMAT DATA
            GSON -> Map<Object,Object>{ "userId": 10,   "id": 200,  "title": "ipsam aperiam voluptates qui",  "completed": false}


            2)Java Objectlerini Json formatındaki data'lara donusturur.-SERIALIZATION

                 <dependency>
                    <groupId>com.google.code.gson</groupId>
                    <artifactId>gson</artifactId>
                    <version>2.8.6</version>
                </dependency>
   */


    @Test
    public void getRequest01()
    {
        requestSpecification03.pathParam("userId","2");
        response = given().
                        spec(requestSpecification03).//"https://jsonplaceholder.typicode.com/todos"
                        accept(ContentType.JSON).
                   when().
                        get("/{userId}");// get("/2");


        response.
            then().
                assertThat().
                    statusCode(200).
                    contentType(ContentType.JSON);

        response.prettyPrint();
        /*
            {
                "userId": 1,
                "id": 2,
                "title": "quis ut nam facilis et officia qui",
                "completed": false
            }
         */

        //HashMap sıraya dikkat etmez
        HashMap <String,String> hashMap = response.as(HashMap.class);//deserialization
        //all json format are being converted into String format

        System.out.println(hashMap);
        //1 json formatındaki body {} 1 hsahMap objecti yapar
        //{id=2, completed=false, title=quis ut nam facilis et officia qui, userId=1}

        System.out.println(hashMap.keySet());
        //[id, completed, title, userId]
        System.out.println(hashMap.values());
        //[2, false, quis ut nam facilis et officia qui, 1]


        SoftAssert softAssert=new SoftAssert();

        //completed key'sinin value'sunun  false old. verify et
        softAssert.assertEquals(hashMap.get("completed"),false, "false değil true");

        //userId, id, title verify them
        softAssert.assertEquals(hashMap.get("userId"),1);
        softAssert.assertEquals(hashMap.get("id"),2);
        softAssert.assertEquals(hashMap.get("title"),"quis ut nam facilis et officia qui");


        softAssert.assertAll();


        //java Object'ini JSON formatına dönüstürme- serialization
        Gson gson=new Gson();
        String toJsonFromHashMap=gson.toJson(hashMap);
        System.out.println(toJsonFromHashMap);
        //{"id":2,"completed":false,"title":"quis ut nam facilis et officia qui","userId":1}
        //json format



    }

}
