package study.itmo.xpech.parserlib.exceptions;

public class DivisionByZeroException extends ParsingException {
  public DivisionByZeroException(String expression, int index)
  {
    super(String.format("Division by zero at position: %s \n %s \n %s", index, expression, emphasize(index, 0)));
  }
}
