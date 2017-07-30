package io.github.felixnemis.ictjam4;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MyWorldType extends WorldType {
	public MyWorldType(String name) {
	    super(name);
        System.out.println("this is my constructor");
	}

	@SideOnly(Side.CLIENT)
	public String getTranslateName()
	{
	    return "World world";
	}

    public net.minecraft.world.gen.IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {
        System.out.println("Setting the world chunk generator");
    	return new ChunkGeneratorJam(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
    }
}
