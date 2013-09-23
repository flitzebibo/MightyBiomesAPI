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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.GenLayer;
import net.minecraft.server.v1_6_R3.IntCache;

public class SimpleGenLayerHills extends GenLayer {
	private Map<Integer, Integer> hillBiomes = new HashMap<Integer, Integer>();
	private List<BiomeBase> biomes;

	public SimpleGenLayerHills(long baseSeed, GenLayer parent, List<BiomeBase> biomes) {
		super(baseSeed);
		this.a = parent;
		this.biomes = biomes;

		this.hillBiomes.put(BiomeBase.DESERT.id, BiomeBase.DESERT_HILLS.id);
		this.hillBiomes.put(BiomeBase.FOREST.id, BiomeBase.FOREST_HILLS.id);
		this.hillBiomes.put(BiomeBase.TAIGA.id, BiomeBase.TAIGA_HILLS.id);
		this.hillBiomes.put(BiomeBase.PLAINS.id, BiomeBase.FOREST.id);
		this.hillBiomes.put(BiomeBase.ICE_PLAINS.id, BiomeBase.ICE_MOUNTAINS.id);
		this.hillBiomes.put(BiomeBase.JUNGLE.id, BiomeBase.JUNGLE_HILLS.id);
	}

	/**
	 * Only apply the hill biomes if they are added in the biomes.
	 * TODO: Is there a better way?
	 */
	@Override
	public int[] a(int x, int z, int height, int width) {
		int[] array1 = this.a.a(x - 1, z - 1, height + 2, width + 2);
		int[] array2 = IntCache.a(height * width);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.a(j + x, i + z);
				int k = array1[j + 1 + (i + 1) * (height + 2)];

				if (this.a(3) == 0) {
					int m = k;

					if (this.hillBiomes.containsKey(k)) {
						int n = this.hillBiomes.get(k);

						if (this.biomes.contains(BiomeBase.biomes[n])) {
							m = n;
						}
					}

					if (m == k) {
						array2[j + i * height] = k;
					} else {
						int n = array1[j + 1 + (i + 1 - 1) * (height + 2)];
						int i1 = array1[j + 1 + 1 + (i + 1) * (height + 2)];
						int i2 = array1[j + 1 - 1 + (i + 1) * (height + 2)];
						int i3 = array1[j + 1 + (i + 1 + 1) * (height)];

						if (n == k && i1 == k && i2 == k && i3 == k) {
							array2[j + i * height] = m;
						} else {
							array2[j + i * height] = k;
						}
					}
				} else {
					array2[j + i * height] = k;
				}
			}
		}

		return array2;
	}
}