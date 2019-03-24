package inf112.skeleton.app.objects;

import inf112.skeleton.app.logic.CardType;
import inf112.skeleton.app.logic.Direction;

import java.util.ArrayList;

public interface IProgramCard {
	
	
	/**
	 * A method that returns the type of the programcard
	 * @return
	 */
	CardType getCardType();
	
	/**
	 * A method that returns the priority of the programcard
	 * @return
	 */
	int getPriority();

	/**
	 *
	 * @return the direction the card points to
	 */
	ArrayList<Direction> getDirection();
	
	String toString();
	
	/**
	 * Må vel laga ein boolean metode elns som seier om eit kort er låst eller ikkje seinare?
	 */
	
	

}
