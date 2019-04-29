package com.shiep.fxbankcard.repository;

import com.shiep.fxbankcard.entity.FxAsset;
import com.shiep.fxbankcard.utils.UuidTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author: 倪明辉
 * @date: 2019/4/29 10:35
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FxAssetRepositoryTest {
    @Autowired
    private FxAssetRepository assetRepository;

    @Test
    public void updateBalance() {
        FxAsset asset = new FxAsset();
        asset.setId(UuidTools.getUUID());
        asset.setBankcardId("6216602900057522721");
        asset.setCurrencyCode("CNY");
        asset.setBalance(new BigDecimal(10000));
        asset.setVersion(0);
        assetRepository.save(asset);
        Integer result = assetRepository.updateBalance(new BigDecimal(5000), 0, "CNY", "6216602900057522721");
        assertEquals(new Integer(1), result);
    }
}