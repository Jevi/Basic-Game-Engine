package core;

public interface Manager<T> {

	public boolean register(String path);

	public boolean drop(String id);

	public void dropAll();

	public T get(String id);

}
