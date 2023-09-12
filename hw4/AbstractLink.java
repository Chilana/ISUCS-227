/**
 * @author chila
 */
package hw4;

import api.Crossable;
import api.Point;
import api.PositionVector;

public abstract class AbstractLink implements Crossable{
	/**
	 *  boolean to represent if the train is in crossing
	 */
	private boolean inCross;
	/**
	 *  my constructer for abstract calls just sets my point variables to null and sets my inCross to false becasue it's not true at the beginning
	 */
	/**
	 * the numbers of paths
	 */
	private int numPaths;
	/**
	 * point variables repsents the points of a position vector
	 */
	private Point pointA;
	
	private Point pointB;
	
	public AbstractLink() {
		pointA = null;
		pointB = null;
		inCross = false;
	}
	
	/**
	 * this shifts the position vector for points given but this method is only for certain classes
	 */
	public void shiftPoints(PositionVector positionVector) {
		pointA = getConnectedPoint(positionVector.getPointB());
		
		if (pointA == pointA.getPath().getLowpoint()) { 

		 pointB = pointA.getPath().getPointByIndex(1);

		} else if (pointA == pointA.getPath().getHighpoint()) {

		 pointB = pointA.getPath().getPointByIndex(pointA.getPath().getNumPoints() - 2);

		}

		positionVector.setPointA(pointA);

		positionVector.setPointB(pointB);
	}
	
	/**
	 * the multiswitch and multifixed had different switch points logic so I made a protected method that way I can just call this method for both of them
	 * @param positionVector
	 */
	protected void multiShift(PositionVector positionVector) {
		trainEnteredCrossing();
		
		if(inCross) {
			pointA = getConnectedPoint(positionVector.getPointB());
			

			if (pointA == pointA.getPath().getLowpoint()) {

			 pointB = pointA.getPath().getPointByIndex(1);

			} else if (pointA == pointA.getPath().getHighpoint()) {

			 pointB = pointA.getPath().getPointByIndex(pointA.getPath().getNumPoints() - 2);

			}

			positionVector.setPointA(pointA);

			positionVector.setPointB(pointB);
		}
		
		trainExitedCrossing();
	}
	/**
	 * method says if the train is entered crossing
	 */
	public void trainEnteredCrossing() {
		inCross = true;
	}
	/**
	 * method says the train has exited the crossing
	 */
	public void trainExitedCrossing() {
		inCross = false;
	}
	/**
	 * set the num paths for a link given from the constructor of the class which will specify the number
	 * @param paths
	 */
	protected void setNumPath(int paths) {
		numPaths = paths;
	}
	/**
	 * gets the number of paths for the given link
	 * i thought this was smart to put in the abstract class because it was an easy getter and setter to set up
	 * and it took out a lot of code from the other class
	 */
	public int getNumPaths() {
		return numPaths;
	}
	
}

