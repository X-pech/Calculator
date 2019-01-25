package study.itmo.xpech.parserlib.exceptions;

public class DivisionByZeroException extends ParsingException {
  public DivisionByZeroException()
  {
    super("Division by zero can cause brain cancer");
  }
}
