package br.com.rodrigoamora.marvellapp.factory

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import br.com.rodrigoamora.marvellapp.R
import br.com.rodrigoamora.marvellapp.ui.activity.MainActivity


class ShortcutFactory {
    companion object {
        @TargetApi(26)
        fun createShortcutInfo(
            context: Context,
            shortLabels: Array<String>,
            icons: Array<Int>
        ): List<ShortcutInfo?> {
            val shortcutInfoList: MutableList<ShortcutInfo?> = mutableListOf()

            for (i in shortLabels.indices) {
                val intent = when(shortLabels[i]) {
                    context.getString(R.string.shortcut_characters) -> {
                        Intent(context, MainActivity::class.java)
                    }
                    else -> {
                        Intent(context, MainActivity::class.java)
                    }
                }
                intent.action = Intent.ACTION_VIEW

                val icon = icons[i]?.let { Icon.createWithResource(context, it) }
                val shortcutInfo = ShortcutInfo
                    .Builder(context.applicationContext, "shortcut" + shortLabels[i])
                    .setIntent(intent)
                    .setShortLabel(shortLabels[i])
                    .setLongLabel(shortLabels[i])
                    .setIcon(icon)
                    .build()

                shortcutInfoList.add(shortcutInfo)
            }

            return shortcutInfoList
        }
    }
}