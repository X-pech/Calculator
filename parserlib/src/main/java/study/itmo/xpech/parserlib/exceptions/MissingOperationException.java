package study.itmo.xpech.parserlib.exceptions;

public class MissingOperationException extends ParsingException {
  public MissingOperationException(String exp, int index) {
    super(String.format("Missing parsingType at position %s", index));
  }
}
