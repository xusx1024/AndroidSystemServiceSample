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
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import com.shengxingg.basicaacsample.BasicApp;
import com.shengxingg.basicaacsample.DataRepository;
import com.shengxingg.basicaacsample.db.entity.CommentEntity;
import com.shengxingg.basicaacsample.db.entity.ProductEntity;
import java.util.List;

/**
 * Fun:
 * Created by sxx.xu on 4/13/2018.
 */

public class ProductViewModel extends AndroidViewModel {

  private final LiveData<ProductEntity> mObservableProduct;
  private final int mProductId;
  private final LiveData<List<CommentEntity>> mObservalbeComment;
  public ObservableField<ProductEntity> product = new ObservableField<>();

  public ProductViewModel(@NonNull Application application, DataRepository repository,
      final int productId) {
    super(application);
    mProductId = productId;

    mObservableProduct = repository.loadProduct(productId);
    mObservalbeComment = repository.loadComment(productId);
  }

  public LiveData<List<CommentEntity>> getComments() {
    return mObservalbeComment;
  }

  public LiveData<ProductEntity> getObservableProducts() {
    return mObservableProduct;
  }

  public void setProduct(ProductEntity product) {
    this.product.set(product);
  }

  public static class Factory extends ViewModelProvider.NewInstanceFactory {

    @NonNull private final Application mApplication;

    private final int mProductId;

    private final DataRepository mRepository;

    public Factory(@NonNull Application application, int productId) {
      mApplication = application;
      mProductId = productId;
      mRepository = ((BasicApp) application).getRepository();
    }

    @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
      return (T) new ProductViewModel(mApplication, mRepository, mProductId);
    }
  }
}
