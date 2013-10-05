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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.bukkit.Material;

import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;

public class BiomeWrapper implements BiomeBase {
	private final int id;

	public BiomeWrapper(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public int getBiomeColor() {
		return Biomes.get().get(this.id).getBiomeColor();
	}

	@Override
	public void setBiomeColor(int color) {
		Biomes.get().get(this.id).setBiomeColor(color);
	}

	@Override
	public float getTemperature() {
		return Biomes.get().get(this.id).getTemperature();
	}

	@Override
	public void setTemperature(float temperature) {
		Biomes.get().get(this.id).setTemperature(temperature);
	}

	@Override
	public float getHumitidy() {
		return Biomes.get().get(this.id).getHumitidy();
	}

	@Override
	public void setHumitidy(float humidity) {
		Biomes.get().get(this.id).setHumitidy(humidity);
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
	public void setMinHeight(float height) {
		Biomes.get().get(this.id).setMinHeight(height);
	}

	@Override
	public float getMaxHeight() {
		return Biomes.get().get(this.id).getMaxHeight();
	}

	@Override
	public void setMaxHeight(float height) {
		Biomes.get().get(this.id).setMaxHeight(height);
	}

	@Override
	public Material getTopBlock() {
		return Biomes.get().get(this.id).getTopBlock();
	}

	@Override
	public void setTopBlock(Material material) {
		Biomes.get().get(this.id).setTopBlock(material);
	}

	@Override
	public Material getFillingBlock() {
		return Biomes.get().get(this.id).getFillingBlock();
	}

	@Override
	public void setFillingBlock(Material material) {
		Biomes.get().get(this.id).setFillingBlock(material);
	}

	@Override
	public boolean getGenerateVillages() {
		return Biomes.get().get(this.id).getGenerateVillages();
	}

	@Override
	public void setGenerateVillages(boolean generate) {
		Biomes.get().get(this.id).setGenerateVillages(generate);
	}

	@Override
	public boolean getSpawnable() {
		return Biomes.get().get(this.id).getSpawnable();
	}

	@Override
	public void setSpawnable(boolean spawnable) {
		Biomes.get().get(this.id).setSpawnable(spawnable);
	}

	@Override
	public BiomeDecorator getDecorator() {
		return Biomes.get().get(this.id).getDecorator();
	}

	@Override
	public void setDecorator(BiomeDecorator decorator) {
		Biomes.get().get(this.id).setDecorator(decorator);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BiomeBase)) {
			return false;
		}

		BiomeBase other = (BiomeBase) o;
		return new EqualsBuilder()
				.append(this.getId(), other.getId())
				.append(this.getMinHeight(), other.getMinHeight())
				.append(this.getMaxHeight(), other.getMaxHeight())
				.append(this.getTopBlock(), other.getTopBlock())
				.append(this.getFillingBlock(), other.getFillingBlock())
				.append(this.getTemperature(), other.getTemperature())
				.append(this.getHumitidy(), other.getHumitidy())
				.append(this.getGenerateVillages(), other.getGenerateVillages())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.getId())
				.append(this.getMinHeight())
				.append(this.getMaxHeight())
				.append(this.getTopBlock())
				.append(this.getFillingBlock())
				.append(this.getTemperature())
				.append(this.getHumitidy())
				.append(this.getGenerateVillages())
				.toHashCode();
	}
}