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

import java.util.List;
import java.util.Random;

import me.cybermaxke.mighty.biome.api.gen.WorldGen;
import me.cybermaxke.mighty.biome.plugin.gen.SimpleWorldGen;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.BiomeDecorator;
import net.minecraft.server.v1_6_R3.BiomeMeta;
import net.minecraft.server.v1_6_R3.EnumCreatureType;
import net.minecraft.server.v1_6_R3.WorldGenerator;

public class SimpleBiomeBase extends BiomeBase {
	private final me.cybermaxke.mighty.biome.api.BiomeBase biome;

	public SimpleBiomeBase(me.cybermaxke.mighty.biome.api.BiomeBase biome) {
		super(biome.getId());
		this.biome = biome;
		this.load();
	}

	public void load() {
		this.I = this.a();
		this.A = (byte) this.biome.getTopBlock().getId();
		this.B = (byte) this.biome.getFillingBlock().getId();
		this.D = this.biome.getMinHeight();
		this.E = this.biome.getMaxHeight();
		this.temperature = this.biome.getTemperature();
		this.J.clear();
		this.K.clear();
		this.L.clear();
		this.M.clear();
	}

	public me.cybermaxke.mighty.biome.api.BiomeBase getBiome() {
		return this.biome;
	}

	@Override
	public List<BiomeMeta> getMobs(EnumCreatureType type) {
		List<me.cybermaxke.mighty.biome.api.BiomeMeta> meta = null;

		switch (type) {
			case AMBIENT:
				meta = this.biome.getSpawns(
						me.cybermaxke.mighty.biome.api.data.EnumCreatureType.AMBIENT);
				break;
			case CREATURE:
				meta = this.biome.getSpawns(
						me.cybermaxke.mighty.biome.api.data.EnumCreatureType.CREATURE);
				break;
			case MONSTER:
				meta = this.biome.getSpawns(
						me.cybermaxke.mighty.biome.api.data.EnumCreatureType.MONSTER);
				break;
			case WATER_CREATURE:
				meta = this.biome.getSpawns(
						me.cybermaxke.mighty.biome.api.data.EnumCreatureType.WATER_CREATURE);
				break;
			default:
				break;
		}

		return new SimpleBiomeMetaList(meta);
	}

	@Override
	public BiomeDecorator a() {
		return this.biome == null ? null :
			new SimpleBiomeDecorator(this, this.biome.getNewDecorator());
	}

	@Override
	public WorldGenerator a(Random random) {
		WorldGen gen = this.biome.getWorldGenTrees(random);
		return gen == null ? super.a(random) : new SimpleWorldGen(gen);
	}

	@Override
	public WorldGenerator b(Random random) {
		WorldGen gen = this.biome.getWorldGenGrass(random);
		return gen == null ? super.b(random) : new SimpleWorldGen(gen);
	}
}