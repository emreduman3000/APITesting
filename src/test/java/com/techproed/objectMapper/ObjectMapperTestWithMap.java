package com.techproed.objectMapper;

import apiTesting.TestBase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.techproed.utilities.JsonUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ObjectMapperTestWithMap extends TestBase {

    @Test
    public void javaToJson() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1,"A");
        hashMap.put(2,"B");
        hashMap.put(3,"C");
        System.out.println(hashMap);
        //{1=A, 2=B, 3=C}

        String javaToJson=JsonUtil.convertJavaToJson(hashMap);//*****calısmıyor
        System.out.println(javaToJson);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(hashMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //{"1":"A","2":"B","3":"C"}

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

        //de-serialization
        Map<String, Object> jsonToMap=JsonUtil.convertJsonToJava(response.asString(),Map.class);
        System.out.println(jsonToMap);
//{firstname=Eric, lastname=Brown, totalprice=436, depositpaid=false, bookingdates={checkin=2015-07-12, checkout=2018-11-25}, additionalneeds=Breakfast}

        //assertion
        Map<String, Object> bookingdates = new HashMap();
        bookingdates.put("checkin","2015-07-12");
        bookingdates.put("checkout","2018-11-25");

        Map<String, Object> booking = new HashMap();
        booking.put("firstname","Eric");
        booking.put("lastname", "Brown");
        booking.put("totalprice", 436);
        booking.put("depositpaid", false);
        booking.put("bookingdates", bookingdates);
        booking.put("additionalneeds","Breakfast");



        Assert.assertEquals(jsonToMap.get("firstname"),booking.get("firstname"));
        Assert.assertEquals(jsonToMap.get("lastname"),booking.get("lastname"));
        Assert.assertEquals(jsonToMap.get("totalprice"),booking.get("totalprice"));
        Assert.assertEquals(jsonToMap.get("depositpaid"),booking.get("depositpaid"));
        Assert.assertEquals(new HashMap((Map)jsonToMap.get("bookingdates")).get("checkin"),new HashMap((Map)booking.get("bookingdates")).get("checkin"));
        Assert.assertEquals(new HashMap((Map)jsonToMap.get("bookingdates")).get("checkout"),new HashMap((Map)booking.get("bookingdates")).get("checkout"));
        Assert.assertEquals(jsonToMap.get("additionalneeds"),booking.get("additionalneeds"));




        //serialization - calısmıyor
        String mapToJson=JsonUtil.convertJavaToJson(jsonToMap);//****************
        System.out.println(mapToJson);

        ObjectMapper mapper=new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(jsonToMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //{"firstname":"Mark","lastname":"Smith","totalprice":490,"depositpaid":false,"bookingdates":{"checkin":"2017-02-06","checkout":"2020-04-30"}}

    }

    @Test
    public void javaToJsonToJava()
    {

        requestSpecification03.pathParam("id",3);
        Response response = given().
                spec(requestSpecification03).//"https://jsonplaceholder.typicode.com/todos"
                accept(ContentType.JSON).
                when().
                get("{id}");// get("/3");

        String stringJsonResponse=response.getBody().asString();
        System.out.println(stringJsonResponse);
        /*
        {
            "userId": 1,
            "id": 3,
            "title": "fugiat veniam minus",
            "completed": false
        }
         */

        //GSON deserialization
        HashMap<String, Object> toJava = response.as(HashMap.class);
        System.out.println(toJava);
        //{id=3, completed=false, title=fugiat veniam minus, userId=1}

        //GSON-serialization
        String toJson=new Gson().toJson(toJava);
        System.out.println(toJson);
        //{"id":3,"completed":false,"title":"fugiat veniam minus","userId":1}

        //GSON-deserialization
        HashMap<String, Object> fromJson= new Gson().fromJson(toJson, HashMap.class);
        System.out.println(fromJson);
        //{id=3.0, completed=false, title=fugiat veniam minus, userId=1.0}

        //GSON-deserialization
        HashMap<String, String> fromJson2= new Gson().fromJson(stringJsonResponse, HashMap.class);
        System.out.println(fromJson2);
        //{id=3.0, completed=false, title=fugiat veniam minus, userId=1.0}


        //ObjectMapper ile
        //java formatı json formatta verir- serialization
        String javaToJson=JsonUtil.convertJavaToJson(toJava);//**************
        System.out.println(javaToJson);

        //objectMapper ile deserialiation
        HashMap jsonToJava=JsonUtil.convertJsonToJava(stringJsonResponse,HashMap.class);
        System.out.println(jsonToJava);
        //{id=3, completed=false, title=fugiat veniam minus, userId=1}

    }
}
