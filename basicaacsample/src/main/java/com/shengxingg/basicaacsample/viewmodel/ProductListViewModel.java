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

package com.shengxingg.basicaacsample.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import com.shengxingg.basicaacsample.BasicApp;
import com.shengxingg.basicaacsample.db.entity.ProductEntity;
import java.util.List;

/**
 * Fun:
 * Created by sxx.xu on 4/12/2018.
 */
public class ProductListViewModel extends AndroidViewModel {

  // 该实例可以监听别的LiveData对象,并且对(状态的)发射做出反应.
  private final MediatorLiveData<List<ProductEntity>> mObservableProducts;

  public ProductListViewModel(@NonNull Application application) {
    super(application);
    mObservableProducts = new MediatorLiveData<>();

    // 设置为空,直到我们从数据库中取出数据
    mObservableProducts.setValue(null);
    LiveData<List<ProductEntity>> products = ((BasicApp) application).getRepository().getProducts();

    // java 8 的双冒号
    mObservableProducts.addSource(products, mObservableProducts::setValue);
  }

  public LiveData<List<ProductEntity>> getProducts() {
    return mObservableProducts;
  }
}
