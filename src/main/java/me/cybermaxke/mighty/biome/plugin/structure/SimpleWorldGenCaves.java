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
package me.cybermaxke.mighty.biome.plugin.structure;

import me.cybermaxke.mighty.biome.api.BiomeBase;
import me.cybermaxke.mighty.biome.api.Biomes;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.IChunkProvider;
import net.minecraft.server.v1_7_R1.World;
import net.minecraft.server.v1_7_R1.WorldGenCaves;

public class SimpleWorldGenCaves extends WorldGenCaves {

	@Override
	public void a(IChunkProvider provider, World world, int x, int z, Block[] data) {
		BiomeBase biome = Biomes.get().get(world.getBiome(x, z).id);

		if (biome.isGeneratingCaves()) {
			super.a(provider, world, x, z, data);
		}
	}
}