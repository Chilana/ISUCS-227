/**
 * @author chila
 */
package hw4;

import api.Point;
import api.PositionVector;

public class DeadEndLink extends AbstractLink {


	public DeadEndLink() {
		setNumPath(1);
		
		
	}
	@Override
	public Point getConnectedPoint(Point point) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void shiftPoints(PositionVector positionVector) {
		
	}

	

}
