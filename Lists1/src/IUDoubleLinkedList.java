import java.util.Iterator;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	
	private DoubleNode<T> head, tail;
	private int count, modCount;
	
	public IUDoubleLinkedList() {
		head = null;
		tail = null;
		count = 0;
		modCount = 0;
	}
	
	@Override
	public void addToFront(T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToRear(T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAfter(T element, T target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(int index, T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T removeFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T removeLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T remove(T element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(int index, T element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(T element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T first() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T last() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(T target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}
	
	private class DLLIterator implements Iterator<T>{
		private int iteratorModCount; //Number of elements
		private DoubleNode<T> current; //Current position
		private DoubleNode<T> previous; //Previous position
		private boolean removeCheck;

		public DLLIterator() {
			current = head;
			previous = head.getPrevious();
			iteratorModCount = modCount;
			removeCheck = false;
		}
		
		@Override
		public boolean hasNext() throws ConcurrentModificationException{
			if(iteratorModCount != modCount)
				throw new ConcurrentModificationException();
			return(current != null);
		}

		@Override
		public T next()throws ConcurrentModificationException {
			if(!hasNext())
				throw new NoSuchElementException();
			
			T result = current.getElement();
			current = current.getNext();
			removeCheck = true;
			return result;
		}
		public void remove() {
			if(removeCheck == false)
				throw new IllegalStateException();
			
			current = current.getNext();
			previous = current.getPrevious();
			iteratorModCount++;
			modCount++;
			removeCheck = false;
		}
	}
}
