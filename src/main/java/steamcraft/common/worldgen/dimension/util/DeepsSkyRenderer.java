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
package steamcraft.common.worldgen.dimension.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.client.IRenderHandler;

import boilerplate.client.ClientHelper;
import org.lwjgl.opengl.GL11;
import steamcraft.common.lib.ModInfo;

public class DeepsSkyRenderer extends IRenderHandler
{
	private int starGLCallList;
	private int glSkyList;
	private final int glSkyList2;

	public DeepsSkyRenderer()
	{
		RenderGlobal renderGlobal = ClientHelper.mc().renderGlobal;
		this.glSkyList2 = this.glSkyList;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, WorldClient world, Minecraft mc)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Vec3 vec3 = world.getSkyColor(mc.renderViewEntity, partialTicks);
		float f1 = (float) vec3.xCoord;
		float f2 = (float) vec3.yCoord;
		float f3 = (float) vec3.zCoord;
		float f4;
		if (mc.gameSettings.anaglyph)
		{
			float f5 = ((f1 * 30.0F) + (f2 * 59.0F) + (f3 * 11.0F)) / 100.0F;
			float f6 = ((f1 * 30.0F) + (f2 * 70.0F)) / 100.0F;
			f4 = ((f1 * 30.0F) + (f3 * 70.0F)) / 100.0F;
			f1 = f5;
			f2 = f6;
			f3 = f4;
		}
		GL11.glColor3f(f1, f2, f3);
		Tessellator tessellator1 = Tessellator.instance;
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(f1, f2, f3);
		GL11.glCallList(this.glSkyList);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.disableStandardItemLighting();
		float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
		float f7;
		float f8;
		float f9;
		float f10;
		if (afloat != null)
		{
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glPushMatrix();
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			f4 = afloat[0];
			f7 = afloat[1];
			f8 = afloat[2];
			float f11;
			if (mc.gameSettings.anaglyph)
			{
				f9 = ((f4 * 30.0F) + (f7 * 59.0F) + (f8 * 11.0F)) / 100.0F;
				f10 = ((f4 * 30.0F) + (f7 * 70.0F)) / 100.0F;
				f11 = ((f4 * 30.0F) + (f8 * 70.0F)) / 100.0F;
				f4 = f9;
				f7 = f10;
				f8 = f11;
			}
			tessellator1.startDrawing(6);
			tessellator1.setColorRGBA_F(f4, f7, f8, afloat[3]);
			tessellator1.addVertex(0.0D, 100.0D, 0.0D);
			byte b0 = 16;
			tessellator1.setColorRGBA_F(afloat[0], afloat[1], afloat[2], 0.0F);
			for (int j = 0; j <= b0; ++j)
			{
				f11 = (j * (float) Math.PI * 2.0F) / b0;
				float f12 = MathHelper.sin(f11);
				float f13 = MathHelper.cos(f11);
				tessellator1.addVertex(f12 * 120.0F, f13 * 120.0F, -f13 * 40.0F * afloat[3]);
			}
			tessellator1.draw();
			GL11.glPopMatrix();
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glPushMatrix();
		f4 = 1.0F;
		f7 = 0.0F;
		f8 = 0.0F;
		f9 = 0.0F;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, f4);
		GL11.glTranslatef(f7, f8, f9);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		f10 = 15.0F; // Size of sun from center
		mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.PREFIX + "textures/misc/sun.png")); // Sun
																										// texture
																										// +
																										// texture
																										// path
		tessellator1.startDrawingQuads();
		tessellator1.addVertexWithUV((-f10), 100.0D, (-f10), 0.0D, 0.0D);
		tessellator1.addVertexWithUV(f10, 100.0D, (-f10), 1.0D, 0.0D);
		tessellator1.addVertexWithUV(f10, 100.0D, f10, 1.0D, 1.0D);
		tessellator1.addVertexWithUV((-f10), 100.0D, f10, 0.0D, 1.0D);
		tessellator1.draw(); // Draw sun
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		float f18 = world.getStarBrightness(partialTicks) * f4;
		if (f18 > 0.0F)
		{
			GL11.glColor4f(f18, f18, f18, f18);
			GL11.glCallList(this.starGLCallList);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.0F, 0.0F, 0.0F);
		double d0 = mc.thePlayer.getPosition(partialTicks).yCoord - world.getHorizon();
		if (d0 < 0.0D)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 12.0F, 0.0F);
			GL11.glCallList(this.glSkyList2);
			GL11.glPopMatrix();
			f8 = 1.0F;
			f9 = -((float) (d0 + 65.0D));
			f10 = -f8;
			tessellator1.startDrawingQuads();
			tessellator1.setColorRGBA_I(0, 255);
			tessellator1.addVertex((-f8), f9, f8);
			tessellator1.addVertex(f8, f9, f8);
			tessellator1.addVertex(f8, f10, f8);
			tessellator1.addVertex((-f8), f10, f8);
			tessellator1.addVertex((-f8), f10, (-f8));
			tessellator1.addVertex(f8, f10, (-f8));
			tessellator1.addVertex(f8, f9, (-f8));
			tessellator1.addVertex((-f8), f9, (-f8));
			tessellator1.addVertex(f8, f10, (-f8));
			tessellator1.addVertex(f8, f10, f8);
			tessellator1.addVertex(f8, f9, f8);
			tessellator1.addVertex(f8, f9, (-f8));
			tessellator1.addVertex((-f8), f9, (-f8));
			tessellator1.addVertex((-f8), f9, f8);
			tessellator1.addVertex((-f8), f10, f8);
			tessellator1.addVertex((-f8), f10, (-f8));
			tessellator1.addVertex((-f8), f10, (-f8));
			tessellator1.addVertex((-f8), f10, f8);
			tessellator1.addVertex(f8, f10, f8);
			tessellator1.addVertex(f8, f10, (-f8));
			tessellator1.draw();
		}
		if (world.provider.isSkyColored())
		{
			GL11.glColor3f(0.2F + 0.04F, (f2 * 0.2F) + 0.04F, (f3 * 0.6F) + 0.1F);
		}
		else
		{
			GL11.glColor3f(f1, f2, f3);
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, -((float) (d0 - 16.0D)), 0.0F);
		GL11.glCallList(this.glSkyList2);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);
	}

}
