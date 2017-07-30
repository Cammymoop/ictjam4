package io.github.felixnemis.ictjam4;

public class RoomDef {
	public int doorSouth;
	public int doorNorth;
	public int doorWest;
	public int doorEast;
	public int variant;
	public int rotation;

	public boolean isBossA;
	public boolean isBossB;
	
	public String roomType;
	
	public RoomDef(int doorNorth, int doorEast, int doorSouth, int doorWest, int variant, int rotation, String special) {
		this.doorNorth = doorNorth;
		this.doorEast = doorEast;
		this.doorSouth = doorSouth;
		this.doorWest = doorWest;
		this.variant = variant;
		this.rotation = rotation;

		this.isBossA = false;
		this.isBossB = false;
		
		if (special == "A") {
			this.isBossA = true;
			this.doorNorth = this.doorEast = this.doorSouth = this.doorWest = 0;
		} else if (special == "B") {
			this.isBossB = true;
			this.doorNorth = this.doorEast = this.doorSouth = this.doorWest = 0;
		}


        int count = 0;
        count += doorNorth > 0 ? 1 : 0;
        count += doorEast > 0 ? 1 : 0;
        count += doorSouth > 0 ? 1 : 0;
        count += doorWest > 0 ? 1 : 0;

        this.roomType = "1";
        switch (count) {
            case 1:
                this.roomType = "1";
                break;
            case 2:
                if ((doorNorth > 0 && doorSouth > 0) || (doorWest > 0 && doorEast > 0)) {
                    this.roomType = "2a";
                } else {
                	this.roomType = "2b";
                }
                break;
            case 3:
                this.roomType = "3";
                break;
            case 4:
                this.roomType = "4";
                break;
        }
	}
	
	public String getSchematicName() {
		if (this.isBossA) {
			return "bossa_" + rotation;
		}
		if (this.isBossB) {
			return "bossb_" + rotation;
		}
		return roomType + "_" + variant + "_" + rotation;
	}
}
