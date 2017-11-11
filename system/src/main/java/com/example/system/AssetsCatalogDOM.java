package com.example.system;

import com.example.assets.entity.AssetsCatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.format.SignStyle;
import java.util.List;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

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

    public static void main(String[] args) {
       DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendValue(MONTH_OF_YEAR, 2)
                .appendValue(DAY_OF_MONTH, 2)
                .appendValue(HOUR_OF_DAY,2).toFormatter();

        System.out.println(LocalDateTime.parse("2014032805", formatter));
        System.out.println(LocalDateTime.parse("201409181011".substring(0,10),formatter));
        System.out.println(LocalDateTime.now().getHour());
        System.out.println(LocalDateTime.now());

        LocalDateTime arrivalDate = LocalDateTime.now();
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
            String landing = arrivalDate.format(format);
            System.out.printf("Arriving at : %s %n", landing);
            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyyMMddhh");
            System.out.println(arrivalDate.format(format1));
        } catch (DateTimeException ex) {
            System.out.printf("%s can't be formatted!%n", arrivalDate);
            ex.printStackTrace();
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddhh");
        LocalDateTime preLocalDateTime = LocalDateTime.parse("2014032805", formatter);

        String nowTimeString = arrivalDate.format(format);
        LocalDateTime nowLocalDateTime = LocalDateTime.parse(nowTimeString, formatter);

        System.out.println(preLocalDateTime.isAfter(nowLocalDateTime));

        System.out.println(nowLocalDateTime);

        String str =  "status.loginLog.loginFlag";
        System.out.println(str.substring(str.indexOf(".")+1));
    }
}
