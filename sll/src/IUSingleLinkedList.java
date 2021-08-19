import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author 
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private Node<T> head, tail;
	private int size;
	private int modCount;
	
	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element){
		Node<T> newNode = new Node<T>(element);
		if(isEmpty())
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			newNode.setNext(head);
			head = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		Node<T> newNode = new Node<T>(element);
		if(isEmpty())
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		Node<T> newNode = new Node<T>(element);
		if(isEmpty())
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
		modCount++;		
	}

	@Override
	public void addAfter(T element, T target) {
		boolean found = true;
		Node<T> newNode = new Node<T>(element);
		Node<T> current = head;
		while(current !=null && found)
			if(target.equals(current.getElement()))
				found = true;
			else
				current = current.getNext();
		if(!found)
			throw new ElementNotFoundException("IndexedUnsortedList");
		
		current.setNext(newNode);
		newNode.setNext(current.getNext());
		
		if(newNode.getNext()==null)
			tail = newNode;
		
		size++;
		modCount++;		
	}

	@Override
	public void add(int index, T element) {
		Node<T> newNode = new Node<T>(element);
		
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		if(index == 0)
			addToFront(element);
		if(index == size)
			addToRear(element);
		else
		{
			Node<T> current = head;
			for(int whereIsCurrent = 0; whereIsCurrent < index; whereIsCurrent++ )
				current = current.getNext();
			newNode.setNext(current.getNext());
		}
		size++;
		modCount++;		

		
	}

	@Override
	public T removeFirst() {
		if(isEmpty())
			throw new NoSuchElementException();
		
		Node<T> result = head;
		head = head.getNext();
		
		if(head == null)
			tail = null;
		
		size--;
		modCount++;
		
		return result.getElement();
	}

	@Override
	public T removeLast() {
		if(isEmpty())
			throw new NoSuchElementException();
		
		T returnVal = tail.getElement();
		
		Node<T> current = head;
		Node<T> previous = null;
		
		if(size == 1) {
			head = null;
			tail = null;
		}
		else {
			while(current != tail) {
				previous = current;
				current = current.getNext();
			}
			previous.setNext(null);
			tail = previous;
		}
		size --;
		modCount++;
		return returnVal;
	}

	@Override
	public T remove(T element) {
		if(indexOf(element) < 0 || indexOf(element) >= size)
			throw new NoSuchElementException();
		
		size--;
		modCount++;
		return remove(indexOf(element));
	}


	@Override
	public T remove(int index) {

		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();

		Node<T> current = head;
		int i = 0;
		if(index == 0 && current != null) {
			return head.getElement();
		}
		else {
		while(i < index && current != null) {
			i++;
			current = current.getNext();
		}
		}	
		size--;
		modCount++;
		
		return current.getElement();	
	}

	@Override
	public void set(int index, T element) {
		
		Node<T> newNode = new Node<T>(element);
		
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		if(index == 0)
			addToFront(element);
		if(index == size)
			addToRear(element);
		else
		{
			Node<T> current = head;
			for(int whereIsCurrent = 0; whereIsCurrent < index-1; whereIsCurrent++ )
				current = current.getNext();
			newNode.setNext(current.getNext());
		}	
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();

		Node<T> current = head;
		Node<T> previous = null;
		if(current == null)
			throw new NoSuchElementException();
		if(index == 0)
			return head.getElement();
		if(index == size)
			return tail.getElement();
		else{
			while(current != null) {
			previous = current;
			current = current.getNext();
			}
		}
		return previous.getElement();
	}

	@Override
	public int indexOf(T element) {
		if(isEmpty())
			return -1;
		
		Node<T> current = head;
		boolean found = false;
		int i = 0;
		while(current!= null && !found) {
			if(current.getElement() == element)
				found = true;
			else {
			current = current.getNext();
			i++;
			}
		if(found == false)
		{
			return -1;
		}
		}
		return i;
	}

	@Override
	public T first() {
		if(isEmpty())
			throw new NoSuchElementException();
		else
			return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty())
			throw new NoSuchElementException();
		else
			return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		return indexOf(target) >= 0;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		String result = " ";
		Node<T> current = head;
		if(current == null)
			return result;
		while(current.getNext() != null) {
			result += current.getElement();
			result += ", ";
			current = current.getNext();
		}
		return result;
	}
	

	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private Node<T> nextNode;
		private Node<T> previousNode;
		private int iterModCount;
		private boolean removeCheck;
		
		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			previousNode = head;
			iterModCount = modCount;
			removeCheck = false;
		}

		@Override
		public boolean hasNext() {
			if(iterModCount != modCount)
				throw new ConcurrentModificationException();
			return (nextNode != null);
		}

		@Override
		public T next() {
			if(!hasNext())
				throw new NoSuchElementException();
			
			T result = nextNode.getElement();
			previousNode = nextNode;
			nextNode = nextNode.getNext();
			
			removeCheck = true;
			return result;
		}
		
		@Override
		public void remove() {
			if(removeCheck == false)
				throw new IllegalStateException();
			
			nextNode = nextNode.getNext();
			previousNode = nextNode.getNext();
			iterModCount++;
			modCount++;
			removeCheck = false;
			
		}
	}
}