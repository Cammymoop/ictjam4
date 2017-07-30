package io.github.felixnemis.ictjam4;


public class JamCardinal {
	private JamCardinalDir dir;
	public JamCardinal(JamCardinalDir direction) {
		this.dir = direction;
	}
	
	public JamCardinalDir getDir() {
		return this.dir;
	}
	
	public void setDir(JamCardinalDir direction) {
		this.dir = direction;
	}
	
	public void rotate(int rotation) {
		for (int i = 0; i < rotation; ++i) {
			this.r90();
		}
	}
	
	private void r90() {
		switch (this.dir) {
		case NORTH:
			this.dir = JamCardinalDir.EAST;
			break;
		case EAST:
			this.dir = JamCardinalDir.SOUTH;
			break;
		case SOUTH:
			this.dir = JamCardinalDir.WEST;
			break;
		case WEST:
			this.dir = JamCardinalDir.NORTH;
			break;
		}
	}
	
	public JamCoordPair moveOnce(JamCoordPair coord) {
		switch (this.dir) {
		case NORTH:
	        System.out.println("moving NORTH");
			return new JamCoordPair(coord.getX(), coord.getZ() - 1);
		case EAST:
	        System.out.println("moving EAST");
			return new JamCoordPair(coord.getX() + 1, coord.getZ());
		case SOUTH:
	        System.out.println("moving SOUTH");
			return new JamCoordPair(coord.getX(), coord.getZ() + 1);
		case WEST:
	        System.out.println("moving WEST");
			return new JamCoordPair(coord.getX() - 1, coord.getZ());
		default:
			return null;
		}
	}
}
