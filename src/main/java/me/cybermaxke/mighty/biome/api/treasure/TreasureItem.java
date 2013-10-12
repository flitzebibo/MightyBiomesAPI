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

import me.cybermaxke.mighty.biome.api.utils.WeightedRandomItem;

import org.bukkit.inventory.ItemStack;

public interface TreasureItem extends WeightedRandomItem {

	/**
	 * Gets the item of the treasure.
	 * @return item
	 */
	public ItemStack getItem();

	/**
	 * Sets the item of the treasure.
	 * @param item
	 */
	public void setItem(ItemStack item);

	/**
	 * Gets the weight of the treasure.
	 * @return weight
	 */
	public int getWeight();

	/**
	 * Sets the weight of the treasure.
	 * @param weight
	 */
	public void setWeight(int weight);

	/**
	 * Gets the minimum amount of the treasure item.
	 * @return minCount
	 */
	public int getMinCount();

	/**
	 * Sets the minimum amount of the treasure item.
	 * @param minCount
	 */
	public void setMinCount(int minCount);

	/**
	 * Gets the maximum amount of the treasure item.
	 * @return maxCount
	 */
	public int getMaxCount();

	/**
	 * Sets the maximum amount of the treasure item.
	 * @param maxCount
	 */
	public void setMaxCount(int maxCount);

	/**
	 * Clones the treasure item.
	 * @return clone
	 */
	public TreasureItem clone();
}