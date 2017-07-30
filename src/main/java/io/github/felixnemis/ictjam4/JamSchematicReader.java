package io.github.felixnemis.ictjam4;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.jnbt.ByteArrayTag;
import org.jnbt.CompoundTag;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.ShortTag;
import org.jnbt.Tag;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class JamSchematicReader {
	private String fn;
	
	private short width;
	private short height;
	private short length;

    private byte[] blocks;
    private byte[] data;
    
    private boolean loaded;
	
    public JamSchematicReader(String filename) {
    	this.fn = filename;
    	this.loaded = false;
    }
    
    public void loadFile() {
    	try {
            //InputStream fis = getClass().getResourceAsStream(this.fn);
            //NBTTagCompound nbtdata = CompressedStreamTools.readCompressed(fis);
    		/*
    		NBTTagCompound nbtdata = CompressedStreamTools.read(new File(this.fn));
    		//DataInputStream dis = new DataInputStream(new FileInputStream(new File("schematics/", this.fn+".schematic")));
            //System.out.println("created file stream");
    		//NBTTagCompound nbtdata = CompressedStreamTools.read(dis);
    		 
            
            this.width = nbtdata.getShort("Width");
            this.height = nbtdata.getShort("Height");
            this.length = nbtdata.getShort("Length");
 
            this.blocks = nbtdata.getByteArray("Blocks");
            System.out.println(this.blocks.length);
            this.data = nbtdata.getByteArray("Data");
            
            this.loaded = true;
            */
 
            //NBTTagList entities = nbtdata.getList("Entities");
            //NBTTagList entities = nbtdata.getTagList("Entities", type);
            //NBTTagList tileentities = nbtdata.getTagList("TileEntities");
            
            //fis.close();
            File f = new File("schematics/", this.fn + ".schematic");
            FileInputStream fis = new FileInputStream(f);
            NBTInputStream nbt = new NBTInputStream(fis);
            CompoundTag backuptag = (CompoundTag) nbt.readTag();
            Map<String, Tag> tagCollection = backuptag.getValue();

            this.width = (Short)getChildTag(tagCollection, "Width", ShortTag.class).getValue();
            this.height = (Short) getChildTag(tagCollection, "Height", ShortTag.class).getValue();
            this.length = (Short) getChildTag(tagCollection, "Length", ShortTag.class).getValue();

            this.blocks = (byte[]) getChildTag(tagCollection, "Blocks", ByteArrayTag.class).getValue();
            this.data = (byte[]) getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();

            /*
            List entities = (List) getChildTag(tagCollection, "Entities", ListTag.class).getValue();
            List tileentities = (List) getChildTag(tagCollection, "TileEntities", ListTag.class).getValue();
            */
            nbt.close();
            fis.close();
            this.loaded = true;
            //System.out.println(entities);
        } catch (Exception e) {
            System.out.println("Error loading schemaic!");
            e.printStackTrace();
        }
    }
    
    private static Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
        Tag tag = items.get(key);
        return tag;
    }
    
    public IBlockState[] getBlocks() {
    	if (!this.loaded) {
    		return null;
    	}
        //System.out.println(this.blocks.length + " blocks___");
    	IBlockState[] blocks = new IBlockState[this.blocks.length];
    	for (int i = 0; i < this.blocks.length; ++i) {
    		blocks[i] = Block.getBlockById(this.blocks[i]).getStateFromMeta(this.data[i]);
    	}
    	return blocks;
    }
    
    public int getWidth() {
    	return (int) this.width;
    }
    
    public int getHeight() {
    	return (int) this.height;
    }
    
    public int getLength() {
    	return (int) this.length;
    }
}
