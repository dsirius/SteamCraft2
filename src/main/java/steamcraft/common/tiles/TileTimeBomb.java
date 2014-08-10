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
package steamcraft.common.tiles;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import steamcraft.common.entities.EntityTimeBomb;

public class TileTimeBomb extends TileEntity
{
	private int time;

	@Override
	public void updateEntity()
	{
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		System.out.println(time);
		if (Integer.parseInt(sdf.format(cal.getTime())) == time)
		{
			System.out.print("BANG!");
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			worldObj.spawnEntityInWorld(new EntityTimeBomb(worldObj, xCoord, yCoord, zCoord, null, 0));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		setTime(nbt.getInteger("time"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("time", getTime());
	}

	public int getTime()
	{
		return time;
	}

	public void setTime(int time)
	{
		this.time = time;
	}
}