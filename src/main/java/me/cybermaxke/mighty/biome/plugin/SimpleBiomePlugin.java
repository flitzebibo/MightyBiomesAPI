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

import me.cybermaxke.mighty.biome.api.Biomes;
import me.cybermaxke.mighty.biome.plugin.entity.SimpleEntityRegister;

import org.bukkit.plugin.java.JavaPlugin;

public class SimpleBiomePlugin extends JavaPlugin {
	private static SimpleBiomePlugin instance;

	private SimpleBiomeRegister biomeRegister;
	private SimpleEntityRegister entityRegister;

	public SimpleBiomePlugin() {
		SimpleBiomePlugin.instance = this;
	}

	@Override
	public void onEnable() {
		this.biomeRegister = new SimpleBiomeRegister();
		this.biomeRegister.load();

		this.entityRegister = new SimpleEntityRegister();
		this.entityRegister.load();

		Biomes.set(this.biomeRegister);
	}

	@Override
	public void onDisable() {
		this.biomeRegister.clean();
		this.biomeRegister = null;

		this.entityRegister = null;

		Biomes.set(null);
	}

	public SimpleBiomeRegister getBiomeRegister() {
		return this.biomeRegister;
	}

	public SimpleEntityRegister getEntityRegister() {
		return this.entityRegister;
	}

	public static SimpleBiomePlugin get() {
		return SimpleBiomePlugin.instance;
	}
}