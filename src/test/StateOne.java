package test;

import core.GameContainer;
import core.GameState;
import core.StateBasedGame;

public class StateOne extends GameState {

	public StateOne(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
		addEntity(new EntityOne("one"));
		// addEntity(new EntityTwo("two"));

		super.init(gameContainer, stateBasedGame);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void render() {

	}
}
