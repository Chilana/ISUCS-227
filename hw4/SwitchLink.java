/**
 * @author chila
 */
package hw4;

import api.Point;
import api.PositionVector;

public class SwitchLink extends AbstractLink{

	private boolean inTurn;
	
	private Point point1;
	private Point point2;
	private Point point3;
	
	public SwitchLink(Point endpointA, Point endpointB, Point endpointC) {
		point1 = endpointA;
		point2 = endpointB;
		point3 = endpointC;

		setNumPath(3);

	}
	@Override
	public Point getConnectedPoint(Point point) {
		if(point == point1 && inTurn) {
			return point3;
		} else if(point == point1 && !inTurn) {
			return point2;
		} else {
			return point1;
		}
		
	}


	/**
	 * sets if turn is true or not
	 * @param turn
	 */
	public void setTurn(boolean turn) {
		inTurn = turn;
	}
	

}
