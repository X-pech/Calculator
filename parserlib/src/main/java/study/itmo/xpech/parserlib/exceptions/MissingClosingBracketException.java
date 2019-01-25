package study.itmo.xpech.parserlib.exceptions;

public class MissingClosingBracketException extends ParsingException {
  public MissingClosingBracketException(int balance) {
    super(String.format("Missing closing bracket, bracket balance is %s", balance));
  }
}
