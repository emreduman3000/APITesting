package apiTesting;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TestBase {

    protected RequestSpecification
            requestSpecification01,
            requestSpecification02,
            requestSpecification03;

    protected Response response;
    protected JsonPath jsonPath;
    protected SoftAssert softAssert;

    public Map<String, String> bookingDatesMap=new HashMap<>();
    public Map<String, Object> requestBodyMap = new HashMap();


    protected JSONObject jsonRequestBody;
    protected JSONObject jsonBookingdatesObject;


    @BeforeTest
    public void setUp01(){
        requestSpecification01=new RequestSpecBuilder().
                                setBaseUri("https://restful-booker.herokuapp.com/").//BASE URL
                                build();
        //requestSpecification01 object'in içinde END-POINT'im var
        //FOR TESTS, I SET UP BASE URL
    }


    @BeforeTest
    public void setUp02(){
        requestSpecification02=new RequestSpecBuilder().
                setBaseUri("https://dummy.restapiexample.com/api/v1/employees").
                build();
        //requestSpecification01 object'in içinde END-POINT'im var
        //FOR TESTS, I SET UP BASE URL
    }


    @BeforeTest
    public void setUp03(){
        requestSpecification03=new RequestSpecBuilder().
                setBaseUri("https://jsonplaceholder.typicode.com/todos").
                build();

    }

    protected  Response createRequestBodyByJSONObjectClass()
    {

        jsonBookingdatesObject=new JSONObject();
        jsonBookingdatesObject.put("checkin","2018-11-30");
        jsonBookingdatesObject.put("checkout","2019-09-11");

        System.out.println(jsonBookingdatesObject);
        //{"checkin":"2018-11-30","checkout":"2019-09-11"}


        jsonRequestBody=new JSONObject();
        jsonRequestBody.put("firstname", "Sally");
        jsonRequestBody.put("lastname", "Brown");
        //jsonRequestBody.put("totalprice", "383");//BU DA calısır
        jsonRequestBody.put("totalprice", 383);
        //jsonRequestBody.put("depositpaid", "true");//BU DA calısır
        jsonRequestBody.put("depositpaid", true);
        jsonRequestBody.put("bookingdates",jsonBookingdatesObject);
        jsonRequestBody.put("additionalneeds","Wifi");


        requestSpecification01.pathParam("booking","booking");

         response= given().
                contentType(ContentType.JSON).
                spec(requestSpecification01).
                auth().basic("admin","password123").
                body(jsonRequestBody.toString())//body() her zaman String ister
                .when()
                .post("{booking}");


        jsonPath = response.jsonPath();
        softAssert=new SoftAssert();

        return response;

    }


    protected  Response createRequestBodyByHashMap()
    {
        //JSONObject Class daha az kullanılıyormus
        bookingDatesMap.put("checkin", "2018-11-30");
        bookingDatesMap.put("checkout", "2019-09-11");

        requestBodyMap.put("firstname","Sally");
        requestBodyMap.put("lastname", "Brown");
        requestBodyMap.put("totalprice", 383);
        requestBodyMap.put("depositpaid", true);
        requestBodyMap.put("bookingdates", bookingDatesMap);
        requestBodyMap.put("additionalneeds","Wifi");

        requestSpecification01.pathParam("booking","booking");

        response= given().
                contentType(ContentType.JSON).
                spec(requestSpecification01).
                auth().basic("admin","password123").
                body(requestBodyMap)
                .when()
                .post("{booking}");

        jsonPath = response.jsonPath();
        softAssert=new SoftAssert();

        return response;

    }



    protected  Response createRequestBodyByHashMap(Map<String, String> bookingDatesMap,Map<String, Object> requestBodyMap)
    {


        //JSONObject Class daha az kullanılıyormus
        bookingDatesMap.put("checkin", "2018-11-30");
        bookingDatesMap.put("checkout", "2019-09-11");

        requestBodyMap.put("firstname","Sally");
        requestBodyMap.put("lastname", "Brown");
        requestBodyMap.put("totalprice", 383);
        requestBodyMap.put("depositpaid", true);
        requestBodyMap.put("bookingdates", bookingDatesMap);
        requestBodyMap.put("additionalneeds","Wifi");

        requestSpecification01.pathParam("booking","booking");

        response= given().
                contentType(ContentType.JSON).
                spec(requestSpecification01).
                auth().basic("admin","password123").
                body(requestBodyMap)
                .when()
                .post("{booking}");

        jsonPath = response.jsonPath();
        softAssert=new SoftAssert();

        return response;

    }


}
