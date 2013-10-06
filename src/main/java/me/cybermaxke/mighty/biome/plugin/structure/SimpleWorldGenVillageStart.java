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
import java.util.Random;

import net.minecraft.server.v1_6_R3.World;
import net.minecraft.server.v1_6_R3.WorldGenVillageStart;
import net.minecraft.server.v1_6_R3.WorldGenVillageStartPiece;

public class SimpleWorldGenVillageStart extends WorldGenVillageStart {

	public SimpleWorldGenVillageStart() {
		super();
	}

	public SimpleWorldGenVillageStart(World world, Random random, int x, int y, int z,
			boolean sandStoneVillage) {
		super(world, random, x, y, z);

		try {
			Field field = WorldGenVillageStartPiece.class.getDeclaredField("b");
			field.setAccessible(true);

			for (Object object : this.a) {
				if (object instanceof WorldGenVillageStartPiece) {
					field.set(object, sandStoneVillage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}