/**
 * @author chila
 */
package hw4;

import api.Point;
import api.PositionVector;

public class CouplingLink extends AbstractLink{

	private Point point1;
	private Point point2;
	public CouplingLink(Point endpoint1, Point endpoint2) {
		point1 = endpoint1;
		point2 = endpoint2;

		setNumPath(2);
	}

	@Override
	public Point getConnectedPoint(Point point) {
		if(point == point1) {
			return point2;
		} else if(point == point2) {
			return point1;
		}
		return null;
	}

	
}
