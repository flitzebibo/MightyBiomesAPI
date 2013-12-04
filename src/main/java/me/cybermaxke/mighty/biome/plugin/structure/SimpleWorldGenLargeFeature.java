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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.cybermaxke.mighty.biome.api.Biomes;

import net.minecraft.server.v1_7_R1.BiomeBase;
import net.minecraft.server.v1_7_R1.StructureStart;
import net.minecraft.server.v1_7_R1.World;
import net.minecraft.server.v1_7_R1.WorldGenLargeFeature;
import net.minecraft.server.v1_7_R1.WorldGenLargeFeatureStart;
import net.minecraft.server.v1_7_R1.WorldGenWitchHut;

public class SimpleWorldGenLargeFeature extends WorldGenLargeFeature {

	@Override
	public boolean a(int x, int z) {
		try {
			Field field = WorldGenLargeFeature.class.getDeclaredField("e");
			field.setAccessible(true);

			Field mfield = Field.class.getDeclaredField("modifiers");
			mfield.setAccessible(true);
			mfield.set(field, field.getModifiers() & ~Modifier.FINAL);

			List<BiomeBase> list = new ArrayList<BiomeBase>();

			for (me.cybermaxke.mighty.biome.api.BiomeBase biome : Biomes.get().getAll()) {
				if (biome.isGeneratingWitchHouse() ||
						biome.isGeneratingJungleTemple() ||
						biome.isGeneratingPyramid()) {
					list.add(BiomeBase.n()[biome.getId()]);
				}
			}

			field.set(null, list);
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

	@Override
	public boolean a(World world, Random random, int x, int z) {
		try {
			return super.a(world, random, x, z);
		} catch (Exception e) {
			return false;
		}
	}
}