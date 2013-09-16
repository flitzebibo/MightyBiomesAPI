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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_6_R2.BiomeBase;
import net.minecraft.server.v1_6_R2.GenLayer;
import net.minecraft.server.v1_6_R2.IntCache;

public class SimpleGenLayerBiome extends GenLayer {
	private List<BiomeBase> biomes;

	public SimpleGenLayerBiome(long baseSeed, GenLayer parent, List<BiomeBase> biomes) {
		super(baseSeed);
		this.a = parent;
		this.biomes = new ArrayList<BiomeBase>(biomes);

		/**
		 * Removing hill biomes, they are already used in 'SimpleGenLayerHills'.
		 */
		this.biomes.removeAll(Arrays.asList(
				BiomeBase.DESERT_HILLS,
				BiomeBase.FOREST_HILLS,
				BiomeBase.TAIGA_HILLS,
				BiomeBase.ICE_MOUNTAINS,
				BiomeBase.JUNGLE_HILLS));
	}

	public List<BiomeBase> getBiomes() {
		return this.biomes;
	}

	@Override
	public int[] a(int x, int z, int height, int width) {
		int[] array1 = this.a.a(x, z, height, width);
		int[] array2 = IntCache.a(height * width);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.a(j + x, i + height);
				int k = array1[j + i * height];

				/**
				 * There isn't a biome assigned.
				 */
				if (k == 0) {
					array2[j + i * height] = 0;
				/**
				 * Already used a mushroom island biome. See 'GenLayerMushroomIsland'
				 */
				} else if (k == BiomeBase.MUSHROOM_ISLAND.id) {
					array2[j + i * height] = k;
				/**
				 * Chosing a random biome.
				 */
				} else {
					array2[j + i * height] = this.biomes.get(this.a(this.biomes.size())).id;
				}
			}
		}

		return array2;
	}
}