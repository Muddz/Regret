# Regret
[![](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![APK](https://img.shields.io/badge/Download-Demo-brightgreen.svg)](https://github.com/Muddz/Regret/raw/master/demo.apk)

Regret is an Android library for apps that wants to implement an undo/redo feature.
The library is simple to use and works with all primitive types and objects.

Regret is based on the [UndoRedoList](https://github.com/Muddz/UndoRedoList)



## Usage

1) Instantiate `Regret` and add key-value data to it
```java
   Regret regret = new Regret(context, this);
   regret.add(KEY_TEXT, editText.getText().toString());
   regret.add(KEY_TEXT_COLOR, Color.BLACK);
```

2) Call `regret.undo()` or `regret.redo()`. The key-value data will be returned via the listener
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

3) Update UI related buttons such as Undo/Redo buttons with the following listener
```java
    @Override
    public void onCanDo(boolean canUndo, boolean canRedo) {
        btnUndo.setAlpha(canUndo ? 1 : 0.4f);
        btnRedo.setAlpha(canRedo ? 1 : 0.4f);
        btnUndo.setEnabled(canUndo);
        btnRedo.setEnabled(canRedo);
    }
```

## Installation

Add the dependency in your `build.gradle`
```groovy
dependencies {
    implementation 'com.muddzdev:regret:1.3.1'  
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
