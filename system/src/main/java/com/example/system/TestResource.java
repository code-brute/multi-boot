package com.example.system;

import com.example.assets.entity.AssetsCatalog;

import org.apache.catalina.startup.Catalina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * @author: mitnick
 * @date: 2017-11-06 下午5:35
 */
@RestController
@RequestMapping("/api")
public class TestResource {

    @Autowired
    private AssetsCatalogDOM assetsCatalogDOM;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void init() {
        System.out.println("system ==========");
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AssetsCatalog> test() {
        return assetsCatalogDOM.queryBean();
    }

    @RequestMapping(value = "/cache", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void getCache() {

        Cache cache = cacheManager.getCache("GoodsType");
        SimpleValueWrapper assetsCatalogs = (SimpleValueWrapper) cache.get("catalog");
        List<AssetsCatalog> assetsCatalogList = (List<AssetsCatalog>) assetsCatalogs.get();
        assetsCatalogList.stream().filter(assetsCatalog -> {
            if (assetsCatalog.getId().intValue() == 1010000) {
                System.out.println(assetsCatalog.getName());
                return true;
            } else {
                System.out.println("============");
                return false;
            }
        });
    }
}
