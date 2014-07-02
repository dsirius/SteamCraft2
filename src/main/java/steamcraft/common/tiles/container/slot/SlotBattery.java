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
 * File created @ [Jul 2, 2014, 6:23:03 PM]
 */
package steamcraft.common.tiles.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import steamcraft.common.items.ItemElectricJar;
import steamcraft.common.tiles.TileBattery;

/**
 * @author decebaldecebal
 *
 */
public class SlotBattery extends Slot
{
	TileBattery tile;
	
	public SlotBattery(IInventory inv, int par2, int par3, int par4)
	{
		super(inv, par2, par3, par4);
		
		tile = (TileBattery) inv;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemElectricJar)
			return true;
		return false;
	}
	
	@Override
	public void onSlotChange(ItemStack stack1, ItemStack stack2)
	{
		super.onSlotChange(stack1, stack2);
		
		tile.updateEnergyFromInv();
	}
}