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

import android.content.Context
import org.json.JSONObject


open class LiveServer(internal var context: Context) : Live() {
    internal var live_db: LiveHelperDB
    private var live_name: String? = null
    private var live_link: String? = null
    private var is_run: String? = null
    private var count_request: Int = 0
    private var count_limit: Int = 0

    init {
        live_db = LiveHelperDB(context)

        val live_cursor = live_db.get_data()
        if (live_cursor.count > 0) {
            get_and_set_data()
            live_db.updateSingleColumn("count_request", Integer.toString(count_request + 1))
            if (count_limit > 0 && count_request >= count_limit) {
                live_db.updateSingleColumn("is_continue", "no")
            }
        }
    }

    override fun HttpLiveResult(name: String, result: String, `object`: JSONObject) {
        LiveResult(name, result, `object`)
    }

    override fun HttpLiveRequestFailed(name: String) {
        LiveRequestFailed(name)
    }

    fun LiveResult(name: String, result: String, `object`: JSONObject) {}
    fun LiveRequestFailed(name: String) {}

    fun Start(name: String, link: String, data: String) {
        val live_cursor = live_db.get_data()
        if (live_cursor.count === 0) {
            live_db.insert_primary_data(name, link)
        } else {
            live_db.updateAllDataById(name, link, "0", "yes", "0")
        }
        execute(name, link, data)
    }

    private fun get_and_set_data() {
        val live_cursor = live_db.get_data()
        while (live_cursor.moveToNext()) {
            val req_name = StringBuffer()
            req_name.append(live_cursor.getString(1))

            val req_link = StringBuffer()
            req_link.append(live_cursor.getString(2))

            val count_req = StringBuffer()
            count_req.append(live_cursor.getString(3))

            val is_con = StringBuffer()
            is_con.append(live_cursor.getString(4))

            val limit = StringBuffer()
            limit.append(live_cursor.getString(5))

            live_name = req_name.toString()
            live_link = req_link.toString()
            count_request = Integer.parseInt(count_req.toString())
            is_run = is_con.toString()
            count_limit = Integer.parseInt(limit.toString())
        }
    }

    open fun LiveExchange(data: String) {
        if (is_run.equals("yes")) {
            execute(live_name, live_link, data)
        }
    }

    open fun LiveExchange(ObjectData: JSONObject) {
        if (is_run.equals("yes")) {
            val data = ObjectData.toString()
            execute(live_name, live_link, data)
        }
    }

    open fun LiveExchange(new_link: String, data: String) {
        if (is_run.equals("yes")) {
            execute(live_name, new_link, data)
        }
    }

    open fun LiveExchange(new_link: String, ObjectData: JSONObject) {
        if (is_run.equals("yes")) {
            val data = ObjectData.toString()
            execute(live_name, new_link, data)
        }
    }

    open fun LiveExchange(new_name: String, new_link: String, data: String) {
        if (is_run.equals("yes")) {
            execute(new_name, new_link, data)
        }
    }

    open fun LiveExchange(new_name: String, new_link: String, ObjectData: JSONObject) {
        if (is_run.equals("yes")) {
            val data = ObjectData.toString()
            execute(new_name, new_link, data)
        }
    }

    fun Stop() {
        live_db.updateSingleColumn("is_continue", "no")
    }

    fun Stop(request_limite: Int) {
        count_request = 0
        live_db.updateSingleColumn("count_request", "1")
        live_db.updateSingleColumn("request_limit", Integer.toString(request_limite))
    }
}