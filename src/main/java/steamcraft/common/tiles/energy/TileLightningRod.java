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
package steamcraft.common.tiles.energy;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import steamcraft.common.config.ConfigBalance;
import steamcraft.common.config.ConfigGeneral;
import steamcraft.common.entities.living.EntityFleshGolem;
import steamcraft.common.init.InitBlocks;

/**
 * @author warlordjones, MrIbby
 *
 */
public class TileLightningRod extends TileEntity implements IEnergyProvider
{
	private static int RFOutPerTick = 10000;

	private static final ArrayList<EntityLightningBolt> unnaturalLightningBolts = new ArrayList<EntityLightningBolt>();
	private static Class weather2Class;

	private final EnergyStorage buffer = new EnergyStorage(100000, RFOutPerTick);

	@Override
	public void updateEntity()
	{
		if (!this.worldObj.isRemote)
		{
			boolean isLightningSpawned = false;

			if (this.worldObj.getWorldInfo().isThundering() && this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord)
					&& !BiomeDictionary.isBiomeOfType(this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord), Type.SANDY)
					&& ConfigGeneral.unnaturalLightningStrikes)
			{
				Random random = this.worldObj.rand;
				int chance = random.nextInt(ConfigBalance.lightningRodHitChance);
				if (chance == 0)
				{
					EntityLightningBolt lightningBolt = new EntityLightningBolt(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
					unnaturalLightningBolts.add(lightningBolt);
					this.worldObj.addWeatherEffect(lightningBolt);
					this.buffer.modifyEnergyStored(ConfigBalance.lightningRodEnergyProduction);
					isLightningSpawned = true;
				}
			}

			if (ConfigGeneral.naturalLightningStrikes)
			{
				AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox((double) this.xCoord - 1, (double) this.yCoord - 1,
						(double) this.zCoord - 1, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
				List list = this.worldObj.getEntitiesWithinAABB(EntityLightningBolt.class, axisalignedbb);

				if (!list.isEmpty())
					isLightningSpawned = true;

				for (Object obj : list)
				{
					if (unnaturalLightningBolts.remove(obj))
						continue;
					this.buffer.modifyEnergyStored(ConfigBalance.lightningRodEnergyProduction);
				}
			}

			if (ConfigGeneral.weather2LightningStrikes)
			{
				if (weather2Class == null)
				{
					try
					{
						weather2Class = Class.forName("weather2.entity.EntityLightningBolt");
					} catch (ClassNotFoundException exception)
					{
					}
				}

				if (weather2Class != null)
				{
					AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox((double) this.xCoord - 1, (double) this.yCoord - 1,
							(double) this.zCoord - 1, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
					List list = this.worldObj.getEntitiesWithinAABB(weather2Class, axisalignedbb);

					if (!list.isEmpty())
						isLightningSpawned = true;

					for (Object obj : list)
						this.buffer.modifyEnergyStored(ConfigBalance.lightningRodEnergyProduction);
				}
			}

			if (isLightningSpawned && (this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == InitBlocks.blockCopperWire)
					&& (this.worldObj.getBlock(this.xCoord, this.yCoord - 2, this.zCoord) == InitBlocks.blockFlesh)
					&& (this.worldObj.getBlock(this.xCoord, this.yCoord - 3, this.zCoord) == InitBlocks.blockFlesh))
			{
				EntityFleshGolem golem = new EntityFleshGolem(this.worldObj);
				golem.setPosition(this.xCoord, this.yCoord, this.zCoord);
				this.worldObj.spawnEntityInWorld(golem);
				this.worldObj.setBlockToAir(this.xCoord, this.yCoord - 1, this.zCoord);
				this.worldObj.setBlockToAir(this.xCoord, this.yCoord - 2, this.zCoord);
				this.worldObj.setBlockToAir(this.xCoord, this.yCoord - 3, this.zCoord);
			}
			if (this.buffer.getEnergyStored() > 0)
			{
				int usedEnergy = Math.min(this.buffer.getEnergyStored(), RFOutPerTick);
				int outputEnergy = usedEnergy;

				for (ForgeDirection dir : EnumSet.allOf(ForgeDirection.class))
					usedEnergy -= this.outputEnergy(dir, usedEnergy);

				this.buffer.modifyEnergyStored(usedEnergy - outputEnergy);
			}
		}
	}

	private int outputEnergy(ForgeDirection dir, int usedEnergy)
	{
		if (usedEnergy > 0)
		{
			TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

			if (tileEntity instanceof IEnergyReceiver)
				return ((IEnergyReceiver) tileEntity).receiveEnergy(dir.getOpposite(), usedEnergy, false);
		}
		return 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return this.buffer.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return this.buffer.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return this.buffer.getMaxEnergyStored();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.buffer.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		this.buffer.writeToNBT(tag);
	}

}
