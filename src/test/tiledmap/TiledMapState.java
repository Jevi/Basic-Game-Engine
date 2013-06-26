package test.tiledmap;

import gfx.JsonTiledMap;

import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;

import com.google.gson.JsonSyntaxException;

import core.AppContainer;
import core.AppContext;
import core.AppState;
import core.StateBasedApp;

public class TiledMapState extends AppState {

	private JsonTiledMap jsonTiledMap;

	public TiledMapState(int id) {
		super(id);
	}

	@Override
	public void init(AppContainer appContainer, StateBasedApp app) {

		try {
			jsonTiledMap = new JsonTiledMap("res/map/Gym.json", AppContext.textureManager.get("GymsTileset"), new Vector2f(16, appContainer.getHeight() - 16));
		}
		catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}

		super.init(appContainer, app);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void input() {

	}

	@Override
	public void render() {
		jsonTiledMap.render();
	}

}
