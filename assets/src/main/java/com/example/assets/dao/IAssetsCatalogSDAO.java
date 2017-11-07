package com.example.assets.dao;

import com.example.assets.entity.AssetsCatalog;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAssetsCatalogSDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(AssetsCatalog record);

    int insertSelective(AssetsCatalog record);

    List<AssetsCatalog> queryList();

    int updateByPrimaryKeySelective(AssetsCatalog record);

    int updateByPrimaryKey(AssetsCatalog record);
}