package study.itmo.xpech.parserlib.exceptions;

public class MissingOperandException extends ParsingException {
  public MissingOperandException(int index) {
    super(String.format("Missing operand at position %s", index));
  }
}
