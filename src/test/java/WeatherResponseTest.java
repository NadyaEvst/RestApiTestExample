import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class WeatherResponseTest {
  private static ResponseSpecification responseSpec;
  private static RequestSpecification requestSpec;

  @DataProvider(name="validParams")
  public Object[][] testValidData() {
    return new Object[][]{
            {"q", "Warsaw", "PL"},
            {"id", "2892705", "DE"},
            {"q", "Warsaw,PL", "PL"},
            {"q", "Moscow", "RU"},
            {"id", "529334", "RU"},
            {"zip","94040,us","US"}
    };
  }
    @DataProvider(name="invalidParams")
    public Object[][] testInvalidData(){
      return new Object[][]{
              {"0","400","Invalid ID"},
              {"01","404","city not found"},
              {"94040,us","400","94040,us is not a city ID"}
      };
  }

  @BeforeMethod
  public static void createSpecification(){
    responseSpec = new ResponseSpecBuilder().
      expectStatusCode(200).
      expectContentType(ContentType.JSON).
      build();
    requestSpec = new RequestSpecBuilder().
      setBaseUri(OpenWeatherApiConfig.OPENWEATHER_URL).
      build();
  }

  @Test(dataProvider = "validParams")
  public void validParametersTest(String paramName, String paramValue, String expectedCountry){
    given().
            spec(requestSpec).
            queryParam(paramName,paramValue).
            queryParam("APPID", OpenWeatherApiConfig.APPID).
            log().all().
            when().
            get().
            then().
            spec(responseSpec).
            and().assertThat().body("sys.country",is(expectedCountry));
  }

  @Test(dataProvider = "invalidParams")
  public void invalidIdsTest(String id,String code, String message){
    given().
            spec(requestSpec).
            queryParam("id",id).
            queryParam("APPID", OpenWeatherApiConfig.APPID).
            log().all().
            when().
            get().
            then().
            assertThat().body("cod",is(code)).
            assertThat().body("message",is(message));
  }

}
