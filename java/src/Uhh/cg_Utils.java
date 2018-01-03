package Uhh;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class cg_Utils {
  public static final Number bigWeight = 8000L;
  public static final Number smallWeight = 4000L;

  public cg_Utils() {}

  public static Boolean stringsEquals(final String s1, final String s2) {

    return Utils.equals(s1, s2);
  }

  public String toString() {

    return "cg_Utils{"
        + "bigWeight = "
        + Utils.toString(bigWeight)
        + ", smallWeight = "
        + Utils.toString(smallWeight)
        + "}";
  }
}
