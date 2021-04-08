package com.techproed.getRequest;

import apiTesting.TestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

import static io.restassured.RestAssured.given;

public class GetRequest12 extends TestBase {

    @Test
    public void getRequest01()
    {
        Response response=given().
                            spec(requestSpecification02).
                            accept(ContentType.JSON).
                          when().
                            get();

        response.
            then().
                assertThat().
                    statusCode(200).
                    contentType(ContentType.JSON);

        response.prettyPrint();

        //firts 5 name?

        JsonPath jsonPath=response.jsonPath();
        SoftAssert softAssert=new SoftAssert();

        List listofFirst5 = Arrays.asList("Tiger Nixon","Garrett Winters","Ashton Cox","Cedric Kelly","Airi Satou");

        for(int i=0; i<listofFirst5.size(); i++)
        {
            softAssert.assertEquals(jsonPath.getString("data["+i+"].employee_name"),listofFirst5.get(i));
        }



        //firts 5 name?
        List<Map>list= jsonPath.getList("data");
        Map<Integer, String> name5=new HashMap();


        for(int i=0; i<5; i++){
            name5.put(i,list.get(i).get("employee_name").toString());
            //list.get(i).get("employee_name") - object olarak algılıyor bunu, so I add toString()
        }
        System.out.println(name5);
        //{0=Tiger Nixon, 1=Garrett Winters, 2=Ashton Cox, 3=Cedric Kelly, 4=Airi Satou}

        for(int i=0; i< name5.size(); i++){
            softAssert.assertEquals(jsonPath.getString("data["+ i +"].employee_name"),name5.get(i));
        }







        }
}
