import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class WeatherResponseTest {
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
  @Test(dataProvider = "validParams")
  public void validParametersTest(String paramName, String paramValue, String expectedCountry){
    given().
            when().
            get(OpenWeatherApiConfig.getEndPointWithParameter(paramName,paramValue)).
            then().
            assertThat().statusCode(200).
            assertThat().body("sys.country",is(expectedCountry));
  }
  @Test(dataProvider = "invalidParams")
  public void invalidIdsTest(String id,String code, String message){
    given().
            when().
            get(OpenWeatherApiConfig.getEndPointWithParameter("id",id)).
            then().
            assertThat().body("cod",is(code)).
            assertThat().body("message",is(message));
  }

}
