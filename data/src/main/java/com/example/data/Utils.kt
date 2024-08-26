package com.example.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException

internal object Utils {

    inline fun <reified OBJ> moshiToString(obj: OBJ): String {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<OBJ> = moshi.adapter(OBJ::class.java)
        return jsonAdapter.toJson(obj)
    }

    inline fun <reified OBJ> stringToMoshi(str: String): OBJ? {
        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter: JsonAdapter<OBJ> = moshi.adapter(OBJ::class.java)
        return jsonAdapter.fromJson(str)
    }

}
