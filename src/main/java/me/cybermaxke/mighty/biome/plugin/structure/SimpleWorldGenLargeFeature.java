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
import java.util.List;

import me.cybermaxke.mighty.biome.api.Biomes;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.StructureStart;
import net.minecraft.server.v1_6_R3.WorldGenLargeFeature;
import net.minecraft.server.v1_6_R3.WorldGenLargeFeatureStart;
import net.minecraft.server.v1_6_R3.WorldGenStronghold;
import net.minecraft.server.v1_6_R3.WorldGenWitchHut;

public class SimpleWorldGenLargeFeature extends WorldGenLargeFeature {

	@Override
	public boolean a(int x, int z) {
		try {
			Field field = WorldGenStronghold.class.getDeclaredField("e");
			field.setAccessible(true);

			List<BiomeBase> list = (List<BiomeBase>) field.get(null);
			list.clear();

			for (me.cybermaxke.mighty.biome.api.BiomeBase biome : Biomes.get().getAll()) {
				if (biome.isGeneratingWitchHouse() ||
						biome.isGeneratingJungleTemple() ||
						biome.isGeneratingPyramid()) {
					list.add(BiomeBase.biomes[biome.getId()]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.a(x, z);
	}

	@Override
	public SimpleWorldGenLargeFeatureStart b(int x, int z) {
		return new SimpleWorldGenLargeFeatureStart(this.c, this.b, x, z);
	}

	@Override
	public boolean a(int x, int y, int z) {
		StructureStart start = this.c(x, y, z);

		if (start == null || !(start instanceof WorldGenLargeFeatureStart)) {
			return false;
		}

		try {
			Field field = StructureStart.class.getDeclaredField("a");
			field.setAccessible(true);

			List<?> list = (List<?>) field.get(null);
			if (list.isEmpty()) {
				return false;
			}

			return list.get(0) instanceof WorldGenWitchHut;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}