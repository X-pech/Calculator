package study.itmo.xpech.parserlib.exceptions;

public class MissingOpeningBracketException extends ParsingException {
  public MissingOpeningBracketException(int index) {
    super(String.format("Missing opening bracket, odd closing bracket at position %s", index));
  }
}
