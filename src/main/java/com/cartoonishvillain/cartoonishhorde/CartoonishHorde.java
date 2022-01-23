package com.cartoonishvillain.cartoonishhorde;

import com.cartoonishvillain.cartoonishhorde.mixin.AvailableGoalsAccessor;
import com.cartoonishvillain.cartoonishhorde.mixin.LivingGoalAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.Giant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class CartoonishHorde implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "cartoonishhorde";
	public static Horde horde;

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ServerLifecycleEvents.SERVER_STARTING.register(ServerStartListener.getInstance());
		ServerTickEvents.END_WORLD_TICK.register(WorldTickListener.getInstance());



	}

	public static boolean isHordeMember(PathfinderMob entity) {
		GoalSelector mobGoalSelector = ((LivingGoalAccessor) entity).cartoonishHordeGetMobGoalSelector();
		Set<WrappedGoal> prioritizedGoals = ((AvailableGoalsAccessor) mobGoalSelector).cartoonishHordeGetAvailableGoals();
		Goal hordeGoal = null;
		if (prioritizedGoals != null) {
			for (WrappedGoal prioritizedGoal : prioritizedGoals) {
				if (prioritizedGoal.getGoal() instanceof HordeMovementGoal) {
					hordeGoal = prioritizedGoal.getGoal();
					if (hordeGoal != null) break;
				}
			}
			return hordeGoal != null;
		}
		return false;
	}

	public static class ServerStartListener implements ServerLifecycleEvents.ServerStarting{
		private static final ServerStartListener INSTANCE = new ServerStartListener();

		public static ServerStartListener getInstance() {return INSTANCE;}

		@Override
		public void onServerStarting(MinecraftServer server) {
			//Step 2 - Instantiate
			horde = new Horde(server);
			horde.setHordeData(new EntityHordeData<>(2, 1, 1, EntityType.SPIDER, Giant.class));

		}
	}

	public static class WorldTickListener implements ServerTickEvents.EndWorldTick{
		private static final WorldTickListener INSTANCE = new WorldTickListener();

		public static WorldTickListener getInstance() {return INSTANCE;}

		@Override
		public void onEndTick(ServerLevel world) {
			//Step 3 - Connect
			horde.tick();
		}
	}

	//STEP 4 is in JumpMixin
}
