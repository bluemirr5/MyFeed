package com.nnggstory.feedfactory.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserDataModel {
  public String id = null;
  public String password = null;
  public String email = null;
  public String nickName = null;
}