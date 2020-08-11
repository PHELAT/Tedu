package com.phelat.tedu.backup.datasource

import android.content.SharedPreferences
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.Failure
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.Success
import javax.inject.Inject

@FeatureScope
internal class WebDavCredentialsDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : WebDavCredentialsWritable,
    WebDavCredentialsReadable {

    override fun write(input: WebDavCredentials) {
        sharedPreferences.edit()
            .putString(WEB_DAV_URL, input.url)
            .putString(WEB_DAV_USERNAME, input.username)
            .putString(WEB_DAV_PASSWORD, input.password)
            .apply()
    }

    override fun read(): Response<WebDavCredentials, BackupErrorContext> {
        val url = sharedPreferences.getString(WEB_DAV_URL, null)
        val username = sharedPreferences.getString(WEB_DAV_USERNAME, null)
        val password = sharedPreferences.getString(WEB_DAV_PASSWORD, null)
        return if (url.isNullOrEmpty() || username.isNullOrEmpty() || password.isNullOrEmpty()) {
            Failure(BackupErrorContext.CredentialsEmpty())
        } else {
            val credentials = WebDavCredentials(url, username, password)
            Success(credentials)
        }
    }

    companion object {
        private const val WEB_DAV_URL = "web_dav_url"
        private const val WEB_DAV_USERNAME = "web_dav_username"
        private const val WEB_DAV_PASSWORD = "web_dav_password"
    }
}

typealias WebDavCredentialsWritable = Writable<WebDavCredentials>

typealias WebDavCredentialsReadable = Readable<@JvmSuppressWildcards Response<WebDavCredentials,
        BackupErrorContext>>