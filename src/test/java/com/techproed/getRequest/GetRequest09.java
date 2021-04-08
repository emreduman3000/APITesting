package com.techproed.getRequest;

import apiTesting.TestBase;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

import static io.restassured.RestAssured.given;

public class GetRequest09 extends TestBase {

    @Test
    public void getRequest01()
    {
        //get the json format data and put into response body
        Response response=given().
                            spec(requestSpecification02).//"https://dummy.restapiexample.com/api/v1/employees"
                            accept(ContentType.JSON).
                         when().
                            get();//get request

        //assert if the httpStatusCode and Content-Type are as expected.
        response.
            then().
                assertThat().
                    statusCode(200).
                    contentType(ContentType.JSON);

        //print out json format data in response body
        response.prettyPrint();

        JsonPath jsonPath=response.jsonPath();
        SoftAssert softAssert=new SoftAssert();

        //GROVY LANGUAGE

        //print out all id which are greater than 10, id is String,so to compare we have convert into integer
        List<String> idList= jsonPath.getList("data.findAll{Integer.valueOf(it.id)>10}.id");
        //it=data "it" like "this" in Java
        //data. 'nın içine gir, her şeyi bul of(data.it>10)

        System.out.println(idList);
        //[11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]

        //verify if there are 14 id greater than 10
        softAssert.assertEquals(idList.size(),14,"employee number is not as expected");

       //verify if the age of "23" is the greatest among the all ages which are less than 30.
        List<String> ageList= jsonPath.getList("data.findAll{Integer.valueOf(it.employee_age)<30}.employee_age");
        System.out.println(ageList);
        Collections.sort(ageList);
        softAssert.assertEquals(ageList.get(ageList.size()-1),23);


        List<String> ageList2= jsonPath.getList("data.findAll{Integer.valueOf(it.employee_age)<30 && Integer.valueOf(it.employee_age)>=23 }.employee_age");
        System.out.println(ageList2);
        //[22, 23, 22, 19, 21, 23]

        Set<String> tSet = new TreeSet<>(ageList2);
        tSet.addAll(ageList2);
        System.out.println(tSet);//[23]

        softAssert.assertTrue(tSet.size()==1);

        //salary'si 35000'den büyük olan employeel'leri yazdır
        //Charde Marshall is available 35000ten fazla maas alan işciler arasında
        List<String> nameList= jsonPath.getList("data.findAll{Integer.valueOf(it.employee_salary)>35000}.employee_name");
        System.out.println(nameList);

        softAssert.assertTrue(nameList.contains("Charde Marshall"));

        softAssert.assertAll();

    }

}
