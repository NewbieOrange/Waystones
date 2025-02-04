package xyz.atrius.waystones.data.config

import xyz.atrius.waystones.Power
import xyz.atrius.waystones.Power.INTER_DIMENSION
import xyz.atrius.waystones.SicknessOption
import xyz.atrius.waystones.SicknessOption.DAMAGE_ON_TELEPORT
import xyz.atrius.waystones.data.Property
import xyz.atrius.waystones.localization
import xyz.atrius.waystones.utility.KotlinPlugin
import java.util.*

class Config(plugin: KotlinPlugin) {
    init {
        plugin.config.options().copyDefaults(true)
        plugin.saveDefaultConfig()
    }

    // The localization to be used
    val locale: Property<Locale> =
        Property("locale", Locale.ENGLISH, LocaleParser) { localization.reload() }

    // The amount of ticks to wait before teleporting
    val waitTime: Property<Int> =
        Property("wait-time", 60, PositiveValueParser)

    // Whether or not the player receiving damage will force cancel the warp
    val damageStopsWarping: Property<Boolean> =
        Property("damage-stops-warping", true, BooleanParser)

    // Determines if teleporters limit their max distance or not, will check if user is in range before teleporting if true
    val limitDistance: Property<Boolean> =
        Property("limit-distance", true, BooleanParser)

    // The minimum base range a waystone can have
    val baseDistance: Property<Int> =
        Property("base-distance", 100, PositiveValueParser)

    // The maximum amount of blocks a portal block can boost the range of a portal (this is for netherite)
    val maxBoost: Property<Int> =
        Property("max-boost", 50, PositiveValueParser)

    // The max number of blocks allowed in warp structures
    val maxWarpSize: Property<Int> =
        Property("max-warp-size", 25, PositiveValueParser)

    // Allow users to jump between dimensions when warping
    val jumpWorlds: Property<Boolean> =
        Property("jump-worlds", true, BooleanParser)

    // How many blocks it takes to travel one block in the destination world
    val worldRatio: Property<Int> =
        Property("world-ratio", 8, PositiveValueParser)

    // Play animations during teleport sequence
    val warpAnimations: Property<Boolean> =
        Property("warp-animations", true, BooleanParser)

    // Determines whether or not warping with a compass deletes the item after use
    val singleUse: Property<Boolean> =
        Property("single-use", false, BooleanParser)

    // Determines if/how power is required from a respawn anchor in order to use the warp
    val requirePower: Property<Power> =
        Property("require-power", INTER_DIMENSION, EnumParser(Power::class))

    val powerCost: Property<Int> =
        Property("power-cost", 1, PositiveValueParser)

    // Whether or not debuff effects are enabled
    val portalSickness: Property<Boolean> =
        Property("enable-portal-sickness", true, BooleanParser)

    // The chance at which debuff effects can occur (default is 5%)
    val portalSicknessChance: Property<Double> =
        Property("portal-sickness-chance", 0.05, PercentageParser)

    // Defines the behavior of warping while under the portal sickness effect
    val portalSickWarping: Property<SicknessOption> =
        Property("portal-sickness-warping", DAMAGE_ON_TELEPORT, EnumParser(SicknessOption::class))

    // The amount of damage done by portal sickness
    val portalSicknessDamage: Property<Double> =
        Property("portal-sickness-damage", 5.0, DoubleParser)

    // Whether or not keys can be relinked to another waystone after being linked once
    val relinkableKeys: Property<Boolean> =
        Property("relinkable-keys", true, BooleanParser)

    // Whether or not the plugin uses a custom key item for warp keys
    val keyItems: Property<Boolean> =
        Property("enable-key-items", true, BooleanParser)

    val keyRecipe: Property<List<String>> =
        Property("key-recipe", listOf(
            "AIR", "IRON_INGOT", "AIR",
            "IRON_INGOT", "REDSTONE_BLOCK", "IRON_INGOT",
            "AIR", "IRON_INGOT", "AIR"
        ), ListParser(StringParser))

    // Netherite grants the max amount of boost per block
    fun netheriteBoost(): Int = maxBoost()

    // Emerald grants 75% of the max boost per block
    fun emeraldBoost(): Int = (maxBoost() * 0.75).toInt()

    // Diamond grants 50% of the max boost per block
    fun diamondBoost(): Int = maxBoost() / 2

    // Gold grants 33% of the max boost per block
    fun goldBoost(): Int = maxBoost() / 3

    // Iron grants 20% of the max boost per block
    fun ironBoost(): Int = maxBoost() / 5
}
