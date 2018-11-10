# LiveServer
LiveServer is a light and fast HTTP Server Library that makes networking for Android apps very simple. It is specially designed for transfer **String and JSON data** between Android App and Server. Another major advantage of this library is that it turned MySQL Database into a **Real-time Database** and it is very friendly with PHP.

LiveServer offers the following benefits :
------
* It's light and fast.
* Easy to learn and use.
* Transfer String and JSON data.
* Receiving data as String and JSONObject.
* **Transfer and Receive data in Real-time.**
* Very friendly with PHP.

build.gradle (Project)
------
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

build.gradle (Module: app)
------
```
	dependencies {
		implementation 'com.github.sharifulislam52:LiveServer:1.0.2'
	}
}
```

Usage / Tutorial
-----
Comming Soon...

Changelog
---------
* **1.0.0 - 1.0.2**
    * Initial release
	
License
-------
```
**Apache License**

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
```
