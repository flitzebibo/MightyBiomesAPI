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
package me.cybermaxke.mighty.biome.plugin.entity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_6_R2.EntityBat;
import net.minecraft.server.v1_6_R2.EntityBlaze;
import net.minecraft.server.v1_6_R2.EntityCaveSpider;
import net.minecraft.server.v1_6_R2.EntityChicken;
import net.minecraft.server.v1_6_R2.EntityCow;
import net.minecraft.server.v1_6_R2.EntityCreeper;
import net.minecraft.server.v1_6_R2.EntityEnderDragon;
import net.minecraft.server.v1_6_R2.EntityEnderman;
import net.minecraft.server.v1_6_R2.EntityGhast;
import net.minecraft.server.v1_6_R2.EntityGiantZombie;
import net.minecraft.server.v1_6_R2.EntityHorse;
import net.minecraft.server.v1_6_R2.EntityIronGolem;
import net.minecraft.server.v1_6_R2.EntityMagmaCube;
import net.minecraft.server.v1_6_R2.EntityMushroomCow;
import net.minecraft.server.v1_6_R2.EntityOcelot;
import net.minecraft.server.v1_6_R2.EntityPig;
import net.minecraft.server.v1_6_R2.EntityPigZombie;
import net.minecraft.server.v1_6_R2.EntitySheep;
import net.minecraft.server.v1_6_R2.EntitySilverfish;
import net.minecraft.server.v1_6_R2.EntitySkeleton;
import net.minecraft.server.v1_6_R2.EntitySlime;
import net.minecraft.server.v1_6_R2.EntitySnowman;
import net.minecraft.server.v1_6_R2.EntitySpider;
import net.minecraft.server.v1_6_R2.EntitySquid;
import net.minecraft.server.v1_6_R2.EntityVillager;
import net.minecraft.server.v1_6_R2.EntityWitch;
import net.minecraft.server.v1_6_R2.EntityWither;
import net.minecraft.server.v1_6_R2.EntityWolf;
import net.minecraft.server.v1_6_R2.EntityZombie;

import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class SimpleEntityRegister {
	private final Map<Class<?>, Class<?>> classesByBukkit = new HashMap<Class<?>, Class<?>>();
	private final Map<Class<?>, Class<?>> classesByMc = new HashMap<Class<?>, Class<?>>();

	public void put(Class<?> clazz1, Class<?> clazz2) {
		this.classesByBukkit.put(clazz1, clazz2);
		this.classesByMc.put(clazz2, clazz1);
	}

	public Class<?> getMcEntity(Class<?> clazz) {
		return this.classesByBukkit.get(clazz);
	}

	public Class<?> getBukkitEntity(Class<?> clazz) {
		return this.classesByMc.get(clazz);
	}

	public void load() {
		this.put(Creeper.class, EntityCreeper.class);
		this.put(Skeleton.class, EntitySkeleton.class);
		this.put(Spider.class, EntitySpider.class);
		this.put(Giant.class, EntityGiantZombie.class);
		this.put(Zombie.class, EntityZombie.class);
		this.put(Slime.class, EntitySlime.class);
		this.put(Ghast.class, EntityGhast.class);
		this.put(PigZombie.class, EntityPigZombie.class);
		this.put(Enderman.class, EntityEnderman.class);
		this.put(CaveSpider.class, EntityCaveSpider.class);
		this.put(Silverfish.class, EntitySilverfish.class);
		this.put(Blaze.class, EntityBlaze.class);
		this.put(MagmaCube.class, EntityMagmaCube.class);
		this.put(EnderDragon.class, EntityEnderDragon.class);
		this.put(Wither.class, EntityWither.class);
		this.put(Bat.class, EntityBat.class);
		this.put(Witch.class, EntityWitch.class);
		this.put(Pig.class, EntityPig.class);
		this.put(Sheep.class, EntitySheep.class);
		this.put(Cow.class, EntityCow.class);
		this.put(Chicken.class, EntityChicken.class);
		this.put(Squid.class, EntitySquid.class);
		this.put(Wolf.class, EntityWolf.class);
		this.put(MushroomCow.class, EntityMushroomCow.class);
		this.put(Snowman.class, EntitySnowman.class);
		this.put(Ocelot.class, EntityOcelot.class);
		this.put(IronGolem.class, EntityIronGolem.class);
		this.put(Horse.class, EntityHorse.class);
		this.put(Villager.class, EntityVillager.class);
	}
}