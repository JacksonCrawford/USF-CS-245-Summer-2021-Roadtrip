package project03;

// ArrayList Class that implements List interface
public class ArrayList<T> implements List<T> {

	// Initialize array and size
	private T[] arr;
	private int size;

	// Constructor
	@SuppressWarnings("unchecked")
	public ArrayList() {
		size = 0;
		arr = (T[]) new Object[10];
	}
	
	// Constructor with size
	@SuppressWarnings("unchecked")
	public ArrayList(int len) {
		size = len;
		arr = (T[]) new Object[10];
	}
	
	// toString method
	public String toString() {
		for(T ele : arr) {
			if(ele != null) {
				System.out.println(ele);
			}
		}
		return "";
	}
	
	// Method to get array size
	public int size() {
		int len = 0;
		for(T ele : arr) {
			if(ele != null) {
				len++;
			}
		}
		return len;
	}

	// Method to remove trailing null values from the array
	@SuppressWarnings("unchecked")
	public void compress() {
		int len = size;
		while(arr[len] == null) {
			len--;
		}
		
		T[] newArr = (T[]) new Object[len];
		
		for(int i=0;i<len;i++) {
			newArr[i] = arr[i];
		}
		
		arr = newArr;
	}
	
	// Double array size
	public void growArray() {
		@SuppressWarnings("unchecked")
		T[] newArr = (T[]) new Object[arr.length * 2];
		for(int i=0;i<arr.length;i++) {
			newArr[i] = arr[i];
		}
		arr = newArr;
	}
	
	// Add a value to the end of the array
	public void add(T value) {
		if(arr.length == size) {
			growArray();
		}
		arr[size++] = value;
//		size++;
	}

	// Add a value to a specified position in the array
	@Override
	public void add(T value, int pos) throws Exception {
		if(pos > size) { throw new Exception("Position " + pos +
				" out of bounds for length " + arr.length);
		} else if(pos < 0) { throw new Exception("Position " + pos +
				" out of bounds: below zero."); }
		if(arr.length == size) {
			growArray();
		}
		for(int i=size;i>pos;i--) {
			arr[i] = arr[i-1];
		}
		arr[pos] = value;
		size++;
	}

	// Get a value form the array at an index
	@Override
	public T get(int pos) throws Exception {
		if(pos > arr.length) { throw new Exception("Position " + pos +
				" out of bounds for length " + arr.length);
		} else if(pos < 0) { throw new Exception("Position " + pos +
				" out of bounds: below zero."); }
		
		return arr[pos];
	}

	// Remove a value from an index
	@Override
	public T remove(int pos) throws Exception {
		if(pos > size-1) { throw new Exception("Position " + pos +
				" out of bounds for length " + arr.length);
		} else if(pos < 0) { throw new Exception("Position " + pos +
				" out of bounds: below zero."); }
		if(arr.length == size) {
			growArray();
		}
		T obj = arr[pos];
		for(int i=pos;i<size;i++) {
			arr[i] = arr[i+1];
		}
		size--;
		
		return obj;
	}
	
	// Check if a value is stored within the array
	@Override
	public boolean contains(T obj) {
		for(int i=0;i<arr.length;i++) {
			if(arr[i].equals(obj)) {
				return true;
			}
		}
		
		return false;
	}
}