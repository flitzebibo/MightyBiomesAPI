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
import java.util.List;

import org.bukkit.entity.LivingEntity;

import me.cybermaxke.mighty.biome.api.Biome;
import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;

import net.minecraft.server.v1_6_R2.BiomeBase;
import net.minecraft.server.v1_6_R2.BiomeMeta;
import net.minecraft.server.v1_6_R2.WeightedRandomChoice;
import net.minecraft.server.v1_6_R2.WorldGenVillage;

public class SimpleBiome implements Biome {
	private final BiomeBase biome;

	public SimpleBiome(BiomeBase biome) {
		this.biome = biome;
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
		this.getMeta(type).clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<me.cybermaxke.mighty.biome.api.BiomeMeta> getSpawns(EnumCreatureType type) {
		List<me.cybermaxke.mighty.biome.api.BiomeMeta> spawns =
				new ArrayList<me.cybermaxke.mighty.biome.api.BiomeMeta>();

		try {
			Field field = WeightedRandomChoice.class.getDeclaredField("a");
			field.setAccessible(true);

			for (BiomeMeta meta : this.getMeta(type)) {
				Class<?> entity = SimpleBiomePlugin.get().getEntityRegister()
						.getBukkitEntity(meta.b);
				int weight = field.getInt(meta);
				me.cybermaxke.mighty.biome.api.BiomeMeta meta1 =
						new me.cybermaxke.mighty.biome.api.BiomeMeta(
								(Class<? extends LivingEntity>) entity, weight, meta.c, meta.d);
				spawns.add(meta1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return spawns;
	}

	@Override
	public void addSpawn(EnumCreatureType type, me.cybermaxke.mighty.biome.api.BiomeMeta meta) {
		Class<?> entity = SimpleBiomePlugin.get().getEntityRegister().getMcEntity(meta.getEntity());
		BiomeMeta meta1 = new BiomeMeta(entity, meta.getWeight(), meta.getMinGroupCount(),
				meta.getMaxGroupCount());
		this.getMeta(type).add(meta1);
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

	@SuppressWarnings("unchecked")
	public List<BiomeMeta> getMeta(EnumCreatureType type) {
		try {
			return (List<BiomeMeta>) this.getField(type).get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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