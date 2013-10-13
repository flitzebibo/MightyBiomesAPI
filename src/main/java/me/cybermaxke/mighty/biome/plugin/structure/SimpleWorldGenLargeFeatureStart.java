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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.cybermaxke.mighty.biome.api.BiomeBase;
import me.cybermaxke.mighty.biome.api.Biomes;

import net.minecraft.server.v1_6_R3.StructureStart;
import net.minecraft.server.v1_6_R3.World;
import net.minecraft.server.v1_6_R3.WorldGenJungleTemple;
import net.minecraft.server.v1_6_R3.WorldGenPyramidPiece;
import net.minecraft.server.v1_6_R3.WorldGenWitchHut;

public class SimpleWorldGenLargeFeatureStart extends StructureStart {

	public SimpleWorldGenLargeFeatureStart() {

	}

	public SimpleWorldGenLargeFeatureStart(World world, Random random, int x, int z) {
		super(x, z);

		int xx = x * 16 + 8;
		int zz = z * 16 + 8;

		BiomeBase biome = Biomes.get().getWorld(world.getWorld()).get(xx, zz);

		List<Structure> available = new ArrayList<Structure>();
		if (biome.isGeneratingJungleTemple()) {
			available.add(Structure.JUNGLE_TEMPLE);
		}
		if (biome.isGeneratingWitchHouse()) {
			available.add(Structure.WITCH_HUT);
		}
		if (biome.isGeneratingPyramid()) {
			available.add(Structure.PYRAMID);
		}

		Structure type = available.get(random.nextInt(available.size()));
		switch (type) {
			case JUNGLE_TEMPLE:
				this.a.add(new WorldGenJungleTemple(random, x * 16, z * 16));
				break;
			case PYRAMID:
				this.a.add(new WorldGenPyramidPiece(random, x * 16, z * 16));
				break;
			case WITCH_HUT:
				this.a.add(new WorldGenWitchHut(random, x * 16, z * 16));
				break;
			default:
				break;
		}

		this.c();
	}

	private enum Structure {
		JUNGLE_TEMPLE,
		WITCH_HUT,
		PYRAMID
	}
}