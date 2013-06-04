package test;

import core.Entity;
import core.GameContainer;
import core.GameState;
import core.StateBasedGame;

public class GameStateImpl extends GameState {

	public GameStateImpl(int id, float ygravity, float xgravity) {
		super(id, ygravity, xgravity);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {

		Entity test = new EntityImpl("Test");
		addEntity(test);
		super.init(gameContainer, stateBasedGame);
	}

	@Override
	public void render() {
	}
}
