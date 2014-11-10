package steamcraft.common.dimension;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import steamcraft.common.InitBiomes;

public class GenLayerBiomesDeeps extends GenLayer
{
	protected BiomeGenBase[] allowedBiomes = { InitBiomes.biomeDepths, InitBiomes.biomeDepthsF, InitBiomes.biomeDepthsM, InitBiomes.biomeDepthsS,
			InitBiomes.biomeDepthsI, InitBiomes.biomeDepthsO };

	public GenLayerBiomesDeeps(long seed, GenLayer genlayer)
	{
		super(seed);
		this.parent = genlayer;
	}

	public GenLayerBiomesDeeps(long seed)
	{
		super(seed);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth)
	{
		int[] dest = IntCache.getIntCache(width * depth);

		for(int dz = 0; dz < depth; dz++)
		{
			for(int dx = 0; dx < width; dx++)
			{
				this.initChunkSeed(dx + x, dz + z);
				dest[(dx + dz * width)] = this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID;
			}
		}
		return dest;
	}
}
