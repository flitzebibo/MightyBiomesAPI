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
package me.cybermaxke.mighty.biome.plugin.gen;

import java.util.Random;

import me.cybermaxke.mighty.biome.api.gen.WorldGen;

import net.minecraft.server.v1_7_R1.World;
import net.minecraft.server.v1_7_R1.WorldGenerator;

public class SimpleWorldGen extends WorldGenerator {
	private final WorldGen generator;

	public SimpleWorldGen(WorldGen generator) {
		this.generator = generator;
	}

	@Override
	public boolean a(World world, Random random, int x, int y, int z) {
		return this.generator.onGenerate(world.getWorld(), random, x, y, z);
	}
}