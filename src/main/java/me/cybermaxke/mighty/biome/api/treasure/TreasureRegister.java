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
package me.cybermaxke.mighty.biome.api.treasure;

import java.util.Collection;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public interface TreasureRegister {

	/**
	 * Gets a new treasure item.
	 * @param item
	 * @param weight
	 * @param minCount
	 * @param maxCount
	 * @return treasure
	 */
	public TreasureItem getNew(ItemStack item, int weight, int minCount, int maxCount);

	/**
	 * Gets the treasures for the type.
	 * @param type
	 * @return treasures
	 */
	public List<TreasureItem> getTreasures(TreasureType type);

	/**
	 * Adds a treasure for the type.
	 * @param type
	 * @param item
	 */
	public void addTreasure(TreasureType type, TreasureItem item);

	/**
	 * Adds a collection of treasure for the type.
	 * @param type
	 * @param items
	 */
	public void addTreasures(TreasureType type, Collection<TreasureItem> items);
}