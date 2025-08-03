package me.kyjn.onehitmod.init;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.*;
import net.minecraft.core.BlockPos;
import net.minecraftforge.registries.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, "onehitmod");

    public static final RegistryObject<Item> ONE_HIT_SWORD = ITEMS.register(
        "one_hit_sword",
        () -> new SwordItem(Tiers.NETHERITE, 1000, -2.4F,
            new Item.Properties().stacksTo(1)) {

            @Override
            public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                if (!attacker.level().isClientSide()) {
                    Level level = attacker.level();
                    target.hurt(target.damageSources().magic(), Float.MAX_VALUE);
                    level.explode(null, target.getX(), target.getY(), target.getZ(), 3.0F, Level.ExplosionInteraction.TNT);

                    LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
                    if (lightning != null) {
                        lightning.moveTo(target.getX(), target.getY(), target.getZ());
                        level.addFreshEntity(lightning);
                    }

                    target.setSecondsOnFire(10);

                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.END_ROD, target.getX(), target.getY() + 1, target.getZ(), 100, 0.5, 1, 0.5, 0.05);
                    }

                    level.playSound(null, new BlockPos(target.getX(), target.getY(), target.getZ()),
                        SoundEvents.EXPLOSION, SoundSource.PLAYERS, 2.0F, 1.0F);
                }
                return super.hurtEnemy(stack, target, attacker);
            }
        });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
