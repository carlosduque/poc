package javathreads.examples.ch02;

public interface CharacterSource {
  void addCharacterListener(CharacterListener cl);
  void removeCharacterListener(CharacterListener cl);
  void nextCharacter();
}
