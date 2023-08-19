package api.kuCoinApi;

import io.restassured.http.ContentType;
import org.asynchttpclient.util.Assertions;
import org.junit.Test;
import org.testng.Assert;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class StreamTestAPIExample {

    List<TikerData> getAllTickers(){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://api.kucoin.com/api/v1/market/allTickers")
                .then()
//                .log().body()
                .extract().jsonPath().getList("data.ticker",TikerData.class);
    }

    @Test
    public void checkCrypto(){
        List<TikerData> usdTickers = getAllTickers().stream().filter(x->x.getSymbol().endsWith("USDT")).collect(Collectors.toList());
        Assert.assertTrue(usdTickers.stream().allMatch(x->x.getSymbol().endsWith("USDT")));
    }

    @Test
    public void sortHighToLow(){
        List<TikerData> hightToLow = getAllTickers().stream().filter(x->x.getSymbol().endsWith("USDT")).sorted(new Comparator<TikerData>() {
            @Override
            public int compare(TikerData o1, TikerData o2) {
                return o2.getChangeRate().compareTo(o1.getChangeRate());
            }
        }).collect(Collectors.toList());
        List<TikerData> top10 = hightToLow.stream().limit(10).collect(Collectors.toList());

        Assert.assertEquals(top10.get(0).getSymbol(), "BLZ-USDT");
    }
    @Test
    public void sortLowToHigh(){
//        List<TikerData> lowToHigh = getAllTickers().stream().filter(x->x.getSymbol().endsWith("USDT")).sorted(new Comparator<TikerData>() {
//            @Override
//            public int compare(TikerData o1, TikerData o2) {
//                return o1.getChangeRate().compareTo(o2.getChangeRate());
//            }
//        }).collect(Collectors.toList());
//
//        List<TikerData> top10 = lowToHigh.stream().limit(10).collect(Collectors.toList());
//Assert.assertEquals(top10.get(0).getSymbol(), "USDD-USDT");
        List<TikerData> lowToHigh = getAllTickers().stream().filter(x->x.getSymbol().endsWith("USDT")).sorted(new TickerComparator()).limit(10).collect(Collectors.toList());
        int t=1;
    }

    @Test
    public void map(){
        Map<String, Float> usd = new HashMap<>();
        List<String> tickersLowerCase = getAllTickers().stream().map(x->x.getSymbol().toLowerCase()).collect(Collectors.toList());
        getAllTickers().stream().forEach(x->usd.put(x.getSymbol(), Float.parseFloat(x.getChangeRate())));

        int i=3;
    }
}
