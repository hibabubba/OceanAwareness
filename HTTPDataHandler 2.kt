package com.example.prosjektin2000

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HTTPDataHandler {
    fun GetHTTPDataHandler(urlStr: String?): String? {
        try {
            val url = URL(urlStr)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            if (urlConnection.getResponseCode() === HttpURLConnection.HTTP_OK) {
                val inputStream: InputStream = BufferedInputStream(urlConnection.getInputStream())
                val r = BufferedReader(InputStreamReader(inputStream))
                val sb = StringBuilder()
                var line: String? = ""
                while (r.readLine().also({ line = it }) != null) {
                    sb.append(line)
                    stream = sb.toString()
                    urlConnection.disconnect()
                }
            }
        } catch (ex: Exception) {
            return null
        }
        return stream
    }

    companion object {
        var stream = ""
    }
}