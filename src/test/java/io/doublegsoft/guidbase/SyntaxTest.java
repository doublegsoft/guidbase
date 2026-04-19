/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.guidbase;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author gg
 */
public class SyntaxTest {

  public static GuidbaseContext parse(InputStream in) throws Exception {
    return GuidbaseContext.from(in);
  }

  @Test
  public void syntax_1() throws Exception {
    GuidbaseContext ctx = parse(getClass().getResourceAsStream("/syntax/1"));
    GuidbaseContainer page = ctx.page();
    Assert.assertEquals("index", page.id());
    Assert.assertEquals(3, page.children().size());

    // query
    GuidbaseContainer query = (GuidbaseContainer) page.children().get(0);
    Assert.assertEquals("qry_lev", query.id());
    Assert.assertEquals("query", query.type());
    Assert.assertEquals(3, query.children().size());
    Assert.assertEquals("addvcd", query.children().get(0).id());
    Assert.assertEquals("addv", query.children().get(0).type());
    Assert.assertEquals("行政区划", query.children().get(0).attr("text"));
    // buttons
    GuidbaseContainer buttons = (GuidbaseContainer) page.children().get(1);
    Assert.assertEquals(2, buttons.children().size());
    Assert.assertEquals("查询", buttons.children().get(0).attr("text"));
    Assert.assertEquals("新建", buttons.children().get(1).attr("text"));

    //pagination-table
    GuidbaseContainer paginationTable = (GuidbaseContainer) page.children().get(2);
    Assert.assertEquals("grd_lev", paginationTable.id());
    Assert.assertEquals("pagination-table", paginationTable.type());
    Assert.assertEquals(4, paginationTable.children().size());
    Assert.assertEquals("lvnm", paginationTable.children().get(0).id());
    Assert.assertEquals("label", paginationTable.children().get(0).type());
    Assert.assertEquals("堤防名称", paginationTable.children().get(0).attr("text"));

    GuidbaseContainer buttonsInPaginationTable = (GuidbaseContainer) paginationTable.children().get(3);
    Assert.assertEquals("buttons", buttonsInPaginationTable.type());
    Assert.assertEquals(4, buttonsInPaginationTable.children().get(0).attrs().size());
    Assert.assertEquals("编辑", buttonsInPaginationTable.children().get(0).attr("text"));
  }

  @Test
  public void syntax_2() throws Exception {
    GuidbaseContext ctx = parse(getClass().getResourceAsStream("/syntax/2"));
    Assert.assertEquals("edit", ctx.page().id());
  }

  /**
   * @since 2.0
   */
  @Test
  public void syntax_3() throws Exception {
    List<GuidbaseContext> ctxs = GuidbaseContext.from(new String(Files.readAllBytes(new File("src/test/resources/syntax/3").toPath())));
    Assert.assertEquals(2, ctxs.size());
  }

  @Test
  public void syntax_4() throws Exception {
    List<GuidbaseContext> ctxs = GuidbaseContext.from(new String(Files.readAllBytes(new File("src/test/resources/syntax/4").toPath())));
    GuidbaseContext ctx = ctxs.get(0);
    Assert.assertEquals("home/user/mine", ctx.page().id());
  }

  @Test
  public void systax_mini_1() throws Exception {
    GuidbaseContainer page = GuidbaseMiniContext.from("list{calendar+list_view%edit}");
    Assert.assertEquals("页面的直接子部件", 2, page.children().size());
  }

  @Test
  public void systax_mini_2() throws Exception {
    String s = "index{[list_navigator(anthropometric_measurement).1 + list_view(routine_measurement)]+list_navigator(cardiovascular_measurement)}";
    GuidbaseContainer page = GuidbaseMiniContext.from(s);
    Assert.assertEquals("页面的直接子部件", 2, page.children().size());
  }

  @Test
  public void systax_mini_3() throws Exception {
    String s = "index{[{list_navigator(anthropometric_measurement) + list_navigator(cardiovascular_measurement)}.8 + list_view(routine_measurement).16]}";
    GuidbaseContainer page = GuidbaseMiniContext.from(s);
    Assert.assertEquals("页面的直接子部件", 1, page.children().size());
  }

}
