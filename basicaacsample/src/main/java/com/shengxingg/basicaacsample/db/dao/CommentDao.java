/*
 *  Copyright (C) 2017 The  sxxxxxxxxxu's  Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.shengxingg.basicaacsample.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.shengxingg.basicaacsample.db.entity.CommentEntity;
import java.util.List;

/**
 * Fun:
 * Created by sxx.xu on 4/12/2018.
 */
@Dao public interface CommentDao {
  @Query("SELECT * FROM comments where productId = :productId")
  LiveData<List<CommentEntity>> loadComments(int productId);

  @Query("SELECT * FROM comments where productId = :productId")
  List<CommentEntity> loadCommentsSync(int productId);

  @Insert(onConflict = OnConflictStrategy.REPLACE) void insertAll(List<CommentEntity> comments);
}
