/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Jul 30, 2014, 1:10:34 PM (GMT)]
 */
package vazkii.botania.common.block.tile;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

public class TileEnderEye extends TileMod {

	@Override
	public void updateEntity() {
		if(worldObj.isRemote)
			return;
		
		int meta = getBlockMetadata();
		int range = 80;
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
		
		boolean looking = false;
		for(EntityPlayer player : players) {
			ItemStack helm = player.getCurrentArmor(3);
			if(helm != null && helm.getItem() == Item.getItemFromBlock(Blocks.pumpkin))
				continue;
			
			MovingObjectPosition pos = ToolCommons.raytraceFromEntity(worldObj, player, true, 64);
			if(pos != null && pos.blockX == xCoord && pos.blockY == yCoord && pos.blockZ == zCoord) {
				looking = true;
				break;
			}
		}
		
		int newMeta = looking ? 15 : 0;
		if(newMeta != meta)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta, 1 | 2);
	}
	
}
