package api.kuCoinApi;

import java.util.Comparator;

public class TickerComparator implements Comparator<TikerData> {
    @Override
    public int compare(TikerData o1, TikerData o2) {
        float result = Float.compare(Float.parseFloat(o1.getChangeRate()), Float.parseFloat(o2.getChangeRate()));
        return (int)result;
    }
}
