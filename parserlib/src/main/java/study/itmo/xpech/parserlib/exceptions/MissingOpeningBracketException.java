package study.itmo.xpech.parserlib.exceptions;

public class MissingOpeningBracketException extends ParsingException {
  public MissingOpeningBracketException(String exp, int index) {
    super("Missing opening bracket, odd closing bracket at: " + index + '\n' + exp + '\n' + emphasize(index, 0));
  }
}
