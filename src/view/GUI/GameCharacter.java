package view.GUI;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;
/**
 * class GameCharacter
 * @author Itamar Mizrahi & Chen Erlich
 */
public class GameCharacter {
	private Position pos;
	private Image img;
	
	public GameCharacter() {
		this.img = new Image(null, "resources/images/putin_head.png");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.getX(), cellHeight * pos.getY(), cellWidth, cellHeight);
	}
	
}