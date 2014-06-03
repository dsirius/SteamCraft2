/**
 * This class was created by <Surseance> or his SC2 development team.
 * This class is available as part of the Steamcraft 2 Mod for Minecraft.
 *
 * Steamcraft 2 is open-source and is distributed under the MMPL v1.0 License.
 * (http://www.mod-buildcraft.com/MMPL-1.0.txt)
 *
 * Steamcraft 2 is based on the original Steamcraft Mod created by Proloe.
 * Steamcraft (c) Proloe 2011
 * (http://www.minecraftforum.net/topic/251532-181-steamcraft-source-code-releasedmlv054wip/)
 *
 * File created @ [Apr 13, 2014, 7:31:28 PM]
 */
package steamcraft.common.items.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import steamcraft.common.Steamcraft;
import steamcraft.common.lib.LibInfo;
import steamcraft.common.lib.MaterialHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// TODO: Auto-generated Javadoc
/**
 * The Class ItemNormalArmor.
 *
 * @author Decebaldecebal
 */
public class ItemNormalArmor extends ItemArmor
{

	/** The material. */
	ArmorMaterial material;

	/**
	 * Instantiates a new item normal armor.
	 *
	 * @param id the id
	 * @param mat the mat
	 * @param renderIndex the render index
	 * @param armorType the armor type
	 */
	public ItemNormalArmor(ArmorMaterial mat,
			 int renderIndex,  int armorType)
	{
		super(mat, renderIndex, armorType);
		mat = material;
		setMaxStackSize(1);
		setCreativeTab(Steamcraft.tabSC2);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.item.ItemArmor#registerIcons(net.minecraft.client.renderer.texture.IIconRegister)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons( IIconRegister icon)
	{
		itemIcon = icon.registerIcon(LibInfo.PREFIX + "armor/"
				+ this.getUnlocalizedName().substring(5));
	}

	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getArmorTexture(net.minecraft.item.ItemStack, net.minecraft.entity.Entity, int, java.lang.String)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture( ItemStack is,  Entity entity,
			 int slot,  String type)
	{
		if (material == MaterialHelper.ETHERIUM_ARMOR)
		{
			return slot == 2 ? LibInfo.PREFIX + "textures/armor/etherium_2.png"
					: LibInfo.PREFIX + "textures/armor/etherium_1.png";
		}
		else
		{
			return slot == 2 ? LibInfo.PREFIX + "textures/armor/obsidian_2.png"
					: LibInfo.PREFIX + "textures/armor/obsidian_1.png";
		}
	}
}
