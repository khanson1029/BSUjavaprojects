import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.*;

import javax.lang.model.element.UnknownElementException;

/**
 * Array-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported. 
 * 
 * @author Kyle Hanson
 *
 * @param <T> type to store
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;
	
	private T[] array;
	private int rear;
	private int modCount;
	
	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/** 
	 * Creates an empty list with the given initial capacity
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (T[])(new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
	}
	
	/** Double the capacity of array */
	private void expandCapacity() {
		array = Arrays.copyOf(array, array.length*2);
	}

	@Override
	public void addToFront(T element) {
		if(size() == array.length)
			expandCapacity();
		int scan = 0;
		for(int i = rear; i > scan; i--)
			array[i] = array[i-1];
		array[scan] = element;
		rear++;
		modCount++;
		
		
	}

	@Override
	public void addToRear(T element) {
		Comparable<T> comparableElement = (Comparable<T>)element;

		if(size() == array.length)
			expandCapacity();
		int scan = 0;
		
		while(scan < rear)
			scan++;
		for(int i = rear; i > scan; i--)
			array[i] = array[i-1];
		array[scan] = element;
		rear++;
		modCount++;
		
	}

	@Override
	public void add(T element){
		if (!(element instanceof Comparable))
			throw new NonComparableElementException("OrderedList");
		if(size() == array.length)
			expandCapacity();
		
		int scan = 0;
		
		while(scan < rear )
			scan++;
		array[scan] = element;
		
		for(int i = rear; i > scan; i--)
			array[i] = array[i-1];
		
		rear++;
		modCount++;
		
		
	}

	@Override
	public void addAfter(T element, T target) {
		Comparable<T> comparableElement = (Comparable<T>)element;

		if(size() == array.length)
			expandCapacity();
		int scan = 0;
		
		while(!target.equals(array[scan])) {
			scan++;
		}
		if(scan == rear)
			throw new ElementNotFoundException("IndexedUnsortedList");
		
		scan++;

		// shift elements up one
		for (int shift = rear; shift > scan; shift--)
		array[shift] = array[shift - 1];
		// insert element
		array[scan] = element;
		// clean up
		rear++;
		modCount++;
		
	}

	@Override
	public void add(int index, T element) {
		 if(index < 0 || index > array.length)
			 throw new IndexOutOfBoundsException("IndexedUnsortedList");
		 if(size() == array.length)
			 expandCapacity();
		 int scan = 0;
		 
		 while(scan < index)
			 scan ++;
		 for(int i = rear; i > scan; i--)
			 array[i] = array[i-1];
		 array[scan] = element;
		 rear++;
		 modCount++;
		
	}

	@Override
	public T removeFirst() {
		T result;
		int index = 0;
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		result = array[index];
		for(int i = index; i < rear; i++)
			array[i] = array[i+1];
		array[rear] = null;
		rear--;
		modCount++;
		return result;
	}

	@Override
	public T removeLast() {
		T result;
		int index = 0;
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		while(index < rear)
			index++;
		result = array[index];
		array[rear] = null;
		rear--;
		modCount++;
		
		
		return result;
		
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		
		T retVal = array[index];
		
		
		//shift elements
		for (int i = index; i < rear; i++) {
			array[i] = array[i+1];
		}
		array[rear] = null;
		rear--;
		modCount++;
		
		return retVal;
	}

	@Override
	public T remove(int index) {
		T result;
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		int scan = 0;
		while(scan < index)
			scan++;
		result = array[scan];
		for(int i = index; i < rear; i++)
			array[i] = array[i+1];
		array[rear] = null;
		rear--;
		modCount++;
		
		return result;
		
	}

	@Override
	public void set(int index, T element) {
		if (index < 0 || index > rear) {
			throw new IndexOutOfBoundsException();
		}
		
		int scan = 0;
		while(scan < index)
			scan++;
		array[scan] = element;
		
		
	}

	@Override
	public T get(int index) {
		T result;
		if (index < 0 || index > rear) {
			throw new IndexOutOfBoundsException();
		}
		result = array[index];
		
		return result;
	}

	@Override
	public int indexOf(T element) {
		int index = NOT_FOUND;
		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i < rear) {
				if (element.equals(array[i])) {
					index = i;
				} else {
					i++;
				}
			}
		}
		
		return index;
	}

	@Override
	public T first() {
		if (array.equals(null)) {
			throw new NoSuchElementException();
		}
		else
			return array[0];
	}

	@Override
	public T last() {
		if (array.equals(null)) {
			throw new NoSuchElementException();
		}
		else{
			int scan = 0;
		while(scan < rear)
			scan++;
		return array[scan];
		}
	}

	@Override
	public boolean contains(T target) {
		return (indexOf(target) != NOT_FOUND);
	}

	@Override
	public boolean isEmpty() {
		if(array.equals(null)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int size() {
		
		return array.length;
	}

	@Override
	public Iterator<T> iterator() {
		return new ALIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T> {
		private int current;
		private int iterModCount;
		
		public ALIterator() {
			current = 0;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			if(iterModCount != modCount)
				throw new ConcurrentModificationException();
			return (current < rear);
		}

		@Override
		public T next() {
			if(!hasNext())
				throw new NoSuchElementException();
			current++;
			return array[current -1];
		}
		
		@Override
		public void remove() throws UnsupportedOperationException{
			throw new UnsupportedOperationException();
			
		}
	}
}
