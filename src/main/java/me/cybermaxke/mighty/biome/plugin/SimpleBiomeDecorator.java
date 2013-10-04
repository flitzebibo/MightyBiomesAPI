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

import org.bukkit.Material;

import me.cybermaxke.mighty.biome.api.BiomeDecorator.Setting;
import me.cybermaxke.mighty.biome.api.BiomeMinableMeta;
import me.cybermaxke.mighty.biome.api.gen.WorldGen;
import me.cybermaxke.mighty.biome.plugin.gen.SimpleWorldGen;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.BiomeDecorator;
import net.minecraft.server.v1_6_R3.Block;
import net.minecraft.server.v1_6_R3.WorldGenDeadBush;
import net.minecraft.server.v1_6_R3.WorldGenLakes;
import net.minecraft.server.v1_6_R3.WorldGenLiquids;
import net.minecraft.server.v1_6_R3.WorldGenMinable;
import net.minecraft.server.v1_6_R3.WorldGenPumpkin;
import net.minecraft.server.v1_6_R3.WorldGenerator;

public class SimpleBiomeDecorator extends BiomeDecorator {
	private final WorldGenDeadBush deathBush = new WorldGenDeadBush(Block.DEAD_BUSH.id);
	private final WorldGenLiquids waterLiquids = new WorldGenLiquids(Block.WATER.id);
	private final WorldGenLiquids lavaLiquids = new WorldGenLiquids(Block.LAVA.id);
	private final WorldGenLakes waterLakes = new WorldGenLakes(Block.WATER.id);
	private final WorldGenLakes lavaLakes = new WorldGenLakes(Block.LAVA.id);
	private final WorldGenPumpkin pumpkin = new WorldGenPumpkin();

	private me.cybermaxke.mighty.biome.api.BiomeDecorator decorator;

	public SimpleBiomeDecorator(BiomeBase biome, 
			me.cybermaxke.mighty.biome.api.BiomeDecorator decorator) {
		super(biome);
		this.decorator = decorator;
	}

	@Override
	public void a() {
		if (this.decorator == null) {
			return;
		}

		this.decorator.onPreDecorate(this.a.getWorld(), this.b, this.c, this.d);

		/**
		 * Generating ores.
		 */
		for (BiomeMinableMeta meta : this.decorator.getMinables()) {
			for (int i = 0; i < meta.getGroupCount(); i++) {
				int min = meta.getMinHeight();
				int max = meta.getMaxHeight();
				int size = meta.getGroupSize();

				int x = this.c + this.b.nextInt(16);
				int y = this.b.nextInt(max - min) + min;
				int z = this.d + this.b.nextInt(16);

				WorldGenMinable gen = new WorldGenMinable(meta.getMaterial().getId(), size);
				gen.a(this.a, this.b, x, y, z);
			}
		}

		/**
		 * Generating flowers, sand, lakes, etc.
		 */
		for (int i = 0; i < this.decorator.get(Setting.WATER_LAKES); ++i) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(this.b.nextInt(240) + 8);

			this.waterLakes.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.LAVA_LAKES); ++i) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(this.b.nextInt(this.b.nextInt(112) + 8) + 8);

			this.lavaLakes.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.SAND_2); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;

			this.g.a(this.a, this.b, x, this.a.i(x, z), z);
		}

		for (int i = 0; i < this.decorator.get(Setting.CLAY); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;

			this.f.a(this.a, this.b, x, this.a.i(x, z), z);
		}

		for (int i = 0; i < this.decorator.get(Setting.SAND); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;

			this.g.a(this.a, this.b, x, this.a.i(x, z), z);
		}

		int trees = this.decorator.get(Setting.TREES);
		if (this.b.nextInt(10) == 0) {
			trees++;
		}

		for (int i = 0; i < trees; i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;

			WorldGen gen1 = this.decorator.getWorldGenTrees(this.b);

			WorldGenerator gen2 = gen1 == null ? this.e.a(this.b) : new SimpleWorldGen(gen1);
			gen2.a(1.0D, 1.0D, 1.0D);
			gen2.a(this.a, this.b, x, this.a.getHighestBlockYAt(x, z), z);
		}

		for (int i = 0; i < this.decorator.get(Setting.BIG_MUSHROOMS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;

			this.h.a(this.a, this.b, x, this.a.i(x, z), z);
		}

		for (int i = 0; i < this.decorator.get(Setting.YELLOW_FLOWERS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(128);
			int z = this.d + this.b.nextInt(16) + 8;

			this.q.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.RED_FLOWERS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(128);
			int z = this.d + this.b.nextInt(16) + 8;

			this.r.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.GRASS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(128);
			int z = this.d + this.b.nextInt(16) + 8;

			WorldGen gen1 = this.decorator.getWorldGenGrass(this.b);

			WorldGenerator gen2 = gen1 == null ? this.e.b(this.b) : new SimpleWorldGen(gen1);
			gen2.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.RED_FLOWERS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(128);
			int z = this.d + this.b.nextInt(16) + 8;

			this.r.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.DEATH_BUSH); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(128);
			int z = this.d + this.b.nextInt(16) + 8;

			this.deathBush.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.WATERLILY); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(128);
			int z = this.d + this.b.nextInt(16) + 8;

			while (y > 0 && this.a.getTypeId(x, y - 1, z) == 0) {
				y--;
			}

			this.x.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.BROWN_MUSHROOMS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;
			int y = this.a.getHighestBlockYAt(x, z);

			this.s.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.RED_MUSHROOMS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int z = this.d + this.b.nextInt(16) + 8;
			int y = this.a.getHighestBlockYAt(x, z);

			this.t.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.REEDS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(128);
			int z = this.d + this.b.nextInt(16) + 8;

			this.v.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.PUMPKINS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(128);
			int z = this.d + this.b.nextInt(16) + 8;

			this.pumpkin.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.CACTI); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(128);
			int z = this.d + this.b.nextInt(16) + 8;

			this.w.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.WATER_LIQUIDS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(this.b.nextInt(120) + 8);
			int z = this.d + this.b.nextInt(16) + 8;

			this.waterLiquids.a(this.a, this.b, x, y, z);
		}

		for (int i = 0; i < this.decorator.get(Setting.LAVA_LIQUIDS); i++) {
			int x = this.c + this.b.nextInt(16) + 8;
			int y = this.b.nextInt(this.b.nextInt(this.b.nextInt(112) + 8) + 8);
			int z = this.d + this.b.nextInt(16) + 8;

			this.lavaLiquids.a(this.a, this.b, x, y, z);
		}

		this.decorator.onDecorate(this.a.getWorld(), this.b, this.c, this.d);
	}

	public me.cybermaxke.mighty.biome.api.BiomeDecorator getHandle() {
		return this.decorator;
	}

	public void setHandle(me.cybermaxke.mighty.biome.api.BiomeDecorator decorator) {
		this.decorator = decorator;
	}

	/**
	 * Loads all the default values.
	 */
	public void init() {
		this.decorator.set(Setting.RED_FLOWERS, 1);
		this.decorator.set(Setting.YELLOW_FLOWERS, 2);
		this.decorator.set(Setting.GRASS, 1);
		this.decorator.set(Setting.SAND, 1);
		this.decorator.set(Setting.SAND_2, 3);
		this.decorator.set(Setting.CLAY, 1);

		this.decorator.addMinable(new BiomeMinableMeta(Material.DIRT,
				32, 20, 0, 128));
		this.decorator.addMinable(new BiomeMinableMeta(Material.GRAVEL,
				32, 10, 0, 128));
		this.decorator.addMinable(new BiomeMinableMeta(Material.COAL_ORE,
				16, 20, 0, 128));
		this.decorator.addMinable(new BiomeMinableMeta(Material.IRON_ORE,
				8, 20, 0, 64));
		this.decorator.addMinable(new BiomeMinableMeta(Material.GOLD_ORE,
				8, 2, 0, 32));
		this.decorator.addMinable(new BiomeMinableMeta(Material.REDSTONE_ORE,
				7, 8, 0, 16));
		this.decorator.addMinable(new BiomeMinableMeta(Material.DIAMOND_ORE,
				7, 1, 0, 16));
		this.decorator.addMinable(new BiomeMinableMeta(Material.LAPIS_ORE,
				6, 1, 16, 32));
	}

	/**
	 * Loads all the fields of the old decorator to this one.
	 * @param old
	 */
	public void init(BiomeDecorator old) {
		this.init();

		this.decorator.set(Setting.BIG_MUSHROOMS, this.getInt("J", old));
		this.decorator.set(Setting.CACTI, this.getInt("F", old));
		this.decorator.set(Setting.CLAY, this.getInt("I", old));
		this.decorator.set(Setting.DEATH_BUSH, this.getInt("C", old));
		this.decorator.set(Setting.GRASS, this.getInt("B", old));
		this.decorator.set(Setting.REEDS, this.getInt("E", old));
		this.decorator.set(Setting.SAND, this.getInt("G", old));
		this.decorator.set(Setting.SAND_2, this.getInt("H", old));
		this.decorator.set(Setting.TREES, this.getInt("z", old));
		this.decorator.set(Setting.WATERLILY, this.getInt("y", old));

		int flowers = this.getInt("A", old);
		int flowersRed = flowers / 4;

		this.decorator.set(Setting.RED_FLOWERS, flowersRed == 0 && flowers > 0 ? 1 :
			flowersRed);
		this.decorator.set(Setting.YELLOW_FLOWERS, flowers);

		int mushrooms = this.getInt("D", old);
		int mushroomsBrown = mushrooms / 4;
		int mushroomsRed = mushrooms / 8;

		this.decorator.set(Setting.RED_MUSHROOMS, mushroomsRed == 0 && mushrooms > 0 ? 1 :
			mushroomsRed);
		this.decorator.set(Setting.RED_MUSHROOMS, mushroomsBrown == 0 && mushrooms > 0 ? 1 :
			mushroomsBrown);
	}

	public int getInt(String fieldName, BiomeDecorator old) {
		try {
			Field field = BiomeDecorator.class.getDeclaredField(fieldName);
			field.setAccessible(true);

			return field.getInt(old);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
}