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
import java.util.Map;
import java.util.Random;

import me.cybermaxke.mighty.biome.api.BiomeBase;
import me.cybermaxke.mighty.biome.api.Biomes;

import net.minecraft.server.v1_6_R3.World;
import net.minecraft.server.v1_6_R3.WorldGenVillage;

public class SimpleWorldGenVillage extends WorldGenVillage {

	public SimpleWorldGenVillage() {
		super();
	}

	public SimpleWorldGenVillage(Map<String, Object> map) {
		super(map);
	}

	public int getSize() {
		try {
			Field field = WorldGenVillage.class.getDeclaredField("f");
			field.setAccessible(true);

			return field.getInt(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public SimpleWorldGenVillageStart b(int x, int z) {
		BiomeBase biome = Biomes.get().get(this.c.getBiome(x, z).id);

		return new SimpleWorldGenVillageStart(this.c, this.b, x, z, this.getSize(),
				biome.getSandstoneVillages());
	}

	/**
	 * Return 'true' to block lakes to be always generated.
	 */
	@Override
	public boolean a(World world, Random random, int x, int z) {
		return true;
	}
}