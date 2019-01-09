package study.itmo.xpech.parserlib.exceptions;

public class MissingClosingBracketException extends ParsingException {
  public MissingClosingBracketException(String exp, int balance) {
    super("Missing closing bracket, balance is: " + balance + '\n' + exp);
  }
}
