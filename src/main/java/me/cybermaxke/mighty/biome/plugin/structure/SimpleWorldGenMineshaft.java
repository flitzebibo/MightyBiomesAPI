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

import java.util.Map;

import me.cybermaxke.mighty.biome.api.BiomeBase;
import me.cybermaxke.mighty.biome.api.Biomes;
import net.minecraft.server.v1_6_R3.WorldGenMineshaft;

public class SimpleWorldGenMineshaft extends WorldGenMineshaft {

	public SimpleWorldGenMineshaft() {

	}

	public SimpleWorldGenMineshaft(Map<String, String> map) {
		super(map);
	}

	@Override
	public boolean a(int x, int z) {
		BiomeBase biome = Biomes.get().getWorld(this.c.getWorld()).get(x, z);
		return biome.isGeneratingMineshaft() && super.a(x, z);
	}
}