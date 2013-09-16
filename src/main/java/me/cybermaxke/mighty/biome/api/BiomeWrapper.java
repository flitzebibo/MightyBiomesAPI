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
package me.cybermaxke.mighty.biome.api;

import java.util.List;

import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;

public class BiomeWrapper implements Biome {
	private final int id;

	public BiomeWrapper(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public float getTemperature() {
		return Biomes.get().get(this.id).getTemperature();
	}

	@Override
	public void clearSpawns() {
		Biomes.get().get(this.id).clearSpawns();
	}

	@Override
	public void clearSpawns(EnumCreatureType type) {
		Biomes.get().get(this.id).clearSpawns(type);
	}

	@Override
	public List<BiomeMeta> getSpawns(EnumCreatureType type) {
		return Biomes.get().get(this.id).getSpawns(type);
	}

	@Override
	public void addSpawn(EnumCreatureType type, BiomeMeta meta) {
		Biomes.get().get(this.id).addSpawn(type, meta);
	}

	@Override
	public float getMinHeight() {
		return Biomes.get().get(this.id).getMinHeight();
	}

	@Override
	public float getMaxHeight() {
		return Biomes.get().get(this.id).getMaxHeight();
	}

	@Override
	public int getTopBlock() {
		return Biomes.get().get(this.id).getTopBlock();
	}

	@Override
	public int getFillingBlock() {
		return Biomes.get().get(this.id).getFillingBlock();
	}

	@Override
	public boolean canGenerateVillages() {
		return Biomes.get().get(this.id).canGenerateVillages();
	}
}