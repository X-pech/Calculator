package study.itmo.xpech.parserlib.exceptions;

public class ParsingOverflowException extends ParsingException {
  public ParsingOverflowException(String exp, int index) {
    super(String.format("Parsed constant causes type overflow at position %s", index));
  }
}
