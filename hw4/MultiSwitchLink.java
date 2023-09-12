/**
 * @author chila
 */
package hw4;

import api.Point;
import api.PointPair;
import api.PositionVector;

public class MultiSwitchLink extends AbstractLink{

	private PointPair[] pairs;
	public MultiSwitchLink(PointPair[] connection) {
		pairs = connection;
		setNumPath(pairs.length);
	}
	
	@Override
	public Point getConnectedPoint(Point point) {
		for(int i = 0; i < pairs.length; i++) {
			if(pairs[i].getPointA() == point) {
				return pairs[i].getPointB();
			} else if(pairs[i].getPointB() == point) {
				return pairs[i].getPointA();
			}
		}
		return null;
	}

	@Override
	public void shiftPoints(PositionVector positionVector) {
		multiShift(positionVector);
		
	}
	
	public void switchConnection(PointPair pointPair, int index) {
		pairs[index] = pointPair;
	}

}
