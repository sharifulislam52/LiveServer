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

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

 class LiveHelperDB(context:Context):SQLiteOpenHelper(context, "live_server_data", null, 1) {

//      >>> Table Name : live_info
//      c0 : live_id [int] [value : 1 forever]
//      c1 : request_name [String]
//      c2 : request_link [String]
//      c3 : count_request [int]
//      c4 : is_continue [String] [value : yes/no]
//      c5 : request_limit [int]

     override fun onCreate(sqLiteDatabase:SQLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE live_info (live_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "request_name TEXT, request_link TEXT, count_request TEXT, is_continue TEXT," +
                "request_limit TEXT)")
     }

     override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
         sqLiteDatabase.execSQL("DROP TABLE IF EXISTS live_info")
         onCreate(sqLiteDatabase)
     }

     fun insert_primary_data(req_name: String, link: String) {
         val sqLiteDatabase = this.writableDatabase
         val live_values = ContentValues()
         live_values.put("request_name", req_name)
         live_values.put("request_link", link)
         live_values.put("count_request", "0")
         live_values.put("is_continue", "yes")
         live_values.put("request_limit", "0")
         sqLiteDatabase.insert("live_info", null, live_values)
         sqLiteDatabase.close()
     }

     fun get_data(): Cursor {
         val sqLiteDatabase = this.writableDatabase
         return sqLiteDatabase.rawQuery("SELECT * FROM live_info", null)
     }

     fun updateSingleColumn(c_name: String, update_string: String): Boolean {
         val sqLiteDatabase = this.writableDatabase
         val live_values = ContentValues()
         live_values.put(c_name, update_string)
         sqLiteDatabase.update("live_info", live_values, "live_id= ?", arrayOf("1"))
         return true
     }

     fun updateAllDataById(req_name: String, link: String, count: String, is_continue: String, limit: String): Boolean {
         val sqLiteDatabase = this.writableDatabase
         val live_values = ContentValues()
         live_values.put("request_name", req_name)
         live_values.put("request_link", link)
         live_values.put("count_request", count)
         live_values.put("is_continue", is_continue)
         live_values.put("request_limit", limit)
         sqLiteDatabase.update("live_info", live_values, "live_id= ?", arrayOf("1"))
         return true
     }
 }