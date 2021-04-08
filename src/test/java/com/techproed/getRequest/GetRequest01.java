package com.techproed.getRequest;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
//sadece staticleri import eder, * ile her seyi al

public class GetRequest01 {

    //By using restAssured library, I'll do API automation Testing.

    /*
    given, and, then, when : gherkin language
    restassured library de gherkin languagedeki ifadeleri kullanır
     */
    @Test
    public void getRequest01()
    {
        given().
        when().//after when(), use END POINT, http request methods kullanılır when()'den sonra
            get("https://restful-booker.herokuapp.com").//END POINT -get request in postman
        then().//after then(), do assertion
            assertThat().
            statusCode(200);
        //assertThat if the status code is 200!!!

    }

    @Test
    public void getRequest02(){
        Response response =given().
                            when().
                                get("https://restful-booker.herokuapp.com/booking/3");
        //I put the data into response object.
        //the type of retrieved data by using end-point is response type

        response.prettyPrint(); // I print out the data into response
        //to write response body in console.

        //to print out status code
        System.out.println(response.getStatusCode());
        //200

        //IN POSTMAN, IN STATUS LINE,there are (STATUS-TIME-SIZE)
        System.out.println(response.statusLine());
        //HTTP/1.1 200 OK

        //Content-Type of data in Response Body
        System.out.println(response.getContentType());//1.way
        System.out.println(response.getHeader("getContentType"));//2.way
        //application/json; charset=utf-8

        System.out.println(response.getBody());
        //io.restassured.internal.RestAssuredResponseImpl@6c6357f9

        System.out.println(response.getBody().asString());
        //System.out.println(response.prettyPrint()); ile aynı result 'ı verir

        //IN POSTMAN, There is "headers" section of RESPONSE BODY
        System.out.println(response.getHeaders());//to get all infos in headers section
        /*
        Server=Cowboy
        Connection=keep-alive
        X-Powered-By=Express
        Content-Type=application/json; charset=utf-8
        Content-Length=196
        Etag=W/"c4-n+fEK7399yC/YOjqgPFYxCcTwC0"
        Date=Tue, 02 Feb 2021 19:57:12 GMT
        Via=1.1 vegur
         */

        //to get specific data in headers of response body
        System.out.println(response.getHeader("Server"));
        //Cowboy

        //ASSERTION OF STATUS CODE
        response.
        then().//assertion için then() kullanıyourz
            assertThat().//asserThat=hard assertion(yani 1 hatada test fail olur )
                // - assertThat()'den sonra farklı seyleri assert edebiliriz
                statusCode(200).// statusCode assertion
                contentType("application/json; charset=utf-8");//content-type assertion

    }

    /*
    API 1. Ders Tekrar
1)API: Application Programming Interface
2)API’larda UI (User Interface) yoktur
3)API’lar bir application’in baska bir application ile iletisim kurmasini saglar
4)Bu iletisimi kurarken “Client” dan “Request”’i alir, “request” uygun ise “Datatbase/Server” a ulastirir ve Server’dan aldigi “Response”’i “Client”’a ulastirir.
5)API bir “Middle Man” dir.
6)API Tester’lar API’in istenen fonksiyonlari, istenen sekilde, istenen surede
yerine getirip getirmedigini kontrol eder.
7)     ......./booking ==> Endpoint(URL)
       ......./booking/3 ==> Endpoint(URL)
Biz tester olarak calismaya basladigimizda bize API’in tum Endpoint’lerini  ve bu Endpoint’lerden ne beklendigini gosterecekler, siz bu beklentileri test edeceksiniz.
8)API’in sahip oldugu tum Endpoint’leri ve bu Endpoint’lerden beklentileri gosteren dokumanlar var. En meshuru “SWAGGER” dir. Bu yuzden “SWAGGER” dokuman’dan aldigim direktiflere gore test yaparim.
9)Endpoint’ler ve Swagger dokumani API Developer’lar tarafindan hazirlanir.
10)GET ==> Server’dan data’yi ceker (Select gibi)
   POST ==> Yeni bir data olusturmaya yarar (Insert into gibi)
   PUT ==> Varolan data’yi update etmeye yarar (Update set gibi)
   PATCH ==> Varolan data’yi kismi update etmeye yarar (Update set gibi)
   DELETE ==> Varolan data’yi silmeye yarar (Delete from gibi)
11) API Develeoper’lar hangi method icin Authorization gerekir demislerse
    o methodu kullanirken Authorization’i eklemek gerekir.
    GET icin; sadece Endpoint (URL) lazim
    POST icin; 1)Endpoint
               2)“raw” secili degilse secin
               3)“JSON” format kullaniyorsaniz dropdown menuden “JSON” secili olmali
               4)Request Body
    PUT icin;  1)Endpoint
               2)“raw” secili degilse secin
               3)“JSON” format kullaniyorsaniz dropdown menuden “JSON” secili olmali
               4)Request Body’nin tamami
    PATCH icin;1)Endpoint
               2)“raw” secili degilse secin
               3)“JSON” format kullaniyorsaniz dropdown menuden “JSON” secili olmali
               4)Request Body’nin degistirilmek istenen kismi lazim
    DELETE icin; sadece Endpoint (URL) lazim

    12) JSON formatinda verilen data icinden istedigimiz data’yi cekmek icin asagidaki kurallar kulanilir:
	a) “$” ==> Tum data’yi verir
	b) “.” ==> Iceri girmek icin kullanilir
	c) “[2]” ==> Index’i 2 olan data’yi verir
	d) “[*]” ==> Tum indexlere gore data’yi veirir
	e) “[2, 5]” ==> Index’i 2 ve 5 olan data’lari verir
	f) “[-1]” ==> Son elemani verir
	Alistirma yapmak icin https://jsonpath.herokuapp.com/ kullanilabilir


	13)Iki cesit parametre var:
     a)Query(Request) Param
					i)“?” Endpoint’in bittigi anlamina gelir
					ii)“?” den sonra key-value yapisi kullanilir
					iii)Datalari filtrelemek icin Query Param kullanilir
					iv)Query Param’lar API Developer’lar tarafindan olusturulur.
     b)Path Param
          i)“/” kullanilir
          ii)Ustunde calistigimiz data resource’unu daraltmaya yarar.
     */
}
