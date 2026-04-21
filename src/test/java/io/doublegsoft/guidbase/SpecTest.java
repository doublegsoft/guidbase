package io.doublegsoft.guidbase;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;

public class SpecTest {

  @Test
  public void editable_form_with_groups() throws Exception {
    GuidbaseContext ctx = SyntaxTest.parse(getClass().getResourceAsStream("/page/editable_form_with_groups"));
    GuidbaseContainer page = ctx.page();
    Assert.assertEquals("edit", page.id());
  }

  @Test
  public void pagination_table() throws Exception {
    GuidbaseContext ctx = SyntaxTest.parse(getClass().getResourceAsStream("/page/pagination_table"));
    GuidbaseContainer page = ctx.page();
    Assert.assertEquals("list", page.id());
  }

  @Test
  public void data_analysis_report() throws Exception {
    GuidbaseContext ctx = SyntaxTest.parse(getClass().getResourceAsStream("/page/data_analysis_report"));
    GuidbaseContainer page = ctx.page();
  }

}
