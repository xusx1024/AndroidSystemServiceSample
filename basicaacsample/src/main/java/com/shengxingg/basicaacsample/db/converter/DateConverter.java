package com.shengxingg.basicaacsample.db.converter;

import android.arch.persistence.room.TypeConverter;
import java.util.Date;

/**
 * Fun: 日期转换器
 *
 * 数据持久化,存入数据库用的.
 *
 * Created by sxx.xu on 4/12/2018.
 */
public class DateConverter {

  @TypeConverter public static Date toDate(Long timestamp) {
    return timestamp == null ? null : new Date(timestamp);
  }

  @TypeConverter public static Long toTimestamp(Date date) {
    return date == null ? null : date.getTime();
  }
}
