/**
 * @author chila
 */
package hw4;

import java.util.ArrayList;

import api.Point;
import api.PositionVector;

public class StraightLink extends AbstractLink {

	private ArrayList<Integer> num = new ArrayList<>();
	
	private Point point1;
	private Point point2;
	private Point point3;
	public StraightLink(Point endpointA, Point endpointB, Point endpointC) { 
		point1 = endpointA;
		point2 = endpointB;
		point3 = endpointC;

		setNumPath(3);
	}
	@Override
	public Point getConnectedPoint(Point point) {
		for(Integer i : num) {
			
		}
		if(point == point1) {
			return point2;
		} else {
			return point1;
		}
	}


	
	
	
	

}
