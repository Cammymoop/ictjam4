package io.github.felixnemis.ictjam4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.HashSet;

public class MazeCreator {
	private int targetRooms;
	private Random random;
	private boolean generated;
	
	private SchematicManager schematicManager;

	private HashMap<JamCoordPair, RoomDef> coordsToRooms;
	private LinkedList<JamCoordPair> generatorQueue;
	
	public MazeCreator(long seed, int targetRooms) {
		this.targetRooms = targetRooms;
		this.generated = false;

        this.random = new Random(seed);
        
        this.schematicManager = new SchematicManager();
	}
	
	public void Generate() {
		int rot = this.random.nextInt(4);
		JamCoordPair curCoord = new JamCoordPair(0, 0);
		
		this.coordsToRooms = new HashMap<JamCoordPair, RoomDef>();
		this.generatorQueue = new LinkedList<JamCoordPair>();
		
		this.coordsToRooms.put(curCoord, new RoomDef(0, 1, 1, 1, 2, rot, ""));
		
		JamCardinal sideEast = new JamCardinal(JamCardinalDir.EAST);
		JamCardinal sideSouth = new JamCardinal(JamCardinalDir.SOUTH);
		JamCardinal sideWest = new JamCardinal(JamCardinalDir.WEST);

		sideEast.rotate(rot);
		this.generatorQueue.push(sideEast.moveOnce(curCoord));
		sideSouth.rotate(rot);
		this.generatorQueue.push(sideSouth.moveOnce(curCoord));
		sideWest.rotate(rot);
		this.generatorQueue.push(sideWest.moveOnce(curCoord));
		
		int createdRooms = 1;
		
		boolean bossRoomCreated = false;
		
		HashSet<JamCoordPair> spawnedRooms = new HashSet<JamCoordPair>();
		spawnedRooms.add(new JamCoordPair(0, 0));
		
		int loops = 0;
		while (this.generatorQueue.size() > 0 && loops < 1000) {
			++loops;
			JamCoordPair c = this.generatorQueue.removeFirst();
			int northRand = this.random.nextInt(2);
			int eastRand = this.random.nextInt(2);
			int southRand = this.random.nextInt(2);
			int westRand = this.random.nextInt(2);
			
			if (createdRooms >= this.targetRooms && bossRoomCreated) { // stop making new rooms
				northRand = eastRand = southRand = westRand = 0;
			}
			
			JamCardinal n = new JamCardinal(JamCardinalDir.NORTH);
			JamCardinal e = new JamCardinal(JamCardinalDir.EAST);
			JamCardinal s = new JamCardinal(JamCardinalDir.SOUTH);
			JamCardinal w = new JamCardinal(JamCardinalDir.WEST);

            if (spawnedRooms.contains(n.moveOnce(c))) {
                northRand = coordsToRooms.get(n.moveOnce(c)).doorSouth;
            }
            if (spawnedRooms.contains(e.moveOnce(c))) {
                eastRand = coordsToRooms.get(e.moveOnce(c)).doorWest;
            }
            if (spawnedRooms.contains(s.moveOnce(c))) {
                southRand = coordsToRooms.get(s.moveOnce(c)).doorNorth;
            }
            if (spawnedRooms.contains(w.moveOnce(c))) {
                westRand = coordsToRooms.get(w.moveOnce(c)).doorEast;
            }
            
			System.out.println("nesw: " + " " + northRand + " " + eastRand + " " + southRand + " " + westRand);
            String roomType = getRoomTypeFromDoors(northRand, eastRand, southRand, westRand);
            System.out.println("room type: " + roomType);

            int variationCount = this.schematicManager.schematicTypeCounts.get(roomType);
            //System.out.println("variation count: " + variationCount);
            int randomVariation = this.random.nextInt(variationCount);
            
            int rotation = getRotationFromTypeAndDoors(roomType, northRand, eastRand, southRand, westRand);
            //System.out.println("rotation type: " + rotation);
            
            String special = "";
            if (!bossRoomCreated && createdRooms > this.targetRooms/0.75) {
	            JamCardinal bossExtension = new JamCardinal(JamCardinalDir.NORTH);
	            bossExtension.rotate(rotation);
	            if (roomType == "1" && !spawnedRooms.contains(bossExtension.moveOnce(c))) {
	            	special = "A";
	    			this.coordsToRooms.put(bossExtension.moveOnce(c), new RoomDef(0, 0, 0, 0, randomVariation, rotation, "B"));
	    			spawnedRooms.add(bossExtension.moveOnce(c));
	    			bossRoomCreated = true;
	            }
            }
			
			this.coordsToRooms.put(c, new RoomDef(northRand, eastRand, southRand, westRand, randomVariation, rotation, special));
	        System.out.println("making room at chunk (" + c.getX() + ", " + c.getZ() + ")");
			//System.out.println(this.coordsToRooms.get(c).getSchematicName());
			spawnedRooms.add(new JamCoordPair(c.getX(), c.getZ()));
			++createdRooms;

			if (northRand > 0 && !spawnedRooms.contains(n.moveOnce(c))) {
			    this.generatorQueue.push(n.moveOnce(c));
			}
			if (eastRand > 0 && !spawnedRooms.contains(e.moveOnce(c))) {
			    this.generatorQueue.push(e.moveOnce(c));
			}
			if (southRand > 0 && !spawnedRooms.contains(s.moveOnce(c))) {
			    this.generatorQueue.push(s.moveOnce(c));
			}
			if (westRand > 0 && !spawnedRooms.contains(w.moveOnce(c))) {
			    this.generatorQueue.push(w.moveOnce(c));
			}
	    }
		if (this.isRoomAtChunk(0, 0)) {
			System.out.println("there is a room at chunk 0 0");
		} else {
			System.out.println("there is a not room at chunk 0 0");
		}
		if (this.isRoomAtChunk(1, 0)) {
			System.out.println("there is a room at chunk 1 0");
		} else {
			System.out.println("there is a not room at chunk 1 0");
		}
		if (this.isRoomAtChunk(-1, 0)) {
			System.out.println("there is a room at chunk -1 0");
		} else {
			System.out.println("there is a not room at chunk -1 0");
		}
		if (this.isRoomAtChunk(0, 1)) {
			System.out.println("there is a room at chunk 0 1");
		} else {
			System.out.println("there is a not room at chunk 0 1");
		}
		if (this.isRoomAtChunk(0, -1)) {
			System.out.println("there is a room at chunk 0 -1");
		} else {
			System.out.println("there is a not room at chunk 0 -1");
		}
	}

    private String getRoomTypeFromDoors(int north, int east, int south, int west) {
        int count = 0;
        count += north > 0 ? 1 : 0;
        count += east > 0 ? 1 : 0;
        count += south > 0 ? 1 : 0;
        count += west > 0 ? 1 : 0;

        switch (count) {
            case 1:
                return "1";
            case 2:
                if ((north > 0 && south > 0) || (west > 0 && east > 0)) {
                    return "2a";
                }
                return "2b";
            case 3:
                return "3";
            case 4:
                return "4";
        }
        // shouldn't get here
        return "1";
    }
    
    private int getRotationFromTypeAndDoors(String roomType, int north, int east, int south, int west) {
        switch (roomType) {
            case "1":
            	if (south > 0) {
            		return 0;
            	} else if (west > 0) {
            		return 1;
            	} else if (north > 0) {
            		return 2;
            	} else {
            		return 3;
            	}
            case "2a":
            	if (south > 0) {
            		return 0;
            	} else {
            		return 1;
            	}
            case "2b":
            	if (south > 0) {
            		if (east > 0) {
            			return 0;
            		} else {
            			return 1;
            		}
            	} else {
            		if (west > 0) {
            			return 2;
            		} else {
            			return 3;
            		}
            	}
            case "3":
            	if (north < 1) {
            		return 0;
            	} else if (east < 1) {
            		return 1;
            	} else if (south < 1) {
            		return 2;
            	} else {
            		return 3;
            	}
            case "4":
                return 0;
        }
        return 0;
    }
    
    public boolean isRoomAtChunk(int chunkX, int chunkZ) {
    	return this.coordsToRooms.containsKey(new JamCoordPair(chunkX, chunkZ));
    }
	
	public RoomDef getRoomForChunk(int chunkX, int chunkZ) {
    	return this.coordsToRooms.get(new JamCoordPair(chunkX, chunkZ));
	}
}
