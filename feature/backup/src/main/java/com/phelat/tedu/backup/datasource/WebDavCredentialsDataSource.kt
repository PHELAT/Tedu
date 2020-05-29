package com.phelat.tedu.backup.datasource

import android.content.SharedPreferences
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import javax.inject.Inject

@FeatureScope
class WebDavCredentialsDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Writable<WebDavCredentials>, Readable<WebDavCredentials> {

    override fun write(input: WebDavCredentials) {
        sharedPreferences.edit()
            .putString(WEB_DAV_URL, input.url)
            .putString(WEB_DAV_USERNAME, input.username)
            .putString(WEB_DAV_PASSWORD, input.password)
            .apply()
    }

    override fun read(): WebDavCredentials {
        return sharedPreferences.run {
            WebDavCredentials(
                url = getString(WEB_DAV_URL, "") ?: "",
                username = getString(WEB_DAV_USERNAME, "") ?: "",
                password = getString(WEB_DAV_PASSWORD, "") ?: ""
            )
        }
    }

    companion object {
        private const val WEB_DAV_URL = "web_dav_url"
        private const val WEB_DAV_USERNAME = "web_dav_username"
        private const val WEB_DAV_PASSWORD = "web_dav_password"
    }
}