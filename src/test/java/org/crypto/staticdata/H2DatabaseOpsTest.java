package org.crypto.staticdata;

import org.crypto.domain.BaseSecurity;
import org.crypto.domain.Option;
import org.crypto.domain.Stock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.crypto.Constants.CALL_KEY;

public class H2DatabaseOpsTest {
    @Before
    public void setUpDB(){
        H2DatabaseOps.createDatabase();
    }

    @Test
    public void testGetStocks(){
        List<? super BaseSecurity> stocks = H2DatabaseOps.getStocks();
        Assert.assertEquals(2, stocks.size());
        stocks.forEach(stock -> {
            if(((Stock)stock).getTickerCode().equalsIgnoreCase("STOCK1")){
                Assert.assertEquals(100d, ((Stock)stock).getInitialPrice(), 0.00001);
                Assert.assertEquals(0.8d, ((Stock)stock).getMean(), 0.00001);
                Assert.assertEquals(0.6d, ((Stock)stock).getStdev(), 0.00001);
            }
        });
    }

    @Test
    public void testGetOptions(){
        List<? super BaseSecurity> options = H2DatabaseOps.getOptions(CALL_KEY);
        Assert.assertEquals(1, options.size());
        options.forEach(option -> {
            if(((Option)option).getTickerCode().equalsIgnoreCase("CALLOPTION1")){
                Assert.assertEquals(CALL_KEY, ((Option)option).getType());
                Assert.assertEquals(10d, ((Option)option).getMaturity(), 0.00001);
                Assert.assertEquals(65d, ((Option)option).getStrike(), 0.00001);
                Assert.assertEquals("STOCK1", ((Option)option).getUnderlyer());
            }
        });
    }
}
