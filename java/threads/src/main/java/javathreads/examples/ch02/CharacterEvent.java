package javathreads.examples.ch02;

public class CharacterEvent {
  public CharacterSource source;
  public int character;

  public CharacterEvent(CharacterSource cs, int c) {
    this.source = cs;
    this.character = c;
  }
}
