package org.crypto.staticdata;

import org.crypto.domain.SecurityPosition;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SecurityPositionLoaderServiceTest {

    SecurityPositionLoaderService securityPositionLoaderService;

    @Test
    public void testLoadPositionsFile(){
        Map<String, SecurityPosition> positions = new HashMap<>();
        securityPositionLoaderService = new SecurityPositionLoaderService(positions);
        securityPositionLoaderService.loadPositionsFromCsv();
        Assert.assertEquals(4, positions.size());
        SecurityPosition spStock1 = positions.get("STOCK1");
        Assert.assertEquals(180, spStock1.getQuantity().intValue());
        SecurityPosition spStock2 = positions.get("STOCK2");
        Assert.assertEquals(280, spStock2.getQuantity().intValue());
        SecurityPosition callOption1 = positions.get("CALLOPTION1");
        Assert.assertEquals(95, callOption1.getQuantity().intValue());
        SecurityPosition putOption1 = positions.get("PUTOPTION1");
        Assert.assertEquals(70, putOption1.getQuantity().intValue());
    }
}
