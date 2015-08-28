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

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import boilerplate.client.ClientHelper;
import steamcraft.common.Steamcraft;
import steamcraft.common.lib.ModInfo;

/**
 * @author warlordjones
 *
 */
public class ItemTimeClock extends BaseItem
{
	private IIcon readyIcon;

	public ItemTimeClock()
	{
		this.setMaxStackSize(1);
		this.setCreativeTab(Steamcraft.tabSC2);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 32;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useCount)
	{
		if(!world.isRemote)
		{
			if(useCount <= 5)
			{
				if(ClientHelper.isShiftKeyDown())
				{
					ChatComponentText text = new ChatComponentText("Time set to Night");
					player.addChatComponentMessage(text);
					world.setWorldTime(13000);
					player.playSound(ModInfo.PREFIX + "warp", 1, 1);
					player.inventory.consumeInventoryItem(Items.ender_eye);
				}
				else
				{
					ChatComponentText text = new ChatComponentText("Time set to Day");
					player.addChatComponentMessage(text);
					world.setWorldTime(1000);
					player.playSound(ModInfo.PREFIX + "warp", 1, 1);
					player.inventory.consumeInventoryItem(Items.ender_eye);
				}
			}
			else
				player.playSound("minecraft:mob.wolf.whine", 1, 1);
		}
	}

	@Override
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
	{
		return p_77654_1_;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(player.inventory.hasItem(Items.ender_eye))
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));

		return stack;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int use)
	{
		if(use > 70000)
		{
			return this.readyIcon;
		}
		else
			return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister ir)
	{
		this.itemIcon = ir.registerIcon(ModInfo.PREFIX + "itemTimeClock");
		this.readyIcon = ir.registerIcon(ModInfo.PREFIX + "itemTimeClockReady");
	}
}
