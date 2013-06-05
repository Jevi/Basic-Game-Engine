package test;

import org.lwjgl.input.Keyboard;

import core.Entity;
import core.GameContainer;
import core.GameState;
import core.StateBasedGame;

public class StateTwo extends GameState {

	public StateTwo(int id, float ygravity, float xgravity) {
		super(id, ygravity, xgravity);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {

		Entity test = new EntityTwo("Test");
		addEntity(test);
		super.init(gameContainer, stateBasedGame);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
			stateBasedGame.enterPreviousGameState();
		}
	}

	@Override
	public void render() {
	}
}