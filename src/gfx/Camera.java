package gfx;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

public class Camera {

	private float x = Display.getWidth() / 2;
	private float y = Display.getHeight() / 2;
	private float startx = Display.getWidth() / 2;
	private float starty = Display.getHeight() / 2;

	private float angle = 0;

	public Camera() {
	}

	public void translate(float x, float y) {
		glMatrixMode(GL_MATRIX_MODE);
		glLoadIdentity();
		glTranslatef(-x + (Display.getWidth() / 2), -y + (Display.getHeight() / 2), 0);

		this.x = x;
		this.y = y;
	}

	public void rotate(float angle) {
		this.angle = angle;
	}

	public void reset() {
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		x = startx;
		y = starty;

		angle = 0;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getAngle() {
		return angle;
	}
}
