/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.guidbase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author gg
 */
public class GuidbaseContainer extends GuidbaseWidget {

  final List<GuidbaseWidget> children = new ArrayList<>();

  public List<GuidbaseWidget> children() {
    return Collections.unmodifiableList(children);
  }

  GuidbaseContainer add(GuidbaseWidget child) {
    children.add(child);
    return this;
  }

  GuidbaseContainer() {

  }

}
