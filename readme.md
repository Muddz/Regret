# Regret
[![](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![APK](https://img.shields.io/badge/Download-Demo-brightgreen.svg)](https://github.com/Muddz/PixelShot/raw/master/demo.apk)

Regret is a Android library for apps that wants to implement a undo/redo feature.
The library is simple to use and will persist your history until you clear it manually and works with all primitive types and any object.


## Usage

1) Instantiate `Regret` and add data to it
```java
   Regret regret = new Regret(context, this);
   regret.add(KEY_TEXT, editText.getText().toString());
   regret.add(KEY_TEXT_COLOR, Color.BLACK);
```

2) Call `regret.undo()` or `regret.redo()`. Your data will be returned via the callback
```java

  @Override
    public void onDo(String key, Object value) {
        switch (key) {
            case KEY_TEXT:
                editText.setText((CharSequence) value);
                break;
            case KEY_TEXT_COLOR:
                editText.setTextColor((Integer) value);
                break;
        }
    }
```

## Installation

Add the dependency in your `build.gradle`
```groovy
dependencies {
    implementation 'com.muddzdev:regret:1.0.0'  
}
```
 ----

## License

    Copyright 2018 Muddi Walid

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
