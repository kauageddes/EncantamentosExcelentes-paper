package su.nightexpress.excellentenchants.enchantment.impl.armor;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import su.nexmedia.engine.utils.NumberUtil;
import su.nightexpress.excellentenchants.ExcellentEnchants;
import su.nightexpress.excellentenchants.Placeholders;
import su.nightexpress.excellentenchants.api.enchantment.meta.Chanced;
import su.nightexpress.excellentenchants.api.enchantment.type.DeathEnchant;
import su.nightexpress.excellentenchants.enchantment.config.EnchantScaler;
import su.nightexpress.excellentenchants.enchantment.impl.ExcellentEnchant;
import su.nightexpress.excellentenchants.enchantment.impl.meta.ChanceImplementation;
import su.nightexpress.excellentenchants.enchantment.util.EnchantPriority;

public class MaldicaoDaAlmaCondenada extends ExcellentEnchant implements Chanced, DeathEnchant {

    public static final String ID = "maldição_da_alma_condenada";

    private ChanceImplementation chanceImplementation;

    public EnchantSelfDestruction(@NotNull ExcellentEnchants plugin) {
        super(plugin, ID, EnchantPriority.MEDIUM);
        this.getDefaults().setDescription("%enchantment_trigger_chance%% de chance de ser mandado pro inferno ao morrer.");
        this.getDefaults().setLevelMax(3);
        this.getDefaults().setTier(0.3);
    }

    @Override
    public void loadSettings() {
        super.loadSettings();
        this.chanceImplementation = ChanceImplementation.create(this,
            "10.0 + " + Placeholders.ENCHANTMENT_LEVEL + " * 10");
    }

    @Override
    public @NotNull ChanceImplementation getChanceImplementation() {
        return chanceImplementation;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR_TORSO;
    }

    @Override
    public void onFatalDamage(
            @NotNull EntityDamageEvent e,
            @NotNull LivingEntity entity,
            int level) {

        if (entity.getHealth() - e.getFinalDamage() > 0) return;

        World world = entity.getWorld();

        if (!this.isAvailableToUse(entity)) return;
        if (!this.checkTriggerChance(level)) return;
        if (world.getEnvironment() == World.Environment.NETHER) return;

        World nether = Bukkit.getWorld("world_nether");
        if (nether == null) return;

        Location current = entity.getLocation();

        Location destination = new Location(
            nether,
            current.getX(),
            current.getY(),
            current.getZ(),
            current.getYaw(),
            current.getPitch()
        );

        e.setCancelled(true);
        entity.teleport(destination);
    }

        Location nether_location = new Location(World.Environment, x, y, z);
        entity.teleport(nether_location);

    }

    @Override
    public boolean onKill(@NotNull EntityDeathEvent e, @NotNull LivingEntity entity, @NotNull Player killer, int level) {
        return false;
    }

}
