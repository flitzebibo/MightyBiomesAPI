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

import me.cybermaxke.mighty.biome.api.Biome;
import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.BiomeMeta;
import net.minecraft.server.v1_6_R3.WorldGenVillage;

public class SimpleBiome implements Biome {
	private final Map<EnumCreatureType, List<me.cybermaxke.mighty.biome.api.BiomeMeta>> meta =
			new HashMap<EnumCreatureType, List<me.cybermaxke.mighty.biome.api.BiomeMeta>>();
	private final BiomeBase biome;

	@SuppressWarnings("unchecked")
	public SimpleBiome(BiomeBase biome) {
		this.biome = biome;

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

	@Override
	public int getId() {
		return this.biome.id;
	}

	@Override
	public float getTemperature() {
		return this.biome.temperature;
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
	public float getMaxHeight() {
		return this.biome.E;
	}

	@Override
	public int getTopBlock() {
		return this.biome.A;
	}

	@Override
	public int getFillingBlock() {
		return this.biome.B;
	}

	@Override
	public boolean canGenerateVillages() {
		return WorldGenVillage.e.contains(this.biome);
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