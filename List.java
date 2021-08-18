package project03;

public interface List<T> {
	public void add(T value);
	public void add(T value, int pos) throws Exception;
	public T get(int pos) throws Exception;
	public T remove(int pos) throws Exception;
	public boolean contains(T value);
}
