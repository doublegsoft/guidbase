/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.guidbase;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author gg
 */
public class GuidbaseWidget {

  /**
   * the identifier for this widget, may be null.
   */
  protected String id;

  protected String type;
  
  protected String process;

  protected GuidbaseContainer container;

  protected final Set<GuidbaseAttr> attrs = new HashSet<>();

  public String id() {
    return id;
  }

  public String type() {
    return type;
  }
  
  public String process() {
    return process;
  }

  public GuidbaseContainer container() {
    return container;
  }

  public Set<GuidbaseAttr> attrs() {
    return Collections.unmodifiableSet(attrs);
  }

  public String attr(String name) {
    for (GuidbaseAttr attr : attrs) {
      if (attr.name().equals(name)) {
        return attr.value();
      }
    }
    return null;
  }

  public boolean primitive() {
    return !(this instanceof GuidbaseContainer);
  }

  GuidbaseWidget id(String id) {
    this.id = id;
    return this;
  }

  GuidbaseWidget type(String type) {
    this.type = type;
    return this;
  }
  
  GuidbaseWidget process(String process) {
    this.process = process;
    return this;
  }

  GuidbaseWidget container(GuidbaseContainer container) {
    if (this.container != null) {
      this.container.children().remove(this);
    }
    this.container = container;
    container.add(this);
    return this;
  }

  GuidbaseWidget attr(String name, String value) {
    GuidbaseAttr attr = new GuidbaseAttr().name(name).value(value);
    attrs.add(attr);
    return this;
  }
}
