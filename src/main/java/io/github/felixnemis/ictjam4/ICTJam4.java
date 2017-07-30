package io.github.felixnemis.ictjam4;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


@Mod(modid = ICTJam4.MODID, version = ICTJam4.VERSION)
public class ICTJam4 {
	public static final String MODID = "felixnemis_ictjam4";
	public static final String VERSION = "1.0";

	public static MyWorldType customWorldType;
	public static JamEventHandler myEvHnd;

	@EventHandler
    public void init(FMLInitializationEvent event){
		customWorldType = new MyWorldType("look");
		
        System.out.println("yadablablablabla!");
		myEvHnd = new JamEventHandler();
        MinecraftForge.EVENT_BUS.register(myEvHnd);
    }
}
