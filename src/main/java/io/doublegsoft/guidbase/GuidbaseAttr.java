/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.guidbase;

import java.util.Objects;

/**
 *
 * @author gg
 */
public class GuidbaseAttr {

  private String name;

  private String value;

  public String name() {
    return name;
  }

  public String value() {
    return value;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 41 * hash + Objects.hashCode(this.name);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final GuidbaseAttr other = (GuidbaseAttr) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }

  GuidbaseAttr name(String name) {
    this.name = name;
    return this;
  }

  GuidbaseAttr value(String value) {
    this.value = value;
    return this;
  }

  GuidbaseAttr() {

  }
}
