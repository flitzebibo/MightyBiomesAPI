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
package me.cybermaxke.mighty.biome.api.gen;

import java.util.Random;

import org.bukkit.TreeType;
import org.bukkit.World;

public class WorldGenTree implements WorldGen {
	private final TreeType treeType;

	public WorldGenTree(TreeType treeType) {
		this.treeType = treeType;
	}

	@Override
	public boolean onGenerate(World world, Random random, int x, int y, int z) {
		return world.generateTree(world.getBlockAt(x, y, z).getLocation(), this.treeType);
	}
}