package core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import util.Log;
import static util.DebugLevel.*;

import junit.framework.Assert;

public abstract class StateBasedApp extends App {

	protected Map<Integer, AppState> idToStateMap = new HashMap<Integer, AppState>();
	public static final int reservedId = 1337;

	public StateBasedApp(String title) {
		super(title);

		appState = new AppState(reservedId) {
			@Override
			public void render() {
			}

			@Override
			public void input() {
			}
		};
	}

	@Override
	public void init(AppContainer appContainer) {
		this.appContainer = appContainer;
		initResources();
		initStates();

		Log.println(LOW_DEBUG, toString() + " Initialization Complete");
	}

	public abstract void initResources();

	public abstract void initStates();

	@Override
	public void destroy() {
		for (Entry<Integer, AppState> idToGameEntry : idToStateMap.entrySet()) {
			idToGameEntry.getValue().destroy();
		}

		Log.println(LOW_DEBUG, toString() + " Destruction Complete");
	}

	@Override
	public void update(int delta) {
		appState.update(delta);
		appState.render();
	}

	@Override
	public void input() {
		appState.input();
	}

	public int getCurrentStateID() {
		return appState.getID();
	}

	public void addState(AppState appState) {
		Assert.assertNotNull(appState);

		idToStateMap.put(new Integer(appState.getID()), appState);
		if (this.appState.getID() == reservedId && appState.getID() != reservedId) {
			this.appState = appState;
			this.appState.init(appContainer, this);
		}
		Log.println(LOW_DEBUG, appState.toString() + " added successfully");
	}

	public boolean removeState(int id) {
		AppState appState = idToStateMap.get(new Integer(id));

		if (id != appState.getID() && appState != null) {
			idToStateMap.remove(appState);
			Log.println(LOW_DEBUG, appState.toString() + " removed successfully");
			return true;
		}

		Log.println(LOW_DEBUG, appState.toString() + " could not be removed");
		return false;
	}

	public boolean enterState(int id) {
		if (idToStateMap.containsKey(new Integer(id)) && id != appState.getID()) {
			switchState(id);
			return true;
		}
		return false;
	}

	public boolean enterNextState() {
		int nextState = appState.getID() + 1;
		if (idToStateMap.containsKey(new Integer(nextState))) {
			switchState(nextState);
			return true;
		}
		return false;
	}

	public boolean enterPreviousState() {
		int nextState = appState.getID() - 1;
		if (idToStateMap.containsKey(new Integer(nextState))) {
			switchState(nextState);
			return true;
		}
		return false;
	}

	private void switchState(int id) {
		appState.destroy();
		appState = idToStateMap.get(id);
		appState.init(appContainer, this);

		Log.println(LOW_DEBUG, appState.toString() + " was switched to successfully");
	}

}
