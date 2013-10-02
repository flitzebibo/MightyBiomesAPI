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
package me.cybermaxke.mighty.biome.api.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class WeightedRandom {

	private WeightedRandom() {

	}

	/**
	 * Gets the total weight of a collection weighted items.
	 * @param collection
	 * @return weight
	 */
	public static <T extends WeightedRandomItem> int getTotalWeight(Collection<T> collection) {
		int weight = 0;

		Iterator<T> iter = collection.iterator();
		while (iter.hasNext()) {
			weight += iter.next().getWeight();
		}

		return weight;
	}

	/**
	 * Gets the total weight of a array weighted items.
	 * @param array
	 * @return weight
	 */
	public static <T extends WeightedRandomItem> int getTotalWeight(T... array) {
		return getTotalWeight(Arrays.asList(array));
	}

	/**
	 * Gets a random item from the collection that is balanced
	 * using the weight and the totalWeight.
	 * @param random
	 * @param collection
	 * @param totalWeight
	 * @return item
	 */
	public static <T extends WeightedRandomItem> T getRandomItem(Random random, Collection<T> collection,
			int totalWeight) {
		if (totalWeight <= 0) {
			throw new IllegalArgumentException("The total weight has to be greater then zero!");
		} else {
			int i = random.nextInt(totalWeight);
			Iterator<T> iter = collection.iterator();
			T item = null;

			while (i >= 0) {
				if (!iter.hasNext()) {
					return null;
				}

				item = iter.next();
				i -= item.getWeight();
			}

			return item;
		}
	}

	/**
	 * Gets a random item from the array that is balanced using the weight and the totalWeight.
	 * @param random
	 * @param array
	 * @param totalWeight
	 * @return item
	 */
	public static <T extends WeightedRandomItem> T getRandomItem(Random random, int totalWeight,
			T... array) {
		return getRandomItem(random, Arrays.asList(array), totalWeight);
	}

	/**
	 * Gets a random item from the collection that is balanced by the weight.
	 * @param random
	 * @param collection
	 * @return item
	 */
	public static <T extends WeightedRandomItem> T getRandomItem(Random random,
			Collection<T> collection) {
		return getRandomItem(random, collection, getTotalWeight(collection));
	}

	/**
	 * Gets a random item from the array that is balanced by the weight.
	 * @param random
	 * @param array
	 * @return item
	 */
	public static <T extends WeightedRandomItem> T getRandomItem(Random random, T... array) {
		return getRandomItem(random, Arrays.asList(array));
	}
}