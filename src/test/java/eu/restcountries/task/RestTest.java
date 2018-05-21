package eu.restcountries.task;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


/**
 * Created by massacre99 on 09.05.2018.
 */
public class RestTest {

    String apiUrl = "http://restcountries.eu/rest/v1/";


    @Test
    public void test3() {

        get(apiUrl).
                then().assertThat().
                statusCode(200).
                and().contentType(ContentType.JSON).
                and().body("name", hasItem("Latvia")).
                and().body("find { d -> d.name == 'Latvia' }.borders", hasItem("EST"));
    }

    @Test
    public void test4() {

        get(apiUrl).
                then().assertThat().
                statusCode(200).
                and().contentType(ContentType.JSON).
                and().body("name", hasItem("Ukraine")).
                and().body("find { d -> d.name == 'Ukraine' }.area", greaterThan(500000.0f));


        String name = returnValueByKeys("Ukraine", "name");
        String capital = returnValueByKeys("Ukraine", "capital");
        String region = returnValueByKeys("Ukraine", "region");
        String population = returnValueByKeys("Ukraine", "population");
        List borders = returnValuesByKeys("Ukraine", "borders");

        System.out.println(name + " " + capital + " " + region + " " + population);
        for (Object border : borders) {
            System.out.print(border + " ");
        }

    }


    private String returnValueByKeys(String primary, String secondary) {
        String path = String.format("find { d -> d.name == '%s' }.%s", primary, secondary);
        Object response = given().contentType(ContentType.JSON)
                .when()
                .get(apiUrl)
                .then()
                .extract().response().body().path(path);
        return String.valueOf(response);
    }

    private ArrayList<String> returnValuesByKeys(String primary, String secondary) {
        String path = String.format("find { d -> d.name == '%s' }.%s", primary, secondary);
        ArrayList<String> response = given().contentType(ContentType.JSON)
                .when()
                .get(apiUrl)
                .then()
                .extract().response().body().path(path);
        return response;
    }
}
