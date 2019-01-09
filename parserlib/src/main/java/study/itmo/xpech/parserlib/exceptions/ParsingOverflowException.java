package study.itmo.xpech.parserlib.exceptions;

public class ParsingOverflowException extends ParsingException {
  public ParsingOverflowException(String exp, int index) {
    super("Parsed constant causes type overflow at position: " + index + '\n' + exp + '\n' + emphasize(index, 0));
  }
}
