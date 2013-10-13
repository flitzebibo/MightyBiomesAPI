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

import org.bukkit.Material;

import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;
import me.cybermaxke.mighty.biome.api.decorator.Decorator;

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
	public boolean isGeneratingVillages() {
		return Biomes.get().get(this.id).isGeneratingVillages();
	}

	@Override
	public void setGeneratingVillages(boolean generate) {
		Biomes.get().get(this.id).setGeneratingVillages(generate);
	}

	@Override
	public boolean getSandstoneVillages() {
		return Biomes.get().get(this.id).getSandstoneVillages();
	}

	@Override
	public void setSandstoneVillages(boolean sandstoneVillages) {
		Biomes.get().get(this.id).setSandstoneVillages(sandstoneVillages);
	}

	@Override
	public boolean isGeneratingCanyons() {
		return Biomes.get().get(this.id).isGeneratingCanyons();
	}

	@Override
	public void setGeneratingCanyons(boolean canyons) {
		Biomes.get().get(this.id).setGeneratingCanyons(canyons);
	}

	@Override
	public boolean isGeneratingCaves() {
		return Biomes.get().get(this.id).isGeneratingCaves();
	}

	@Override
	public void setGeneratingCaves(boolean caves) {
		Biomes.get().get(this.id).setGeneratingCaves(caves);
	}

	@Override
	public boolean isGeneratingStronghold() {
		return Biomes.get().get(this.id).isGeneratingStronghold();
	}

	@Override
	public void setGeneratingStronghold(boolean stronghold) {
		Biomes.get().get(this.id).setGeneratingStronghold(stronghold);
	}

	@Override
	public boolean isGeneratingMineshaft() {
		return Biomes.get().get(this.id).isGeneratingMineshaft();
	}

	@Override
	public void setGeneratingMineshaft(boolean mineshaft) {
		Biomes.get().get(this.id).setGeneratingMineshaft(mineshaft);
	}

	@Override
	public boolean isGeneratingWitchHouse() {
		return Biomes.get().get(this.id).isGeneratingWitchHouse();
	}

	@Override
	public void setGeneratingWitchHouse(boolean witchHouse) {
		Biomes.get().get(this.id).setGeneratingWitchHouse(witchHouse);
	}

	@Override
	public boolean isGeneratingJungleTemple() {
		return Biomes.get().get(this.id).isGeneratingJungleTemple();
	}

	@Override
	public void setGeneratingJungleTemple(boolean jungleTemple) {
		Biomes.get().get(this.id).setGeneratingJungleTemple(jungleTemple);
	}

	@Override
	public boolean isGeneratingPyramid() {
		return Biomes.get().get(this.id).isGeneratingPyramid();
	}

	@Override
	public void setGeneratingPyramid(boolean pyramid) {
		Biomes.get().get(this.id).setGeneratingPyramid(pyramid);
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
	public Decorator getDecorator() {
		return Biomes.get().get(this.id).getDecorator();
	}

	@Override
	public void setDecorator(Decorator decorator) {
		Biomes.get().get(this.id).setDecorator(decorator);
	}

	@Override
	public boolean equals(Object o) {
		return Biomes.get().get(this.id).equals(o);
	}

	@Override
	public int hashCode() {
		return Biomes.get().get(this.id).hashCode();
	}
}