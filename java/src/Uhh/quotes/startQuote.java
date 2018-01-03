package Uhh.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class startQuote {
  private static int hc = 0;
  private static startQuote instance = null;

  public startQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static startQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new startQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof startQuote;
  }

  public String toString() {

    return "<start>";
  }
}
