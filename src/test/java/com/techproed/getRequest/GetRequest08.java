package com.techproed.getRequest;

import apiTesting.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.*;

public class GetRequest08 extends TestBase {

    @Test
    public void getRequest01(){

        //requestSpecification02.pathParam("","");
        //requestSpecification02.queryParam("","");

        Response response=given().
                                 spec(requestSpecification02).
                            when().
                                get();

        response.prettyPrint();

        //JsonPath object created
        JsonPath jsonPath=response.jsonPath();

        //print out all employees' name in console
        System.out.println(jsonPath.getString("data.employee_name"));
        //[Tiger Nixon, Garrett Winters, Ashton Cox, Cedric Kelly, Airi Satou, Brielle Williamson, Herrod Chandler, Rhona Davidson, Colleen Hurst, Sonya Frost, Jena Gaines, Quinn Flynn, Charde Marshall, Haley Kennedy, Tatyana Fitzpatrick, Michael Silva, Paul Byrd, Gloria Little, Bradley Greer, Dai Rios, Jenette Caldwell, Yuri Berry, Caesar Vance, Doris Wilder]

        System.out.println(jsonPath.getList("data.employee_name"));
        //[Tiger Nixon, Garrett Winters, Ashton Cox, Cedric Kelly, Airi Satou, Brielle Williamson, Herrod Chandler, Rhona Davidson, Colleen Hurst, Sonya Frost, Jena Gaines, Quinn Flynn, Charde Marshall, Haley Kennedy, Tatyana Fitzpatrick, Michael Silva, Paul Byrd, Gloria Little, Bradley Greer, Dai Rios, Jenette Caldwell, Yuri Berry, Caesar Vance, Doris Wilder]


        //assertion0 HARD ASSERTION
        //2nd employee's name is Garrett Winters?
        assertEquals(jsonPath.getString("data.employee_name[1]"),"Garrett Winters","2nd employee's name is not expected");
        assertEquals(jsonPath.getJsonObject("data.employee_name[1]"),"Garrett Winters","2nd employee's name is not expected");
        //both of assertions are working

        //VERIFY - SOFT ASSERTION
        SoftAssert softAssert=new SoftAssert();

        softAssert.assertEquals(jsonPath.getString("data.employee_name[1]"),"Garrett Winters","2nd employee's name is not expected");

        //verify if  Herrod Chandler is among employees
        softAssert.assertTrue(jsonPath.getList("data.employee_name").contains("Herrod Chandler"),"Herrod Chandler is not avaliable");
        //contains(Object o) List'e özgüdür


        //verify if there are 24 employees
        softAssert.assertTrue(jsonPath.getList("data.employee_name").size()==24,"The amount of employees are not 24");
        softAssert.assertEquals(jsonPath.getList("data.employee_name").size(),24,"The amount of employees are not 24");


        //verify if 7. employee's salary is 137500 or not
        softAssert.assertEquals(jsonPath.getList("data.employee_salary").get(6),137500,"salary is "+jsonPath.getInt("data[6].employee_salary"));
        softAssert.assertEquals(jsonPath.getInt("data[6].employee_salary"),137500,"salary is "+jsonPath.getInt("data[6].employee_salary"));

        softAssert.assertAll();






    }
}
