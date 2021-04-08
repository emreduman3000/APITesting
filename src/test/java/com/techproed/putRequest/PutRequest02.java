package com.techproed.putRequest;

import apiTesting.TestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class PutRequest02 extends TestBase {

    /*
    put yaptıktan sonra statuscodu ve
    dataları verify et
     */
    @Test
    public void putRequest01()
    {
        //retrieve the json data
        Response response=given().
                spec(requestSpecification03).//"https://jsonplaceholder.typicode.com/todos"
                accept(ContentType.JSON).
                when().
                get("/200");

        //printing out
        response.prettyPrint();
        /*
        {
            "userId": 10,
            "id": 200,
            "title": "ipsam aperiam voluptates qui",
            "completed": false
        }
         */

        //put() kullanılırken tüm veriler update edilir
        //JsonObject jsonObject=new JsonObject(); bunu kullanma
        JSONObject jsonObject=new JSONObject();//bunu kullan
        jsonObject.put("userId",11);
        //jsonObject.put("id","201");//id yi updateliyemiyoruz bu endPointte
        jsonObject.put("title","emre");
        jsonObject.put("completed",true);

        System.out.println(jsonObject.toString());
        //{"id":"201","completed":true,"title":"emre","userId":11}

        Response responseAfterPut=given().
                                    contentType(ContentType.JSON).
                            //contentType'ın tastiklenmesi şarttır!!!
                                    spec(requestSpecification03).
                            //auth().basic("admin","password123").
                                    body(jsonObject.toString()).
                                  when().
                                    put("/200");

        responseAfterPut.prettyPrint();
        /*
        {
            "id": 200,  ***id değismedi, swagger documentation'da datanın içeriği yazar,özelliği
            "completed": true,
            "title": "emre",
            "userId": 11
        }
         */

        given().
                accept(ContentType.JSON).
                spec(requestSpecification03).
                when().
                get("/200").prettyPrint();
        //get request yaptıgım güncellenmiş bir sekilde gelmiyor
        //api adresiyle alakalı bir sey


        //status codu-ContentType verify
        responseAfterPut.
                    then().
                        assertThat().
                        statusCode(200).//201 olarak da ayarlanmıs olabilir
                        contentType(ContentType.JSON);


        //dataları verify et
        JsonPath jsonPath=responseAfterPut.jsonPath();
        SoftAssert softAssert=new SoftAssert();

        softAssert.assertEquals(jsonPath.getInt("userId"),jsonObject.getInt("userId"));
        //softAssert.assertEquals(jsonObject.getInt("id"),jsonObject.getInt("id"));
        softAssert.assertEquals(jsonPath.getString("title"),jsonObject.getString("title"));
        softAssert.assertEquals(jsonPath.getBoolean("completed"),jsonObject.getBoolean("completed"));

        softAssert.assertAll();
    }
}
