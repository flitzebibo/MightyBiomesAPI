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
package me.cybermaxke.mighty.biome.plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.BiomeDecorator;
import net.minecraft.server.v1_6_R3.BiomeMeta;
import net.minecraft.server.v1_6_R3.WorldGenVillage;

public class SimpleBiomeBase implements me.cybermaxke.mighty.biome.api.BiomeBase {
	private final Map<EnumCreatureType, List<me.cybermaxke.mighty.biome.api.BiomeMeta>> meta =
			new HashMap<EnumCreatureType, List<me.cybermaxke.mighty.biome.api.BiomeMeta>>();
	private final BiomeBase biome;

	private boolean spawnable = false;

	@SuppressWarnings("unchecked")
	public SimpleBiomeBase(BiomeBase biome) {
		this.biome = biome;
		this.getDecor();

		for (EnumCreatureType type : EnumCreatureType.values()) {
			List<me.cybermaxke.mighty.biome.api.BiomeMeta> list =
					new ArrayList<me.cybermaxke.mighty.biome.api.BiomeMeta>();

			this.meta.put(type, list);

			try {
				Field field = this.getField(type);
				field.setAccessible(true);

				List<BiomeMeta> list1 = (List<BiomeMeta>) this.getField(type).get(biome);
				List<BiomeMeta> list2 = new SimpleBiomeMetaList(list, list1);

				field.set(biome, list2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public SimpleBiomeDecorator getDecor() {
		if (this.biome.I instanceof SimpleBiomeDecorator) {
			return (SimpleBiomeDecorator) this.biome.I;
		}

		me.cybermaxke.mighty.biome.api.BiomeDecorator decorator1 =
				new me.cybermaxke.mighty.biome.api.BiomeDecorator();
		SimpleBiomeDecorator decorator2 = new SimpleBiomeDecorator(this.biome, decorator1);
		decorator2.init(this.biome.I);

		this.biome.I = decorator2;

		return decorator2;
	}

	@Override
	public int getId() {
		return this.biome.id;
	}

	@Override
	public int getBiomeColor() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBiomeColor(int color) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getTemperature() {
		return this.biome.temperature;
	}

	@Override
	public void setTemperature(float temperature) {
		this.biome.temperature = temperature;
	}

	@Override
	public float getHumitidy() {
		return this.biome.humidity;
	}

	@Override
	public void setHumitidy(float humidity) {
		this.biome.humidity = humidity;
	}

	@Override
	public void clearSpawns() {
		for (EnumCreatureType type : EnumCreatureType.values()) {
			this.clearSpawns(type);
		}
	}

	@Override
	public void clearSpawns(EnumCreatureType type) {
		this.meta.get(type).clear();
	}

	@Override
	public List<me.cybermaxke.mighty.biome.api.BiomeMeta> getSpawns(EnumCreatureType type) {
		return this.meta.get(type);
	}

	@Override
	public void addSpawn(EnumCreatureType type, me.cybermaxke.mighty.biome.api.BiomeMeta meta) {
		this.meta.get(type).add(meta);
	}

	@Override
	public float getMinHeight() {
		return this.biome.D;
	}

	@Override
	public void setMinHeight(float height) {
		this.biome.D = height;
	}

	@Override
	public float getMaxHeight() {
		return this.biome.E;
	}

	@Override
	public void setMaxHeight(float height) {
		this.biome.E = height;
	}

	@Override
	public Material getTopBlock() {
		return Material.getMaterial(this.biome.A);
	}

	@Override
	public void setTopBlock(Material material) {
		this.biome.A = (byte) material.getId();
	}

	@Override
	public Material getFillingBlock() {
		return Material.getMaterial(this.biome.B);
	}

	@Override
	public void setFillingBlock(Material material) {
		this.biome.B = (byte) material.getId();
	}

	@Override
	public boolean getGenerateVillages() {
		return WorldGenVillage.e.contains(this.biome);
	}

	@Override
	public void setGenerateVillages(boolean generate) {
		if (!this.getGenerateVillages() && generate) {
			WorldGenVillage.e.add(this.biome);
		} else if (this.getGenerateVillages() && !generate) {
			WorldGenVillage.e.remove(this.biome);
		}
	}

	@Override
	public boolean getSpawnable() {
		return this.spawnable;
	}

	@Override
	public void setSpawnable(boolean spawnable) {
		this.spawnable = spawnable;
	}

	@Override
	public me.cybermaxke.mighty.biome.api.BiomeDecorator getDecorator() {
		return this.getDecor().getHandle();
	}

	@Override
	public void setDecorator(me.cybermaxke.mighty.biome.api.BiomeDecorator decorator) {
		this.getDecor().setHandle(decorator);
	}

	public Field getField(EnumCreatureType type) {
		String fieldName = null;

		switch (type) {
			case AMBIENT:
				fieldName = "M";
				break;
			case MONSTER:
				fieldName = "J";
				break;
			case WATER_CREATURE:
				fieldName = "L";
				break;
			case CREATURE:
			default:
				fieldName = "K";
				break;
		}

		try {
			return BiomeBase.class.getDeclaredField(fieldName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}