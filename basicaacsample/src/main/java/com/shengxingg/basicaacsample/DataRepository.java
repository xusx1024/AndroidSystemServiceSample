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

package com.shengxingg.basicaacsample;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import com.shengxingg.basicaacsample.db.AppDatabase;
import com.shengxingg.basicaacsample.db.entity.CommentEntity;
import com.shengxingg.basicaacsample.db.entity.ProductEntity;
import java.util.List;

/**
 * Fun:
 * Created by sxx.xu on 4/12/2018.
 */

public class DataRepository {
  private static DataRepository sInstance;

  private final AppDatabase mDatabase;

  private MediatorLiveData<List<ProductEntity>> mObservableProducts;

  private DataRepository(AppDatabase database) {
    mDatabase = database;
    mObservableProducts = new MediatorLiveData<>();
    mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(), productEntities -> {
      if (mDatabase.getDatabaseCreated().getValue() != null) {
        mObservableProducts.postValue(productEntities);
      }
    });
  }

  public static DataRepository getInstance(final AppDatabase database) {
    if (sInstance == null) {
      synchronized (DataRepository.class) {
        if (sInstance == null) {
          sInstance = new DataRepository(database);
        }
      }
    }
    return sInstance;
  }

  public LiveData<List<ProductEntity>> getProducts() {
    return mObservableProducts;
  }

  public LiveData<ProductEntity> loadProduct(final int productId) {
    return mDatabase.productDao().loadProduct(productId);
  }

  public LiveData<List<CommentEntity>> loadComment(final int productId) {
    return mDatabase.commentDao().loadComments(productId);
  }
}
