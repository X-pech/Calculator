package study.itmo.xpech.parserlib.exceptions;

public class MissingOperationException extends ParsingException {
  public MissingOperationException(String exp, int index) {
    super("Missing parsingType at position: " + index + '\n' + exp + '\n' + emphasize(index, 1));
  }
}
