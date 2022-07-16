package ir.pourahmadi.got.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.pourahmadi.got.presentation.common.gone
import ir.pourahmadi.got.presentation.common.invisible
import ir.pourahmadi.got.presentation.common.visible

fun <K, V> Map<K, V?>.filterNotNullValues(): Map<K, V> =
    mapNotNull { (key, value) -> value?.let { key to it } }.toMap()


fun getStringResources(context: Context, @StringRes resId: Int): String =
    context.resources.getString(resId)

fun getDrawable(context: Context, @DrawableRes resId: Int?): Drawable? =
    resId?.let { ContextCompat.getDrawable(context, it) }

fun getColor(context: Context, @ColorRes resId: Int): Int =
    ContextCompat.getColor(context, resId)

inline fun <T : View> T.showIf(predicate: (T) -> Boolean): T {
    if (predicate(this)) {
        visible()
    } else {
        gone()
    }
    return this
}

inline fun <T : View> T.showIfInvis(predicate: (T) -> Boolean): T {
    if (predicate(this)) {
        visible()
    } else {
        invisible()
    }
    return this
}

fun String.toListStrings(): List<String> {
    var result: List<String> = listOf()
    try {
        this.let {
            val type = object : TypeToken<List<String>>() {}.type
            result = Gson().fromJson(
                this,
                type
            )
        }
    } catch (e: Exception) {

    }
    return result
}
