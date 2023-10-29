package com.usewalletkit.sdk.login

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.squareup.moshi.JsonAdapter
import com.usewalletkit.sdk.generated.infrastructure.Serializer

class EncryptedPrefSessionStore(
    context: Context,
) : SessionStore {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "session_encrypted_shared_prefs",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val jsonAdapter: JsonAdapter<SessionModel> = Serializer.moshiBuilder
        .build()
        .adapter(SessionModel::class.java)

    override fun storeSession(session: SessionModel) {
        val serializedString = jsonAdapter.toJson(session)
        sharedPreferences
            .edit()
            .putString(SESSION_SHARED_PREF_KEY, serializedString)
            .commit()
    }

    override fun getSession(): SessionModel? {
        val serializedString = sharedPreferences.getString(SESSION_SHARED_PREF_KEY, null)
        return if (serializedString != null) {
            jsonAdapter.fromJson(serializedString)
        } else {
            null
        }
    }

    override fun deleteSession() {
        sharedPreferences
            .edit()
            .remove(SESSION_SHARED_PREF_KEY)
            .commit()
    }

    companion object {
        private const val SESSION_SHARED_PREF_KEY = "session_shared_pref_key"
    }
}
