package com.example.jetpackcomposesetup

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import com.squareup.moshi.Moshi
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


// Date Parser
private val dateParserFull by lazy {
	SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
}

private val dateParserNoNano by lazy {
	SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
}

// Date Formatter
private val dateFormatter by lazy {
	SimpleDateFormat("MMM dd, yyyy", Locale.US)
}

val dateFormatterISO by lazy {
	SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
}

private val hoursFormatter by lazy {
	SimpleDateFormat("hh:mm a", Locale.US)
}

fun parseDate(dateString: String): Date? {
	return try {
		dateParserFull.parse(dateString) as Date
	} catch (e: Exception) {
		try {
			dateParserNoNano.parse(dateString) as Date
		} catch (e: Exception) {
			null
		}
	}
}

fun parseDateAsLocalTimeZone(dateString: String): Date? {
	return try {
		dateParserFull.timeZone = TimeZone.getTimeZone("GMT")
		dateParserFull.parse(dateString) as Date
	} catch (e: Exception) {
		try {
			dateParserNoNano.parse(dateString) as Date
		} catch (e: Exception) {
			null
		}
	}
}

fun Date.asCustomFormat(pattern: String = "yyyy-MM-dd"): String {
	return SimpleDateFormat(pattern, Locale.US).format(this)
}

fun String.asFormattedDate(): String? {
	return try {
		dateFormatter.format(dateParserFull.parse(this) as Date)
	} catch (e: Exception) {
		try {
			dateFormatter.format(dateParserNoNano.parse(this) as Date)
		} catch (e: Exception) {
			null
		}
	}
}

fun String.asFormattedGetHoursMin(): String? {
	return try {
		hoursFormatter.format(dateParserFull.parse(this) as Date)
	} catch (e: Exception) {
		null
	}
}

fun String.asMinusWithCurrentDateTakeHoursMin(): String {
	var result = ""
	val compareDate: Date = parseDate(this) as Date
	val currentDate = Date()
	val diff = compareDate.time - currentDate.time
	
	val oneSec = 1000L
	val oneMin: Long = 60 * oneSec
	val oneHour: Long = 60 * oneMin
	val oneDay: Long = 24 * oneHour
	
	val diffMin: Long = diff / oneMin
	val diffHours: Long = diff / oneHour
	val diffDays: Long = diff / oneDay
	
	when {
		diffHours in 1..1 -> {
			result = "in ${(diffHours - diffDays * 24)} hours ${(diffMin - diffHours * 60)} minus"
		}
		
		diffHours < 2 && diffMin > 0 -> {
			result = "in ${(diffHours - diffDays * 24)} hours ${(diffMin - diffHours * 60)} minus"
		}
		
		else -> ""
	}
	
	return result
}

fun Double.asFormattedUsdAmount(): String {
	return "$%,.2f".format(this)
}

fun toCurrency(amount: Any?): String? {
	return try {
		if (listOf(Double::class, Float::class, Int::class).any { it.isInstance(amount) }) {
			val lvAmount = if (amount is Int) amount.toDouble() else amount
			return "$%,.2f".format(lvAmount)
		}
		
		null
	} catch (e: Exception) {
		e.printStackTrace()
		null
	}
}

fun String.asFormattedPhoneNumber(): String {
	return if (!this.startsWith("+855")) {
		"+855" + this.trimStart('0')
	} else {
		this
	}
}

/**
 * Convert app language code (en, km, zh) to api server language code (en, kh, cn)
 */
fun getServerLanguageCode(language: String): String {
	return when (language) {
		"km" -> "kh"
		else -> "en"
	}
}

fun <A> A.toJson(type: Class<A>): String? {
	return try {
		Moshi.Builder().build().adapter(type).lenient().toJson(this)
	} catch (e: Exception) {
		e.printStackTrace()
		null
	}
}

fun <A> String.fromJson(type: Class<A>): A? {
	return try {
		Moshi.Builder().build().adapter(type).lenient().fromJson(this) as A
	} catch (e: Exception) {
		e.printStackTrace()
		null
	}
}

fun String.toStringUrlEncode(): String = URLEncoder.encode(this, "utf-8")

fun String.fromUrlEncode(): String = URLDecoder.decode(this, "utf-8")

fun toDisplayText(value: Any?): String {
	return when (value) {
		is String -> value
		else -> "--"
	}
}

fun Context.openExternal(uri: Uri) {
	startActivity(Intent(ACTION_VIEW).apply { data = uri })
}

fun Context.openShareSheet(title: String? = null, data: String) {
	val sendIntent: Intent = Intent().apply {
		action = Intent.ACTION_SEND
		putExtra(Intent.EXTRA_TEXT, data)
		type = "text/plain"
	}
	startActivity(Intent.createChooser(sendIntent, title))
}

val Date.age: Int
	get() {
		val calendar = Calendar.getInstance()
		calendar.time = Date(time - Date().time)
		return 1970 - (calendar.get(Calendar.YEAR) + 1)
	}

fun triggerRebirth(ctx: Context, activity: Activity) {
	val intent = Intent(activity, MainActivity::class.java)
	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
	activity.finishAffinity()
	ctx.startActivity(intent)
//	Runtime.getRuntime().exit(0)
}

fun launchToContact(context: Context, phoneNumber: String) {
	try {
		context.startActivity(
			Intent(
				Intent.ACTION_DIAL,
				Uri.parse("tel://$phoneNumber")
			)
		)
	} catch (e: Exception) {
		e.printStackTrace()
	}
}

fun Uri.getTypeUri(context: Context): String? {
	val cR: ContentResolver = context.contentResolver
	val type = cR.getType(this)
	val typStr = type?.split("/")
	
	return typStr?.get(typStr.size - 1)
}

fun Int.getMonth(): String? {
	return DateFormatSymbols().months[this - 1]
}

fun String.isValidEmail(): Boolean {
	return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}