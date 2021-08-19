public class NonComparableElementException extends RuntimeException{
/**
* Sets up this exception with an appropriate message.
*
* @param collection the exception message to deliver
*/
public NonComparableElementException (String collection){
super ("The " + collection + " requires Comparable elements.");
}
}