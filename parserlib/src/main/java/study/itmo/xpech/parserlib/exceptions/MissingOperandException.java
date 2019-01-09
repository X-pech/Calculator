package study.itmo.xpech.parserlib.exceptions;

public class MissingOperandException extends ParsingException {
  public MissingOperandException(String exp, int index) {
    super("Missing operand at position: " + index + '\n' + exp + '\n' + emphasize(index, 1));
  }
}
