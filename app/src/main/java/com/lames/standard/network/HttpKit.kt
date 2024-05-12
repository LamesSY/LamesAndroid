package com.lames.standard.network


import com.lames.standard.App
import com.lames.standard.BuildConfig
import com.lames.standard.common.CommonApp
import com.lames.standard.tools.parseToJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.async
import rxhttp.wrapper.cache.CacheMode
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.RxHttpNoBodyParam
import rxhttp.wrapper.param.toAwaitResultArray
import rxhttp.wrapper.param.toAwaitResultBody
import rxhttp.wrapper.param.toAwaitResultData
import rxhttp.wrapper.ssl.HttpsUtils
import java.io.File
import java.util.Locale
import java.util.concurrent.TimeUnit

fun postRequest(
    method: String,
    params: Any,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
) = (RxHttp.postJson(method)).apply {
    val paramsJson = parseToJson(params)
    if (enableCache) {
        setCacheMode(cacheMode)
        setCacheKey("${method}?json=$paramsJson")
    }
    addAll(paramsJson)
    addHeader("decrypt", if (enableEncrypt) "1" else "0")
    setDecoderEnabled(enableEncrypt)
}

suspend inline fun <reified T : Any> postJsonArrayForResult(
    method: String,
    params: Any,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): ResultBodyData<T> {
    return (RxHttp.postJsonArray(method)).apply {
        val paramsJson = parseToJson(params)
        if (enableCache) {
            setCacheMode(cacheMode)
            setCacheKey("${method}?json=$paramsJson")
        }
        addAll(paramsJson)
        addHeader("decrypt", if (enableEncrypt) "1" else "0")
        setDecoderEnabled(enableEncrypt)
    }.toAwaitResultBody<T>().await()
}

suspend inline fun <reified T : Any> postForResult(
    method: String,
    params: Any,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): ResultBodyData<T> = postRequest(method, params, enableEncrypt, enableCache, cacheMode).toAwaitResultBody<T>().await()

suspend inline fun <reified T : Any> postForData(
    method: String,
    params: Any,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): T = postRequest(method, params, enableEncrypt, enableCache, cacheMode).toAwaitResultData<T>().await()

suspend inline fun <reified T : Any> postForList(
    method: String,
    params: Any,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): List<T> = postRequest(method, params, enableEncrypt, enableCache, cacheMode).toAwaitResultArray<T>().await()

//上传amr文件用
suspend inline fun <reified T : Any> postForm(
    method: String,
    file: File,
    params: Map<String, Any?>,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): ResultBodyData<T> {
    return (RxHttp.postForm(method)).apply {
        val paramsJson = parseToJson(params)
        if (enableCache) {
            setCacheMode(cacheMode)
            setCacheKey("${method}?json=$paramsJson")
        }
        addAll(params)
        addFile("file", file)
        addHeader("decrypt", if (enableEncrypt) "1" else "0")
        setDecoderEnabled(enableEncrypt)
    }.toAwaitResultBody<T>().await()
}

//endregion

//region get
fun getCacheOnly(method: String, params: HashMap<String, Any?>): RxHttpNoBodyParam {
    return RxHttp.get(method).apply {
        setCacheMode(CacheMode.ONLY_CACHE)
        setCacheKey("${method}?json=${parseToJson(params)}")
        addAll(params)
    }
}

fun getRequest(
    method: String,
    params: HashMap<String, Any?>,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): RxHttpNoBodyParam = (RxHttp.get(method)).apply {
    if (enableCache) {
        setCacheMode(cacheMode)
        setCacheKey("${method}?json=${parseToJson(params)}")
    }
    addAll(params)
    addHeader("decrypt", if (enableEncrypt) "1" else "0")
    setDecoderEnabled(enableEncrypt)
}

suspend inline fun <reified T : Any> getResult(
    method: String,
    params: HashMap<String, Any?>,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): ResultBodyData<T> = getRequest(method, params, enableEncrypt, enableCache, cacheMode).toAwaitResultBody<T>().await()

suspend inline fun <reified T : Any> getData(
    method: String,
    params: HashMap<String, Any?>,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): T = getRequest(method, params, enableEncrypt, enableCache, cacheMode).toAwaitResultData<T>().await()

suspend inline fun <reified T : Any> getList(
    method: String,
    params: HashMap<String, Any?>,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): List<T> = getRequest(method, params, enableEncrypt, enableCache, cacheMode).toAwaitResultArray<T>().await()

suspend inline fun <reified T : Any> CoroutineScope.getDataAsync(
    method: String,
    params: HashMap<String, Any?>,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): Deferred<T> = getRequest(method, params, enableEncrypt, enableCache, cacheMode).toAwaitResultData<T>().async(this)

suspend inline fun <reified T : Any> CoroutineScope.getListAsync(
    method: String,
    params: HashMap<String, Any?>,
    enableEncrypt: Boolean = false,
    enableCache: Boolean = false,
    cacheMode: CacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE
): Deferred<List<T>> = getRequest(method, params, enableEncrypt, enableCache, cacheMode).toAwaitResultArray<T>().async(this)
//endregion

//region delete
fun deleteRequest(method: String, params: HashMap<String, Any?>, enableEncrypt: Boolean = true) =
    (RxHttp.deleteJson(method))
        .addAll(params)
        .addHeader("decrypt", if (enableEncrypt) "1" else "0")
        .setDecoderEnabled(enableEncrypt)

suspend inline fun <reified T : Any> deleteForResult(
    method: String, params: HashMap<String, Any?>, enableEncrypt: Boolean = true
): ResultBodyData<T> = deleteRequest(method, params, enableEncrypt).toAwaitResultBody<T>().await()

suspend inline fun <reified T : Any> deleteJsonArrayForResult(
    method: String,
    params: Any,
): ResultBodyData<T> {
    return (RxHttp.deleteJsonArray(method)).apply {
        val paramsJson = parseToJson(params)
        addAll(paramsJson)
        addHeader("decrypt", "0")
        setDecoderEnabled(false)
    }.toAwaitResultBody<T>().await()
}
//endregion

//region put
fun putRequest(method: String, params: HashMap<String, Any?>, enableEncrypt: Boolean = true) =
    (RxHttp.putJson(method))
        .addAll(params)
        .addHeader("decrypt", if (enableEncrypt) "1" else "0")
        .setDecoderEnabled(enableEncrypt)

suspend inline fun <reified T : Any> putForResult(
    method: String, params: HashMap<String, Any?>, enableEncrypt: Boolean = true
): ResultBodyData<T> {
    return putRequest(method, params, enableEncrypt)
        .toAwaitResultBody<T>()
        .await()
}

object HttpKit {


    const val REQUEST_TAG = "REQUEST_TAG"

    val currentCountry: String get() = "CN"


    val currentLanguage: String
        get() {
            val languageTag = Locale.getDefault().toLanguageTag()
            return when {
                languageTag.contains("hans", true) -> "ZH"
                languageTag.contains("hant", true) -> "ZH_HK"
                languageTag.contains("en", true) -> "EN"
                else -> "ZH"
            }
        }


    fun initHttpConfig() {
        val cacheDir = File(CommonApp.obtain<App>().applicationContext.externalCacheDir, "RxHttpCache")
        val sslParams = HttpsUtils.getSslSocketFactory()
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            //添加信任证书
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            //忽略host验证
            .hostnameVerifier { _, _ -> true }
            .build()
        RxHttpPlugins.init(okHttpClient)
            .setDebug(BuildConfig.DEBUG)
            .setCache(cacheDir, 10485760L, CacheMode.ONLY_NETWORK, 96 * 60 * 60 * 1000L) //设置缓存大小及方式
            .setOnParamAssembly {
                //it.addHeader("appId", "BuildConfig.APP_ID")
            }
    }
}
