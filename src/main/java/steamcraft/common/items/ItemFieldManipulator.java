/**
 * This class was created by BrassGoggledCoders modding team.
 * This class is available as part of the Steamcraft 2 Mod for Minecraft.
 *
 * Steamcraft 2 is open-source and is distributed under the MMPL v1.0 License.
 * (http://www.mod-buildcraft.com/MMPL-1.0.txt)
 *
 * Steamcraft 2 is based on the original Steamcraft Mod created by Proloe.
 * Steamcraft (c) Proloe 2011
 * (http://www.minecraftforum.net/topic/251532-181-steamcraft-source-code-releasedmlv054wip/)
 *
 */
package steamcraft.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import steamcraft.common.entities.projectile.EntityFieldManipulator;

public class ItemFieldManipulator extends BaseItem
{
	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--stack.stackSize;
		}
		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / ((itemRand.nextFloat() * 0.4F) + 0.8F));

		if (!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityFieldManipulator(world, player));
		}

		return stack;
	}
}
