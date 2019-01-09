package study.itmo.xpech.parserlib.exceptions;

public class IncorrectSymbolException extends ParsingException {
  public IncorrectSymbolException(String exp, int index) {
    super("Incorrect char or string at position: " + index + '\n' + exp + '\n' + emphasize(index, 0));
  }
}
