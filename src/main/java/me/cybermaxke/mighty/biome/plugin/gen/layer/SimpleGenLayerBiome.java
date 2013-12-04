/**
 * 
 * This software is part of the MightyBiomesAPI
 * 
 * Copyright (c) 2013 Cybermaxke
 * 
 * MightyBiomesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * MightyBiomesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MightyBiomesAPI. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.mighty.biome.plugin.gen.layer;

import me.cybermaxke.mighty.biome.api.BiomeBase;
import me.cybermaxke.mighty.biome.api.Biomes;

import net.minecraft.server.v1_7_R1.GenLayer;
import net.minecraft.server.v1_7_R1.IntCache;

/**
 * TODO: No idea how everything is working in 1.7.2...
 * Any help would be appreciated! :)
 */
public class SimpleGenLayerBiome extends GenLayer {
	private final SimpleGenData data;

	public SimpleGenLayerBiome(long baseSeed, GenLayer parent, SimpleGenData data) {
		super(baseSeed);
		this.a = parent;
		this.data = data;
	}

	@Override
	public int[] a(int x, int z, int height, int width) {
		int[] array1 = this.a.a(x, z, height, width);
		int[] array2 = IntCache.a(height * width);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				GenLayer.a(j + x, i + z);

				int k = array1[j + i * height] & -3841;
				int m = (k & 0xF00) >> 8;

				if (Biomes.get().get(k).isOcean()) {
					array2[j + i * height] = k;
				} else if (k == BiomeBase.MUSHROOM_ISLAND.getId()) {
					array2[j + i * height] = k;
				} else if (k == 1) {
					if (m > 0) {
						if (this.a(3) == 0) {
							array2[j + i * height] = BiomeBase.MESA_PLATEAU.getId();
						} else {
							array2[j + i * height] = BiomeBase.MESA_PLATEAU_F.getId();
						}
					} else {
						//array2[j + i * height] = this.c[a(this.c.length)].getId();
					}
				} else if (k == 2) {
					if (m > 0 && this.data.getBiomes().contains(BiomeBase.JUNGLE)) {
						array2[j + i * height] = BiomeBase.JUNGLE.getId();
					} else {
						//array2[j + i * height] = this.d[a(this.d.length)].getId();
					}
				} else if (k == 3) {
					if (m > 0 && this.data.getBiomes().contains(BiomeBase.MEGA_TAIGA)) {
						array2[j + i * height] = BiomeBase.MEGA_TAIGA.getId();
					} else {
						//array2[j + i * height] = this.e[a(this.e.length)].getId();
					}
				} else if (k == 4) {
					//array2[j + i * height] = this.f[a(this.f.length)].getId();
				} else {
					array2[j + i * height] = BiomeBase.MUSHROOM_ISLAND.getId();
				}
			}
		}
		return array2;
	}
/**
	public List<BiomeBase> getBiomes(float minTemperature, float maxTemperature) {
		
	}*/

	/**
	@Override
	public int[] a(int x, int z, int height, int width) {
		/**
		 * Removing hill biomes, they are already used in 'SimpleGenLayerHills'.
		 *//**
		List<BiomeBase> biomes = new ArrayList<BiomeBase>(this.biomes);
		biomes.removeAll(Arrays.asList(
				BiomeBase.DESERT_HILLS,
				BiomeBase.FOREST_HILLS,
				BiomeBase.TAIGA_HILLS,
				BiomeBase.ICE_MOUNTAINS,
				BiomeBase.JUNGLE_HILLS,
				BiomeBase.MUSHROOM_ISLAND,
				BiomeBase.MUSHROOM_SHORE,
				BiomeBase.BEACH,
				BiomeBase.OCEAN,
				BiomeBase.DEEP_OCEAN));

		int[] array1 = this.a.a(x, z, height, width);
		int[] array2 = IntCache.a(height * width);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.a(j + x, i + z);
				int k = array1[j + i * height];

				/**
				 * There isn't a biome assigned.
				 *//**
				if (k == 0) {
					array2[j + i * height] = 0;
				/**
				 * Already used a mushroom island biome. See 'GenLayerMushroomIsland'
				 *//**
				} else if (k == BiomeBase.MUSHROOM_ISLAND.getId()) {
					array2[j + i * height] = k;
				/**
				 * Chosing a random biome.
				 *//**
				} else {
					array2[j + i * height] = biomes.get(this.a(biomes.size())).getId();
				}
			}
		}

		return array2;
	}*/
}