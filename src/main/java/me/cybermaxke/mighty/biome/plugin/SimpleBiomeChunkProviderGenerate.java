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

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.cybermaxke.mighty.biome.api.BiomeBase;
import me.cybermaxke.mighty.biome.api.BiomeMeta;
import me.cybermaxke.mighty.biome.api.Biomes;
import me.cybermaxke.mighty.biome.api.event.PreCreatureSpawnEvent;
import me.cybermaxke.mighty.biome.plugin.structure.SimpleWorldGenCanyon;
import me.cybermaxke.mighty.biome.plugin.structure.SimpleWorldGenCaves;
import me.cybermaxke.mighty.biome.plugin.structure.SimpleWorldGenVillage;
import me.cybermaxke.mighty.biome.plugin.utils.ReflectionUtils;

import net.minecraft.server.v1_6_R3.ChunkProviderGenerate;
import net.minecraft.server.v1_6_R3.EnumCreatureType;
import net.minecraft.server.v1_6_R3.World;
import net.minecraft.server.v1_6_R3.WorldGenCanyon;
import net.minecraft.server.v1_6_R3.WorldGenCaves;
import net.minecraft.server.v1_6_R3.WorldGenVillage;

public class SimpleBiomeChunkProviderGenerate extends ChunkProviderGenerate {
	private final World world;

	private final SimpleWorldGenVillage genVillage = new SimpleWorldGenVillage();
	private final SimpleWorldGenCaves genCaves = new SimpleWorldGenCaves();
	private final SimpleWorldGenCanyon genCanyon = new SimpleWorldGenCanyon();

	public SimpleBiomeChunkProviderGenerate(World world, ChunkProviderGenerate old) {
		this(world, world.getSeed(), world.getWorldData().shouldGenerateMapFeatures());

		try {
			ReflectionUtils.copyFieldObjects(ChunkProviderGenerate.class, old, this, true);

			Field field1 = ChunkProviderGenerate.class.getDeclaredField("v");
			field1.setAccessible(true);

			Field field2 = ChunkProviderGenerate.class.getDeclaredField("t");
			field2.setAccessible(true);

			Field field3 = ChunkProviderGenerate.class.getDeclaredField("y");
			field3.setAccessible(true);

			WorldGenVillage genVillageOld = (WorldGenVillage) field1.get(old);
			WorldGenCaves genCavesOld = (WorldGenCaves) field2.get(old);
			WorldGenCanyon genCanyonOld = (WorldGenCanyon) field3.get(old);

			ReflectionUtils.copyFieldObjects(WorldGenVillage.class, genVillageOld,
					this.genVillage, true);

			ReflectionUtils.copyFieldObjects(WorldGenVillage.class, genCavesOld,
					this.genCaves, true);

			ReflectionUtils.copyFieldObjects(WorldGenVillage.class, genCanyonOld,
					this.genCanyon, true);

			field1.set(this, this.genVillage);
			field2.set(this, this.genCaves);
			field3.set(this, this.genCanyon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SimpleBiomeChunkProviderGenerate(World world, long seed, boolean mapFeatures) {
		super(world, seed, mapFeatures);
		this.world = world;
	}

	@Override
	public SimpleBiomeMetaList getMobsFor(EnumCreatureType type, int x, int y, int z) {
		List<BiomeMeta> mobs = new ArrayList<BiomeMeta>();
		SimpleBiomeMetaList list = new SimpleBiomeMetaList(mobs, super.getMobsFor(type, x, y, z));

		Location location = new Location(this.world.getWorld(), x, y, z);
		BiomeBase biome = Biomes.get().get(this.world.getBiome(x, z).id);

		PreCreatureSpawnEvent event = new PreCreatureSpawnEvent(mobs, this.getType(type),
				location, biome);
		Bukkit.getPluginManager().callEvent(event);

		return event.isCancelled() ? null : list;
	}

	public me.cybermaxke.mighty.biome.api.data.EnumCreatureType getType(EnumCreatureType type) {
		switch (type) {
			case AMBIENT:
				return me.cybermaxke.mighty.biome.api.data.EnumCreatureType.AMBIENT;
			case MONSTER:
				return me.cybermaxke.mighty.biome.api.data.EnumCreatureType.MONSTER;
			case WATER_CREATURE:
				return me.cybermaxke.mighty.biome.api.data.EnumCreatureType.WATER_CREATURE;
			case CREATURE:
			default:
				return me.cybermaxke.mighty.biome.api.data.EnumCreatureType.CREATURE;
		}
	}

	public SimpleWorldGenVillage getVillageGen() {
		return this.genVillage;
	}
}