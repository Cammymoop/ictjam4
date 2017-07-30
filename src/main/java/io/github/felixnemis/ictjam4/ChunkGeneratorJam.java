package io.github.felixnemis.ictjam4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;

public class ChunkGeneratorJam implements IChunkGenerator {
    private final World world;
    private final Random random;
    //private final FlatGeneratorInfo flatWorldGenInfo;
    private final Map<String, MapGenStructure> structureGenerators = new HashMap<String, MapGenStructure>();
    
    private final IBlockState theBlock;
    //private JamSchematicReader schematic;
    
    private MazeCreator mazeCreator;
	
	public ChunkGeneratorJam(World worldIn, long seed, boolean generateStructures, String flatGeneratorSettings)
    {
        this.world = worldIn;
        this.random = new Random(seed);
        //this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(flatGeneratorSettings);

        if (generateStructures)
        {
        }
        
        this.mazeCreator = new MazeCreator(seed, 20);
        this.mazeCreator.Generate();

        Block testBlock = new BlockBookshelf();
        this.theBlock = testBlock.getDefaultState();
        
        //this.schematic = new JamSchematicReader("test");
        //this.schematic.loadFile();

        worldIn.setSeaLevel(1);
    }
	
	@Override
	public Chunk generateChunk(int x, int z) {

        ChunkPrimer chunkprimer = new ChunkPrimer();

        //Block testBlock = new BlockBookshelf();
        IBlockState bs = Block.getStateById(4);
        for (int j = 0; j < 16; ++j)
        {
            for (int k = 0; k < 16; ++k)
            {
                chunkprimer.setBlockState(j, 0, k, bs);
                chunkprimer.setBlockState(j, 1, k, bs);
                chunkprimer.setBlockState(j, 2, k, bs);
            }
            //chunkprimer.setBlockState(0, 3, 0, bs);
        }
        
        /*
        if (x == 0 && z == 0) {
        	for (int i = 3; i < 20; ++i) {
        		chunkprimer.setBlockState(8, i, 8, bs);
        	}
        }
        */
        
        if (this.mazeCreator.isRoomAtChunk(x, z)) {
            //System.out.println(this.mazeCreator.getRoomForChunk(x, z).getSchematicName());
            JamSchematicReader schem = new JamSchematicReader(this.mazeCreator.getRoomForChunk(x, z).getSchematicName());
            schem.loadFile();
        	IBlockState[] schemBlocks = schem.getBlocks();
        	int maxX = schem.getWidth();
        	int maxZ = schem.getLength();
        	int maxY = schem.getHeight();
        	for (int i = 0; i < schemBlocks.length; ++i) {
                //System.out.println("i: " + i);
        		int blockX = i % maxX;
        		int blockY = i / (maxX*maxZ);
        		int blockZ = (i/maxX) % maxZ;
                //System.out.println("x: " + blockX + " y: " + blockY + " z: " + blockZ);
        		if (blockX >= 16 || blockZ >= 16) {
        			continue;
        		}
        		chunkprimer.setBlockState(blockX, blockY + 2, blockZ, schemBlocks[i]);
        	}
        }

        for (MapGenBase mapgenbase : this.structureGenerators.values())
        {
            mapgenbase.generate(this.world, x, z, chunkprimer);
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        Biome[] abiome = this.world.getBiomeProvider().getBiomes((Biome[])null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (int l = 0; l < abyte.length; ++l)
        {
            abyte[l] = (byte)Biome.getIdForBiome(abiome[l]);
        }

        chunk.generateSkylightMap();
        return chunk;
	}

	@Override
	public void populate(int x, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		// TODO Auto-generated method stub
		return false;
	}

}
