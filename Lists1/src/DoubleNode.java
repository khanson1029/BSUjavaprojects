/**
 * DoubleNode represents a node in a double linked list.
 *
 * @author Kyle Hanson
 * 
 */
public class DoubleNode<T> {
	private DoubleNode<T> next;
	private T element;
	private DoubleNode<T> previous;

	/**
	 * Creates an empty node
	 */
	public DoubleNode() {
		next = null;
		previous = null;
		element = null;
	}

	/**
	 * Creates a node storing the specified element.
	 *
	 * @param elem
	 * @return the element to be stored within the new node
	 */
	public DoubleNode(T elem) {
		next = null;
		previous = null;
		element = elem;
	}

	/**
	 * Returns the node that follows this one.
	 *
	 * @return the node that follows the current one
	 */
	public DoubleNode<T> getNext(){
		return next;
	}
	/**
 	 * Returns the node that precedes this one.
  	 *
  	 * @return the node that precedes the current one
  	 */
	public DoubleNode<T> getPrevious(){
		return previous;
	}
	/**
 	 * Sets the node that follows this one.
 	 *
 	 * @param node the node to be set to follow the current one
 	 */
	public void setNext(DoubleNode<T> dnode) {
		next = dnode;
	}
	/**
 	 * Sets the node that precedes this one.
 	 *
 	 * @param node the node to be set to precede the current one
 	 */
	public void setPrevious(DoubleNode<T> dnode) {
		previous = dnode;
	}
	/**
 	 * Returns the element stored in this node.
 	 *
 	 * @return the element stored in this node
 	 */
	public T getElement() {
		return element;
	}
	/**
 	 * Sets the element stored in this node.
  	 *
  	 * @param elem the element to be stored in this node
  	 */
	public void setElement(T elem) {
		element = elem;
	}
	
	@Override
	public String toString() {
		return "Element: " + element.toString() + " Has next: " + (next != null) +"/nElement: " + element.toString() + "Has previous " + (previous != null);
	}
}
