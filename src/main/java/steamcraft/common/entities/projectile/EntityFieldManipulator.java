package steamcraft.common.entities.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityFieldManipulator extends EntityEnderPearl
{
	public EntityFieldManipulator(World p_i1782_1_)
	{
		super(p_i1782_1_);
	}

	public EntityFieldManipulator(World p_i1783_1_, EntityLivingBase p_i1783_2_)
	{
		super(p_i1783_1_, p_i1783_2_);
	}

	@SideOnly(Side.CLIENT)
	public EntityFieldManipulator(World p_i1784_1_, double p_i1784_2_, double p_i1784_4_, double p_i1784_6_)
	{
		super(p_i1784_1_, p_i1784_2_, p_i1784_4_, p_i1784_6_);
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
	{
		if(p_70184_1_.entityHit != null)
		{
			p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
		}

		for(int i = 0; i < 32; ++i)
		{
			this.worldObj.spawnParticle("portal", this.posX, this.posY + (this.rand.nextDouble() * 2.0D), this.posZ, this.rand.nextGaussian(), 0.0D,
					this.rand.nextGaussian());
		}

		if(!this.worldObj.isRemote)
		{
			if((this.getThrower() != null) && (this.getThrower() instanceof EntityPlayerMP))
			{
				EntityPlayerMP entityplayermp = (EntityPlayerMP) this.getThrower();

				if(entityplayermp.playerNetServerHandler.func_147362_b().isChannelOpen() && (entityplayermp.worldObj == this.worldObj))
				{
					EnderTeleportEvent event = new EnderTeleportEvent(entityplayermp, this.posX, this.posY, this.posZ, 5.0F);
					if(!MinecraftForge.EVENT_BUS.post(event))
					{ // Don't indent to lower patch size
						if(this.getThrower().isRiding())
						{
							this.getThrower().mountEntity((Entity) null);
						}

						this.getThrower().setPositionAndUpdate(event.targetX, event.targetY, event.targetZ);
						this.getThrower().fallDistance = 0.0F;
					}
				}
			}

			this.setDead();
		}
	}

	@Override
	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	protected float getGravityVelocity()
	{
		return 0.00001F;
	}
}