package study.itmo.xpech.parserlib.exceptions;

public class ParsingException extends Exception {
  public ParsingException(final String message) {
    super(message);
  }

  static protected String emphasize(int index, int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < index; i++) {
      sb.append(' ');
    }
    sb.append('^');
    for (int i = 0; i < length - 1; i++) {
      sb.append('~');
    }
    return sb.toString();
  }
}
