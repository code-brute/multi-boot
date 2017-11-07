package com.example.system;

import com.example.assets.entity.AssetsCatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author: mitnick
 * @date: 2017-11-06 下午7:18
 */
@Component

public class AssetsCatalogDOM {

    @Autowired
    AssetsCatalogMDAO assetsCatalogMDAO;

    @Cacheable(cacheNames = "GoodsType",key = "'catalog'")
    public List<AssetsCatalog> queryBean() {
            return assetsCatalogMDAO.queryList();
    }
}
