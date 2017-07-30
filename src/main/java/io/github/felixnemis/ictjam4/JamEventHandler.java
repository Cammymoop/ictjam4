package io.github.felixnemis.ictjam4;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class JamEventHandler {
	public JamEventHandler()
	{
	}

	@SubscribeEvent
	public void worldLoaded(WorldEvent.Load event)
	{
		if (!event.getWorld().isRemote)
		{
			event.getWorld().provider.setSpawnPoint(new BlockPos(8, 5, 8));
		}
	}
}
