/**
Copyright (c) 2018 shariful islam

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package shariful.liveserver

import android.os.AsyncTask

import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

open class Live : AsyncTask<String,Void,String>() {
    var name:String = ""

    override fun doInBackground(strings:Array<String>) : String {
        try {
            name = strings[0]
            val server_url = strings[1]
            val server_data = strings[2]
            val url = URL(server_url)

            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.doOutput = true
            httpURLConnection.doInput = true

            val outputStream = httpURLConnection.outputStream
            val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
            val post_data = URLEncoder.encode("app_data", "UTF-8") + "=" + URLEncoder.encode(server_data, "UTF-8")

            bufferedWriter.write(post_data)
            bufferedWriter.flush()
            bufferedWriter.close()
            outputStream.close()
            val inputStream = httpURLConnection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            val result : String = bufferedReader.readLine()

            bufferedReader.close()
            inputStream.close()
            httpURLConnection.disconnect()

            return result

        } catch (e:MalformedURLException) {
            e.printStackTrace()
        } catch (e:IOException) {
            e.printStackTrace()
        }
        return "0"
    }

    override fun onPreExecute(){}

    override fun onPostExecute(result : String){
        if (!result.equals("0")) {
            var `object`: JSONObject
            try {
                `object` = JSONObject(result)
            } catch (j: JSONException) {
                `object` = JSONObject()
            }

            HttpLiveResult(name, result, `object`)
        } else {
            HttpLiveRequestFailed(name)
        }
    }

    override fun onProgressUpdate(vararg values: Void){
        super.onProgressUpdate()
    }

    open fun HttpLiveResult(name: String, result: String, `object`: JSONObject) {}
    open fun HttpLiveRequestFailed(name: String) {}
}