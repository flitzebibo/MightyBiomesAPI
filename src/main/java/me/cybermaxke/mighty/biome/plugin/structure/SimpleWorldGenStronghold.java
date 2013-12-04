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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.cybermaxke.mighty.biome.api.Biomes;

import net.minecraft.server.v1_7_R1.BiomeBase;
import net.minecraft.server.v1_7_R1.World;
import net.minecraft.server.v1_7_R1.WorldGenStronghold;

public class SimpleWorldGenStronghold extends WorldGenStronghold {

	@Override
	public boolean a(int x, int z) {
		List<BiomeBase> biomes = new ArrayList<BiomeBase>();

		for (me.cybermaxke.mighty.biome.api.BiomeBase biome : Biomes.get().getAll()) {
			if (biome.isGeneratingStronghold()) {
				biomes.add(BiomeBase.n()[biome.getId()]);
			}
		}

		try {
			Field field = WorldGenStronghold.class.getDeclaredField("e");
			field.setAccessible(true);
			field.set(this, biomes.toArray(new BiomeBase[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.a(x, z);
	}

	@Override
	public boolean a(World world, Random random, int x, int z) {
		try {
			return super.a(world, random, x, z);
		} catch (Exception e) {
			return false;
		}
	}
}