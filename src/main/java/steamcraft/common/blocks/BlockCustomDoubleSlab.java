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
package steamcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import steamcraft.common.lib.ModInfo;

/**
 * @author warlordjones
 *
 */
public class BlockCustomDoubleSlab extends BlockSlab
{
	String type;
	Block block;
	Block singleslab;

	public BlockCustomDoubleSlab(String type, Block block, Material mat, Block singleslab)
	{
		super(true, mat);
		this.type = type;
		this.block = block;
		this.singleslab = singleslab;
		this.useNeighborBrightness = true;
	}

	@Override
	public String func_150002_b(int p_150002_1_)
	{
		return ModInfo.PREFIX + this.type;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return this.block.getIcon(side, meta);
	}

	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return Item.getItemFromBlock(this.singleslab);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return new ItemStack(this.singleslab, 1, world.getBlockMetadata(x, y, z));
	}
}
