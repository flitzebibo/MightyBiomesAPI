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

import net.minecraft.server.v1_7_R1.GenLayer;
import net.minecraft.server.v1_7_R1.GenLayerMushroomIsland;

public class SimpleGenLayerDeepOcean extends GenLayerMushroomIsland {
	private final SimpleGenData data;

	public SimpleGenLayerDeepOcean(long baseSeed, GenLayer layer, SimpleGenData data) {
		super(baseSeed, layer);
		this.data = data;
	}

	@Override
	public int[] a(int x, int z, int height, int width) {
		if (this.data.getBiomes().contains(BiomeBase.DEEP_OCEAN)) {
			return super.a(x, z, height, width);
		}

		return this.a.a(x, z, height, width);
	}
}