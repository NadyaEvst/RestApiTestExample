public class OpenWeatherApiConfig {
  final static String APPID = "&APPID=e87d9935a4d97a763869d9e576429c62";
  final static String OPENWEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?";

  public static String getWeatherByCityUrl(String city) {
    return OPENWEATHER_URL + city + APPID;
  }

  public static String getWeatherByCityAndCountryUrl(String city, String country) {
    return OPENWEATHER_URL + city + "," + country + APPID;
  }

  public static String getWeatherByCityIdUrl(String cityId) {
    return OPENWEATHER_URL + cityId + APPID;
  }

  public static String getEndPointWithParameter(String paramName,String paramValue){
    return OPENWEATHER_URL + paramName +"="+paramValue+ APPID;
  }
}
