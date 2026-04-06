/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.guidbase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author gg
 */
public class GuidbaseContext {

  private final GuidbaseContainer page;

  public static GuidbaseContext from(InputStream in) throws IOException {
    io.doublegsoft.guidbase.GuidbaseLexer lexer = new io.doublegsoft.guidbase.GuidbaseLexer(CharStreams.fromStream(in));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    io.doublegsoft.guidbase.GuidbaseParser parser = new io.doublegsoft.guidbase.GuidbaseParser(tokens);

    io.doublegsoft.guidbase.GuidbaseParser.Guidbase_viewContext ctxView = parser.guidbase_view();
    GuidbaseContainer page = new GuidbaseContainer();
    container(page, ctxView.guidbase_container_decl());
    return new GuidbaseContext(page);
  }
  
  /**
   * Parses the model definitions and gets guic context objects.
   * 
   * @param model
   *      the model definitions for guic
   * 
   * @return the parsed guic context objects
   * 
   * @throws IOException 
   *      in case of any IO errors
   * 
   * @since 2.0
   */
  public static List<GuidbaseContext> from(String model) throws IOException {
    List<GuidbaseContext> retVal = new ArrayList<>();
    io.doublegsoft.guidbase.GuidbaseLexer lexer = new io.doublegsoft.guidbase.GuidbaseLexer(CharStreams.fromString(model));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    io.doublegsoft.guidbase.GuidbaseParser parser = new io.doublegsoft.guidbase.GuidbaseParser(tokens);

    io.doublegsoft.guidbase.GuidbaseParser.Guidbase_pagesContext ctxPages = parser.guidbase_pages();
    for (io.doublegsoft.guidbase.GuidbaseParser.Guidbase_container_declContext ctxContainerDecl : ctxPages.guidbase_container_decl()) {
      GuidbaseContainer page = new GuidbaseContainer();
      container(page, ctxContainerDecl);
      retVal.add(new GuidbaseContext(page));
    }
    return retVal;
  }

  private static void container(GuidbaseWidget widget, io.doublegsoft.guidbase.GuidbaseParser.Guidbase_container_declContext ctxContainerDecl) {
    if (ctxContainerDecl == null) {
      return;
    }
    // id and type
    if (ctxContainerDecl.guidbase_id() != null) {
      widget.id(str(ctxContainerDecl.guidbase_id()));
    }
    widget.type(str(ctxContainerDecl.guidbase_container()));
    // attrs
    attrs(widget, ctxContainerDecl.guidbase_attrs_decl());
    // process
    if (ctxContainerDecl.process != null) {
      widget.process(ctxContainerDecl.process.getText());
    }
    for (io.doublegsoft.guidbase.GuidbaseParser.Guidbase_viewContext ctxView : ctxContainerDecl.guidbase_view()) {
      GuidbaseWidget child = null;
      if (ctxView.guidbase_container_decl() != null) {
        child = new GuidbaseContainer();
        child.container((GuidbaseContainer) widget);
        container(child, ctxView.guidbase_container_decl());
      } else {
        child = new GuidbaseWidget();
        child.container((GuidbaseContainer) widget);
        widget(child, ctxView.guidbase_widget_decl());
      }
    }
  }

  private static void widget(GuidbaseWidget widget, io.doublegsoft.guidbase.GuidbaseParser.Guidbase_widget_declContext ctxWidgetDecl) {
    if (ctxWidgetDecl == null) {
      return;
    }
    // id and type
    if (ctxWidgetDecl.guidbase_id() != null) {
      widget.id(str(ctxWidgetDecl.guidbase_id()));
    }
    if (ctxWidgetDecl.guidbase_widget() != null) {
      widget.type(str(ctxWidgetDecl.guidbase_widget()));
    }

    if (widget.type() != null && widget.type().equals("tile")) {

    }
    // attributes
    attrs(widget, ctxWidgetDecl.guidbase_attrs_decl());
    //
    if (ctxWidgetDecl.required != null) {
      widget.attr("required", "true");
    }
    if (ctxWidgetDecl.identifier != null) {
      widget.attr("identifier", "true");
    }
    // process
    if (ctxWidgetDecl.process != null) {
      widget.process(ctxWidgetDecl.process.getText());
    }
  }

  private static void attrs(GuidbaseWidget widget, io.doublegsoft.guidbase.GuidbaseParser.Guidbase_attrs_declContext ctxAttrsDecl) {
    if (ctxAttrsDecl == null || ctxAttrsDecl.guidbase_attrs() == null) {
      return;
    }
    io.doublegsoft.guidbase.GuidbaseParser.Guidbase_attrsContext ctxAttrs = ctxAttrsDecl.guidbase_attrs();
    ctxAttrs.guidbase_attr().stream().forEach((ctxAttr) -> {
      String name = str(ctxAttr.guidbase_id());
      String value = str(ctxAttr.GUIDBASE_QUOTED_STRING());
      if (value != null) {
        value = value.substring(1, value.length() - 1);
      }
      if (name != null) {
        widget.attr(name, value);
      }
    });
  }

  private static String str(TerminalNode node) {
    if (node == null) {
      return null;
    }
    return node.getText();
  }

  private static String str(ParserRuleContext ctx) {
    if (ctx == null) {
      return null;
    }
    return ctx.getText();
  }

  public GuidbaseContainer page() {
    return page;
  }

  private GuidbaseContext(GuidbaseContainer page) {
    this.page = page;
  }
}
