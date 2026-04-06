package io.doublegsoft.guidbase;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import io.doublegsoft.guidbase.GuidbaseMiniParser.*;

import java.io.IOException;
import java.io.InputStream;

public class GuidbaseMiniContext {

  public static GuidbaseContainer from(String input) throws IOException {
    io.doublegsoft.guidbase.GuidbaseMiniLexer lexer = new io.doublegsoft.guidbase.GuidbaseMiniLexer(CharStreams.fromString(input));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    io.doublegsoft.guidbase.GuidbaseMiniParser parser = new io.doublegsoft.guidbase.GuidbaseMiniParser(tokens);

    Guidbase_mini_pageContext ctx = parser.guidbase_mini_page();
    GuidbaseContainer page = new GuidbaseContainer();
    assemblePage(page, ctx);
    return page;
  }

  private static void assemblePage(GuidbaseContainer page, Guidbase_mini_pageContext ctx) {
    page.id(ctx.id.getText()).type("page");
    if (ctx.guidbase_mini_col() == null) {
      return;
    }
    GuidbaseContainer col = new GuidbaseContainer();
    assembleCol(col, ctx.guidbase_mini_col());
    page.children.addAll(col.children());
  }

  private static void assembleWidget(GuidbaseWidget widget, Guidbase_mini_widgetContext ctx) {
    widget.type(ctx.guidbase_mini_id().get(0).getText());
    if (ctx.width != null) {
      widget.attr("width", ctx.width.getText());
    }
    if (ctx.guidbase_mini_object() != null) {
      widget.attr("object", ctx.guidbase_mini_object().guidbase_mini_id().getText());
    }
    if (ctx.guidbase_mini_goto() != null) {
      String anotherId = ctx.anotherId.getText();
      String symbolGoto = ctx.guidbase_mini_goto().getText();
      widget.attr("url", symbolGoto + anotherId);
//      GuidbaseWidget button = new GuidbaseWidget().type("button");
//      button.id("button_" + anotherId);
//      button.attr("url", symbolGoto + anotherId);
//      page.add(button);
    }
  }

  private static void assembleCol(GuidbaseContainer col, Guidbase_mini_colContext ctx) {
    col.type("col");
    if (ctx.width != null) {
      col.attr("width", ctx.width.getText());
    }
    Guidbase_mini_rowsContext ctxRows = ctx.guidbase_mini_rows();
    for (Guidbase_mini_rowContext ctxRow : ctxRows.guidbase_mini_row()) {
      if (ctxRow.guidbase_mini_section() != null) {
        GuidbaseContainer row = new GuidbaseContainer();
        row.type("row");
        assembleRow(row, ctxRow.guidbase_mini_section());
        col.add(row);
      } else {
        if (ctxRow.guidbase_mini_widget().guidbase_mini_col() == null) {
          GuidbaseWidget widget = new GuidbaseWidget();
          assembleWidget(widget, ctxRow.guidbase_mini_widget());
          col.add(widget);
        } else {
          GuidbaseContainer innerCol = new GuidbaseContainer();
          assembleCol(innerCol, ctxRow.guidbase_mini_widget().guidbase_mini_col());
          col.add(innerCol);
        }
      }
    }
  }

  private static void assembleRow(GuidbaseContainer row, Guidbase_mini_sectionContext ctx) {
    for (Guidbase_mini_widgetContext ctxWidget : ctx.guidbase_mini_widgets().guidbase_mini_widget()) {
      if (ctxWidget.guidbase_mini_col() == null) {
        GuidbaseWidget widget = new GuidbaseWidget();
        assembleWidget(widget, ctxWidget);
        row.add(widget);
      } else {
        GuidbaseContainer col = new GuidbaseContainer();
        assembleCol(col, ctxWidget.guidbase_mini_col());
        row.add(col);
      }
    }
  }

}
