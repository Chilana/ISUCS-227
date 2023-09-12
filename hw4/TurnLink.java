/**
 * @author chila
 */
package hw4;

import api.Point;
import api.PositionVector;

public class TurnLink extends AbstractLink {

	private Point point1;
	private Point point2;
	private Point point3;
	
	public TurnLink(Point endpointA, Point endpointB, Point endpointC) {
		point1 = endpointA;
		point2 = endpointB;
		point3 = endpointC;
		setNumPath(3);
	}
	@Override
	public Point getConnectedPoint(Point point) {
		if(point1 == point) {
			return point3;
		} else {
			return point1;
		}
	}

	


}
