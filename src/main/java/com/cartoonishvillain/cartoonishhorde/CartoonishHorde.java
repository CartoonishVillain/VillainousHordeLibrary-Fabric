package com.cartoonishvillain.cartoonishhorde;

import com.cartoonishvillain.cartoonishhorde.mixin.AvailableGoalsAccessor;
import com.cartoonishvillain.cartoonishhorde.mixin.LivingGoalAccessor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class CartoonishHorde implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "cartoonishhorde";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

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
}
