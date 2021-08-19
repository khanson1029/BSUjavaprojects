import java.util.Iterator;

import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
/**
 * Doubly-Linked List implementation of the IndexedUnsortedList interface.
*
 * @param <T> - type of elements held in this collection
 * @author Kyle Hanson
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	
	private DoubleNode<T> head, tail;
	private int count, modCount;
	/**
	 * Finds a node storing the target element.
	 *
	 * @param target
	 */
	private DoubleNode<T> find(T target){
		
		boolean found = false;
		DoubleNode<T> current = head;
		DoubleNode<T> result = null;
		
		if(!isEmpty()) {
			while(!found && current != null) {
				if(target.equals(current.getElement()))
					found = true;
				else
					current = current.getNext();
			}
		}

		if(found)
			result = current;
		return result;
	}

	
	private DoubleNode<T> addNode(T element, DoubleNode<T> next, DoubleNode<T> prev){
		DoubleNode<T> add = new DoubleNode<T>(element);
		add.setNext(next);
		add.setPrevious(prev);
		if(next != null) {
			next.setPrevious(add);
		}
		else {
			tail = add;
		}
		if(prev != null) {
			prev.setNext(add);
		}
		else {
			head = add;
		}
		count++;
		modCount++;
		return add;
	}
	public IUDoubleLinkedList() {
		head = null;
		tail = null;
		count = 0;
		modCount = 0;
	}
	
	/**
	 * Adds a new object to the front of the list.
	 *
	 * @param element
	 */
	
	public void addToFront(T element) {
		addNode(element, head, null);
		
	}

	/**
	 * Adds a new object to the end of the list.
	 *
	 * @param element
	 */
	public void addToRear(T element) {
		addNode(element, null, tail);
	}

	/**
	 * Adds a new object to the end of the list.
	 *
	 * @param element
	 */
	public void add(T element) {
		addNode(element, null, tail);
	}
	/**
	 * Adds a new object after the target element.
	 *
	 * @param element, target
	 */
	public void addAfter(T element, T target) {
		DoubleNode<T> prev = find(target);
		if(prev == null) {
			throw new NoSuchElementException();
		}
		addNode(element, prev.getNext(), prev);
	}

	/**
	 * Adds a new object at the specified index in the list.
	 *
	 * @param element, index
	 */
	public void add(int index, T element) {
		if(index < 0 || index > size())
			throw new IndexOutOfBoundsException();
		if(index == 0)	
		{
			addToFront(element);
		}
			
		else if(index == size())
		{
			addToRear(element);
		}	
		else
		{
			DoubleNode<T> node = new DoubleNode<T>(element);
			DoubleNode<T> current = head;
			for(int i = 0; i < index; i++ )
				current = current.getNext();
			addNode(element, node.getNext(), node);
			
		}		
	}

	/**
	 * Removes the first object of the list.
	 *
	 */
	public T removeFirst() {
		if(isEmpty())
			throw new NoSuchElementException();
		T returnFirst = head.getElement();
		
		DoubleNode<T> current = head;
		head = current.getNext();
		if(head!=null)
			head.setPrevious(null);

		count--;
		modCount++;
		return returnFirst;
	}

	/**
	 * Removes the last object of the list.
	 *
	 */
	public T removeLast() throws EmptyCollectionException {
		if(isEmpty())
			throw new NoSuchElementException("DLL");
		T returnLast = tail.getElement();
		
		DoubleNode<T> current = tail;
		tail = current.getPrevious();
		if(tail != null)
			tail.setNext(null);
		
		count--;
		modCount++;
		return returnLast;

	}

	/**
	 * Removes the specified object in the list.
	 *
	 *@param element
	 */
	public T remove(T element) {
		T result;
		boolean found = false;
		DoubleNode<T> current = head;
		
		while(current != null && !found)
			if(current.getElement().equals(element))
				found = true;
			else
				current = current.getNext();		
		if(!found)
			throw new NoSuchElementException("DLL");	
		
		result = current.getElement();
		
		//check to see if heads or tails
		if(current == head)
			result = this.removeFirst();
		else if(current == tail)
			result = this.removeLast();
		else {
			current.setPrevious(current.getPrevious());
			current.getPrevious().setNext(null);
			count--;
			modCount++;
		}

		
		return result;
	}

	/**
	 * Removes the element at the specified index of the list.
	 *
	 *@param index
	 */
	public T remove(int index) {
		DoubleNode<T> node = head;
		T result;
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		if(count == 0)
			throw new NoSuchElementException("DLL");		

		
		//heads tails check
		if(index == 0) {
			result = head.getElement();
			node = head;
			head = node.getNext();
			if(head!=null)
				head.setPrevious(null);
			count--;
			modCount++;
			return result;
		}
			
		if(index == size()) {
			result = tail.getElement();
			node = tail;
			tail = node.getPrevious();
			if(tail != null)
				tail.setNext(null);
			count--;
			modCount++;
			return result;
		}
			else {
				for(int i = 0; i < index; i++) {
					node = node.getNext();
					}
				result = node.getElement();
				if(node.getNext() != null && node.getPrevious() != null) {
				node.getPrevious().setNext(node.getNext());
				node.getNext().setPrevious(node.getPrevious());
				}
			}
		
		count--;
		modCount++;
		return result;
	}

	/**
	 * Replaces an element at a certain index with a new one.
	 *
	 *@param index, element
	 */
	public void set(int index, T element) {
		DoubleNode<T> node = new DoubleNode<T>(element);
		
		if(index < 0 || index >= count)
			throw new IndexOutOfBoundsException();
		else
		{
			DoubleNode<T> current = head;
			for(int i = 0; i < index; i++ )
				current = current.getNext();
			current.setElement(element);	
		}	
	}

	/**
	 * Gets the object at the specified index and returns the object found.
	 * 
	 * @param index
	 * @return element
	 */
	public T get(int index) {
		if(index < 0 || index > size())
			throw new IndexOutOfBoundsException();	
		if(isEmpty())
			throw new IndexOutOfBoundsException();
		DoubleNode<T> node = head;
		for(int i = 0; i < index; i++) {
			if(node.getNext() != null)
				node = node.getNext();
			else
				throw new IndexOutOfBoundsException();
		}

		return node.getElement();
	
	
	}

	/**
	 * Returns the index of a specified element.
	 *
	 *@param element
	 *@return index
	 */
	public int indexOf(T element) {	
		if(isEmpty())
				return -1;
		
		boolean found = false;
		DoubleNode<T> current = head;
		int i = 0;
		
		while(current != null && !found) {
			if(element.equals(current.getElement()))
				found = true;
			else {
				current = current.getNext();
				i++;
			}
			}
		if(!found)
			return -1;
		return i;
	}
	/**
	 * Returns the first element of the list.
	 *
	 *@return element
	 */
	public T first() {
		if(isEmpty())
			throw new NoSuchElementException("DLL");
		T result = null;
		DoubleNode<T> node = head;
		result = node.getElement();
		return result;
	}

	/**
	 * Returns the last element of the list.
	 * 
	 *@return element
	 */
	public T last() {
		if(isEmpty())
			throw new NoSuchElementException("DLL");
		T result = null;
		DoubleNode<T> node = tail;
		result = node.getElement();
		return result;
	}

	/**
	 * Returns true if the list contains the target element.
	 * 
	 *@param target
	 *@return true
	 */
	public boolean contains(T target) {
		return (find(target) != null);
	}

	/**
	 * Returns true if the list is empty.
	 * 
	 *@return true
	 */
	public boolean isEmpty() {
		return count==0;
	}

	/**
	 * Returns the size of the list.
	 * 
	 * @return size
	 */
	public int size() {
		return count;
	}

	/**
	 * Returns the string representation of the list
	 *
	 *@return string
	 */
	public String toString() {
		String result = "";
		DoubleNode<T> current = head;
		while(current != null) {
			result+= "[";
			result += current.getElement();
			result += "]";
			current = current.getNext();
			if(current!= null)
				result+= ",";
		}
		if(isEmpty())
			return "[]";
		return result;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		if(startingIndex >= 0 && startingIndex <= size())
			return new DLLIterator(startingIndex);
		else
			throw new IndexOutOfBoundsException();
	}
	/**
	 * Doubly-Linked List iterator implementation of the ListIterator interface.
	 *
	 * @author Kyle Hanson
	 */
	private class DLLIterator implements ListIterator<T>{
		private int iteratorModCount; //Number of elements
		private DoubleNode<T> next; //Current position
		private DoubleNode<T> lastReturned;//Last returned node
		private DoubleNode<T> previous; //previous position
		private int indexCount;//which index comes next
		private boolean removed = true; //a check for the set() method below
		public DLLIterator() {
			previous = null;
			next = head;
			iteratorModCount = modCount;
			lastReturned = null;
			indexCount = 0;
		}
		

		
		public DLLIterator(int index) {
			indexCount = 0;
			previous = null;
			next = head;
			iteratorModCount = modCount;
			for(int i = 0; i < index; i++)
				next();
			lastReturned = null;
		}
		
		/**
		 * Returns true if the list has another element.
		 * @return true
		 */
		public boolean hasNext() throws ConcurrentModificationException{
			if(iteratorModCount != modCount)
				throw new ConcurrentModificationException();
			return(next != null);
		}

		/**
		 * Returns the element the iterator has just passed over.
		 * @return element
		 */
		public T next()throws ConcurrentModificationException {
			if(!hasNext())
				throw new NoSuchElementException();
			
			T result = next.getElement();
			lastReturned = next;
			previous = next;

			next = next.getNext();
			indexCount++;
			return result;
		}
		
		/**
		 * Removes the element just passed if next() or previous() has been called.
		 */
		public void remove() {
			if(iteratorModCount != modCount)
				throw new ConcurrentModificationException();
			if (lastReturned == null) {
				removed = false;
				throw new IllegalStateException();
			}
			IUDoubleLinkedList.this.remove(IUDoubleLinkedList.this.indexOf(lastReturned.getElement()));
			iteratorModCount++;
			lastReturned = null;
			indexCount--;
		}

		/**
		 * Returns true if the list has a previous element.
		 * @return true
		 */
		public boolean hasPrevious() {
			if(iteratorModCount != modCount)
				throw new ConcurrentModificationException();
			return (previous != null);
		}

		/**
		 * Returns the element the iterator has passed over going backwards.
		 * @return element
		 */
		public T previous() {

			if(!hasPrevious())
				throw new NoSuchElementException();
			T result = previous.getElement();
			
			lastReturned = previous;
			next = previous;

			previous = previous.getPrevious();
			
			indexCount--;
			return result;
		}

		/**
		 * Returns the index of the element if one were to call next() one more time.
		 * @return index
		 */
		public int nextIndex() {
			if(iteratorModCount != modCount)
				throw new ConcurrentModificationException();
			return indexCount;
		}

		/**
		 * Returns the index of the element if one were to call previous() one more time.
		 * @return index
		 */
		public int previousIndex() {
			if(iteratorModCount != modCount)
				throw new ConcurrentModificationException();
			return indexCount - 1;
		}

		/**
		 * replaces the element that has just been passed over with the given element.
		 * @param e
		 */
		public void set(T e) {
			if(iteratorModCount != modCount)
				throw new ConcurrentModificationException();
			if(lastReturned == null && removed)
				throw new IllegalStateException();	
			DoubleNode<T> node = new DoubleNode<T>(e);
			lastReturned = node;	

		}

		/**
		 * Inserts an element into the list.
		 * @param e
		 */
		public void add(T e) {
			if(iteratorModCount != modCount)
				throw new ConcurrentModificationException();
			DoubleNode<T>add = new DoubleNode<T>(e);
			add.setNext(next);
			add.setPrevious(previous);
			if(next != null) {
				next.setPrevious(add);
			}
			else {
				tail = add;
			}
			if(previous != null) {
				previous.setNext(add);
			}
			else {
				head = add;
			}
			count++;
			modCount++;
		}
	}
}

