package xyz.atrius.waystones.commands

import org.bukkit.command.CommandSender
import xyz.atrius.waystones.data.config.ConfigManager
import xyz.atrius.waystones.localization
import xyz.atrius.waystones.utility.message

object ConfigCommand : SimpleCommand("config", "conf", "co", "c") {

    override fun execute(sender: CommandSender, args: Array<String>) {
        // Permissions Check
        if (!sender.hasPermission("waystones.config"))
            return sender.message(localization["command-no-permission"])
        // Pull arguments from args
        val setting = args.getOrNull(0)
        val new = args.getOrNull(1)
        // List all arguments if the argument list is empty
        if (args.isEmpty())
            return listOptions(sender)
        val prop = ConfigManager[setting]
        // Return the existing config value
        if (new == null)
            return sender.message(localization["command-config-view", setting, prop])
        // Assign the new config value to the system and print result of action
        if (prop?.invoke(new) == true)
            sender.message(localization["command-config-set", setting, prop])
        else
            sender.message(localization["command-config-set-fail", setting])
    }

    private fun listOptions(sender: CommandSender) {
        sender.message("&7--------- &dWaystones Config &7----------&r")
        // Display each config property
        for ((prop, value) in ConfigManager)
            sender.message("&f$prop&f: &b$value")
        sender.message("&7-----------------------------------")
    }
}
