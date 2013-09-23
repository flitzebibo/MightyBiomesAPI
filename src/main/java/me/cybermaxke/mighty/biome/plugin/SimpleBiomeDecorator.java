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

import me.cybermaxke.mighty.biome.api.BiomeDecorator.Setting;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.BiomeDecorator;
import net.minecraft.server.v1_6_R3.Block;
import net.minecraft.server.v1_6_R3.WorldGenLakes;

public class SimpleBiomeDecorator extends BiomeDecorator {
	private final me.cybermaxke.mighty.biome.api.BiomeDecorator decorator;

	private int waterLakesPerChunk = 1;
	private int lavaLakesPerChunk = 1;

	public SimpleBiomeDecorator(BiomeBase biome, 
			me.cybermaxke.mighty.biome.api.BiomeDecorator decorator) {
		super(biome);
		this.decorator = decorator;
	}

	@Override
	public void a() {
		this.load();

		for (int i = 0; i < this.waterLakesPerChunk; ++i) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(this.b.nextInt(240) + 8);

			new WorldGenLakes(Block.WATER.id).a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.lavaLakesPerChunk; ++i) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(this.b.nextInt(this.b.nextInt(112) + 8) + 8);

			new WorldGenLakes(Block.LAVA.id).a(this.a, this.b, x, y, z);
		}

		this.decorator.onDecorate(this.a.getWorld(), this.b, this.c, this.d);
		super.a();
	}

	/**
	 * Loads all the settings into the generator.
	 */
	public void load() {
		this.K = false;

		for (Setting setting : Setting.values()) {
			switch (setting) {
				case BIG_MUSHROOMS:
					this.J = this.decorator.get(setting, Integer.class);
					break;
				case CACTI:
					this.F = this.decorator.get(setting, Integer.class);
					break;
				case CLAY:
					this.I = this.decorator.get(setting, Integer.class);
					break;
				case DEATH_BUSH:
					this.C = this.decorator.get(setting, Integer.class);
					break;
				case FLOWERS:
					this.A = this.decorator.get(setting, Integer.class);
					break;
				case GRASS:
					this.B = this.decorator.get(setting, Integer.class);
					break;
				case MUSHROOMS:
					this.D = this.decorator.get(setting, Integer.class);
					break;
				case REEDS:
					this.E = this.decorator.get(setting, Integer.class);
					break;
				case SAND:
					this.G = this.decorator.get(setting, Integer.class);
					break;
				case SAND_2:
					this.H = this.decorator.get(setting, Integer.class);
					break;
				case TREES:
					this.z = this.decorator.get(setting, Integer.class);
					break;
				case WATERLILY:
					this.y = this.decorator.get(setting, Integer.class);
					break;
				case WATER_LAKES:
					this.waterLakesPerChunk = this.decorator.get(setting, Integer.class);
					break;
				case LAVA_LAKES:
					this.lavaLakesPerChunk = this.decorator.get(setting, Integer.class);
					break;
				default:
					break;
			}
		}
	}
}