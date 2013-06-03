package com.nnggstory.feedfactory.model;

import org.springframework.data.mongodb.core.mapping.Document;

public class UserDataModel {
  public @Document String id = null;
  public @Document String email = null;
  public @Document String nickName = null;
}