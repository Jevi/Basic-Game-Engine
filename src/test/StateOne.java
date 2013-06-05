package test;

import org.lwjgl.input.Keyboard;

import core.Entity;
import core.GameContainer;
import core.GameState;
import core.StateBasedGame;

public class StateOne extends GameState {

	public StateOne(int id, float ygravity, float xgravity) {
		super(id, ygravity, xgravity);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {

		Entity test = new EntityOne("Test");
		addEntity(test);
		super.init(gameContainer, stateBasedGame);
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
			stateBasedGame.enterNextGameState();
		}
	}

	@Override
	public void render() {
	}
}
