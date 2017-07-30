package io.github.felixnemis.ictjam4;

public class JamCoordPair {
    private int x;
    private int yOrZ;
    
    public JamCoordPair(int x, int yOrZ) {
    	this.x = x;
    	this.yOrZ = yOrZ;
    }
    
    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.yOrZ;
    }
    
    public int getZ() {
    	return this.yOrZ;
    }

    public boolean equals(Object other) {
        if (other instanceof JamCoordPair) {
            JamCoordPair otherPair = (JamCoordPair) other;
            return this.getX() == otherPair.getX() && this.getY() == otherPair.getY();
        }
        return false;
    }

    public int hashCode() {
        return (this.x + this.yOrZ) * this.yOrZ + this.x;
    }
    
    public String toString() {
    	return "(" + this.x + ", " + this.yOrZ + ")";
    }
}