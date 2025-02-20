[![](https://jitpack.io/v/v-shahzadahmad/Fragmentation.svg)](https://jitpack.io/#v-shahzadahmad/Fragmentation)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0)

# PLEASE NOTE, THIS PROJECT IS NO LONGER BEING MAINTAINED

# FragmentationX

![](/gif/logo.png)

Fragmentation is a powerful library managing Fragment for Android.

It is designed for "Single Activity + Multi-Fragments" and "Multi-FragmentActivities + Multi-Fragments" architecture to simplify development process.

## Demo
The first demo shows the basic usage of the library. The second one shows the way to implement an app which is similar to Instagram. Complicated nested fragments' usage demo are also showed.

## [Download APK](https://www.pgyer.com/fragmentation)

<img src="/gif/demo1.gif" width="280px"/> <img src="/gif/demo2.gif" width="280px"/>
 <img src="/gif/demo3.gif" width="280px"/>

## Feature

**1. Develop complicated nested fragment app rapidly**

**2. Use fragment's stack view dialog to debug easily**

**3. Add launch mode, startForResult etc. to provide similar behavior of Activity**

**4. Add onBackPressedSupport() method to support back button press monitoring in Fragment**

**5. Add onSupportVisible(), onLazyInitView() to simplify dev**

**6. Easily manage Fragment transition animations**

**7. To simplify the communication between Fragment([EventBusActivityScope module](https://github.com/YoKeyword/Fragmentation/blob/master/eventbus_activity_scope/README.md))**

**8. Support SwipeBack to pop(Fragmentation_SwipeBack module [README](https://github.com/YoKeyword/Fragmentation/blob/master/fragmentation_swipeback/README.md))**

<img src="/gif/stack.png" width="150px"/> <img src="/gif/log.png" width="300px"/>       <img src="/gif/SwipeBack.png" width="150px"/>

## How do I use Fragmentation?
### Note: This is the use of androidx, if you are using the android.support, [click here `branch:master`](https://github.com/YoKeyword/Fragmentation/blob/master/README.md)
**1、build.gradle**
````gradle

// Add it in your root settings.gradle at the end of repositories:
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

// This is the use of androidx, if you are using the android.support: fragmentationx -> fragmentation

implementation 'com.github.Jibestream:fragmentation:2.1.0'

// If you don't want to extends SupportActivity/Fragment and would like to customize your own support, just rely on fragmentation-core
implementation 'com.github.Jibestream:fragmentation_core:2.1.0'

// Swipeback is based on fragmentation. Refer to SwipeBackActivity/Fragment for your Customized SupportActivity/Fragment
implementation 'com.github.Jibestream:fragmentation_swipeback:2.1.0'

// To simplify the communication between Fragments.
implementation 'com.github.Jibestream:eventbus_activity_scope:2.1.0'

// Your EventBus's version
implementation 'org.greenrobot:eventbus:{version}'
````

**2. Activity `extends` SupportActivity or `implements` ISupportActivity：(refer to [MySupportActivity](https://github.com/YoKeyword/Fragmentation/blob/master/demo/src/main/java/me/yokeyword/sample/demo_flow/base/MySupportActivity.java))**
````java
// since v1.0.0, forced extends of SupportActivity is not required, you can use interface + delegate to implement your own SupportActivity 
public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(...);
      	// Fragmentation is recommended to initialize in the Application
        Fragmentation.builder()
          	 // show stack view. Mode: BUBBLE, SHAKE, NONE
             .stackViewMode(Fragmentation.BUBBLE)
             .debug(BuildConfig.DEBUG)
             ...
             .install();

        if (findFragment(HomeFragment.class) == null) {
            loadRootFragment(R.id.fl_container, HomeFragment.newInstance());  //load root Fragment
        }
    }
````

**3. Fragment `extends` SupportFragment or `implements` ISupportFragment：(refer to [MySupportFragment](https://github.com/YoKeyword/Fragmentation/blob/master/demo/src/main/java/me/yokeyword/sample/demo_flow/base/MySupportFragment.java))：**
````java
// since v1.0.0, forced extends of SupportActivity is not required, you can use interface + delegate to implement your own SupportActivity
public class HomeFragment extends SupportFragment {

    private void navigate() {
      	// launch a new Fragment, other methods: start(fragment,SINGTASK)、startForResult、startWithPop etc.
        start(DetailFragment.newInstance(HomeBean));
      	// check wiki for other pop, find and animation setting related API
    }
}
````

## [WIKI](https://github.com/YoKeyword/Fragmentation/wiki) , [CHANGELOG](https://github.com/YoKeyword/Fragmentation/blob/master/CHANGELOG.md)

## LICENSE
````
Copyright 2016 YoKey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
````
