package io.github.felixnemis.ictjam4;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class SchematicManager {
	public HashMap<String, Integer> schematicTypeCounts;
	public static String[] schematicTypes = {"1", "2a", "2b", "3", "4"};
	public SchematicManager() {
		FileSystem fs = FileSystems.getDefault();
		
		this.schematicTypeCounts = new HashMap<String, Integer>();
		for (String t : schematicTypes) {
			JamSchematicFinder finder = new JamSchematicFinder(t);
			try {
			    Files.walkFileTree(Paths.get("schematics/"), finder);
			    int num = finder.getNumMatches();
			    int actualNum = t == "2a" ? num/2 : (t == "4" ? num : num/4);
		        System.out.println("actualNum: " + actualNum);
			    schematicTypeCounts.put(t, actualNum);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
