package Uhh;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Stack {
  public VDMSeq contents = SeqUtil.seq();

  public void cg_init_Stack_1() {

    return;
  }

  public Stack() {

    cg_init_Stack_1();
  }

  public void push(final Object x) {

    contents = SeqUtil.conc(SeqUtil.seq(x), Utils.copy(contents));
  }

  public void pop() {

    contents = SeqUtil.tail(Utils.copy(contents));
  }

  public Object top() {

    return ((Object) contents.get(0));
  }

  public String toString() {

    return "Stack{" + "contents := " + Utils.toString(contents) + "}";
  }
}
