package cracker.ui;

import cracker.logic.Character;
import javafx.scene.image.Image;

public class CharacterView {
	private Character character;
	private Image image;

	public CharacterView(Character character, Image image) {
		this.character = character;
		this.image = image;
	}

	public Character getCharacter() {
		return character;
	}

	public Image getImage() {
		return image;
	}
}
