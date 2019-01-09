package study.itmo.xpech.parserlib.exceptions;

public class IncorrectSymbolException extends ParsingException {
  public IncorrectSymbolException(int index) {
    super(String.format("Incorrect char or string at position %s", index));
  }
}
