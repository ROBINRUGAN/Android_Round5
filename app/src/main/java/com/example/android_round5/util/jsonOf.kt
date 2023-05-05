import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun <K, V> jsonOf(vararg pairs: Pair<K, V>): RequestBody =
    if (pairs.isNotEmpty()){
        Gson().toJson(mapOf(*pairs))
            .toRequestBody("application/json; charset=utf-8".toMediaType())
    }
    else throw Throwable("bad json arguments")