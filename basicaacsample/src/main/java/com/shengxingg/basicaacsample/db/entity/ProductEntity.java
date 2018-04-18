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

package com.shengxingg.basicaacsample.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.shengxingg.basicaacsample.model.Product;

/**
 * Fun:
 * Created by sxx.xu on 4/12/2018.
 */

//@Entity(tableName = "products")

public class ProductEntity implements Product {

  @PrimaryKey private int id;
  private String name;
  private String description;
  private int price;

  public ProductEntity() {
  }

  public ProductEntity(int id, String name, String description, int price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public ProductEntity(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
  }

  @Override public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
