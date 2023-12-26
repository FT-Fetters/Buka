package xyz.ldqc.buka.data.repository.script.token;

/**
 * @author Fetters
 */
public class Token {

  private final TokenType type;
  private final String value;

  public Token(TokenType type, String value){
    this.type = type;
    this.value = value;
  }

  public Token(TokenType type, char value){
    this.type = type;
    this.value = String.valueOf(value);
  }


  @Override
  public String toString() {
    return value;
  }
}
