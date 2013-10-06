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
package me.cybermaxke.mighty.biome.api.decorator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DecoratorSetting {
	private final DecoratorSettingType type;

	private double chance;
	private int count;

	public DecoratorSetting(DecoratorSettingType type) {
		this(type, 100.0D, 0);
	}

	public DecoratorSetting(DecoratorSettingType type, double chance, int count) {
		this.type = type;
		this.chance = chance;
		this.count = count;
	}

	/**
	 * Gets the type of the setting.
	 * @return type
	 */
	public DecoratorSettingType getType() {
		return this.type;
	}

	/**
	 * Gets the chance. It's a value bewteen '0.0' and '100.0'.
	 * @return chance
	 */
	public double getChance() {
		return this.chance;
	}

	/**
	 * Sets the chance. It's a value bewteen '0.0' and '100.0'.
	 * @param chance
	 */
	public void setChance(double chance) {
		this.chance = chance;
	}

	/**
	 * Gets the amount of times that the decorator will try to generate.
	 * The chance is diffirent for every try.
	 * @return count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * Sets the amount of times that the decorator will try to generate.
	 * The chance is diffirent for every try.
	 * @param count
	 */
	public void setCount(int count) {
		this.chance = count;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DecoratorSetting)) {
			return false;
		}

		DecoratorSetting other = (DecoratorSetting) o;
		return new EqualsBuilder()
				.append(this.type, other.type)
				.append(this.chance, other.chance)
				.append(this.count, other.count)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.type)
				.append(this.chance)
				.append(this.count)
				.toHashCode();
	}
}