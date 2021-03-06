package test.pong;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import core.AppContext;

public class PongWorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		if (contact.getFixtureA().getBody().getUserData().equals("PongBall") || contact.getFixtureB().getBody().getUserData().equals("PongBall")) {
			AppContext.audioManager.get("BallCollision").play();
		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}
