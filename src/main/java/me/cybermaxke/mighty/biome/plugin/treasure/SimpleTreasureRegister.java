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
package me.cybermaxke.mighty.biome.plugin.treasure;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_6_R3.WorldGenDungeons;
import net.minecraft.server.v1_6_R3.WorldGenJungleTemple;
import net.minecraft.server.v1_6_R3.WorldGenMineshaftPieces;
import net.minecraft.server.v1_6_R3.WorldGenNetherPiece1;
import net.minecraft.server.v1_6_R3.WorldGenPyramidPiece;
import net.minecraft.server.v1_6_R3.WorldGenStrongholdChestCorridor;
import net.minecraft.server.v1_6_R3.WorldGenStrongholdLibrary;
import net.minecraft.server.v1_6_R3.WorldGenStrongholdRoomCrossing;
import net.minecraft.server.v1_6_R3.WorldGenVillageBlacksmith;

import org.bukkit.inventory.ItemStack;

import me.cybermaxke.mighty.biome.api.treasure.TreasureItem;
import me.cybermaxke.mighty.biome.api.treasure.TreasureRegister;
import me.cybermaxke.mighty.biome.api.treasure.TreasureType;

public class SimpleTreasureRegister implements TreasureRegister {
	private final Map<TreasureType, SimpleTreasureList> lists =
			new HashMap<TreasureType, SimpleTreasureList>();

	public void load() {
		try {
			Class<?> clazz1 = Class.forName(WorldGenNetherPiece1.class.getName()
					.replace("Piece1", "Piece"));
	
			Field field1 = clazz1.getDeclaredField("a");
			Field field2 = WorldGenMineshaftPieces.class.getDeclaredField("a");
			Field field3 = WorldGenStrongholdChestCorridor.class.getDeclaredField("a");
			Field field4 = WorldGenVillageBlacksmith.class.getDeclaredField("a");
			Field field5 = WorldGenPyramidPiece.class.getDeclaredField("i");
			Field field6 = WorldGenStrongholdLibrary.class.getDeclaredField("a");
			Field field7 = WorldGenStrongholdRoomCrossing.class.getDeclaredField("b");
			Field field8 = WorldGenJungleTemple.class.getDeclaredField("l");
			Field field9 = WorldGenDungeons.class.getDeclaredField("a");

			this.lists.put(TreasureType.NETHER_FORTRESS, new SimpleTreasureList(field1));
			this.lists.put(TreasureType.MINESHAFT, new SimpleTreasureList(field2));
			this.lists.put(TreasureType.STRONGHOLD_CHEST_CORRIDOR, new SimpleTreasureList(field3));
			this.lists.put(TreasureType.VILLAGE_BLACKSMITH, new SimpleTreasureList(field4));
			this.lists.put(TreasureType.PYRAMID, new SimpleTreasureList(field5));
			this.lists.put(TreasureType.STRONGHOLD_LIBRARY, new SimpleTreasureList(field6));
			this.lists.put(TreasureType.STRONGHOLD_ROOM_CROSSING, new SimpleTreasureList(field7));
			this.lists.put(TreasureType.JUNGLE_TEMPLE, new SimpleTreasureList(field8));
			this.lists.put(TreasureType.DUNGEONS, new SimpleTreasureList(field9));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public TreasureItem getNew(ItemStack item, int weight, int minCount, int maxCount) {
		return new SimpleTreasureItem(item, weight, minCount, maxCount);
	}

	@Override
	public List<TreasureItem> getTreasures(TreasureType type) {
		return this.lists.get(type);
	}

	@Override
	public void addTreasure(TreasureType type, TreasureItem item) {
		this.getTreasures(type).add(item);
	}

	@Override
	public void addTreasures(TreasureType type, Collection<TreasureItem> items) {
		this.getTreasures(type).addAll(items);
	}
}