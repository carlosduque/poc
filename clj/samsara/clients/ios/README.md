# samsara-ios-sdk

[![CI Status](http://img.shields.io/travis/Sathyavijayan Vittal/samsara-ios-sdk.svg?style=flat)](https://travis-ci.org/Sathyavijayan Vittal/samsara-ios-sdk)
[![Version](https://img.shields.io/cocoapods/v/samsara-ios-sdk.svg?style=flat)](http://cocoapods.org/pods/samsara-ios-sdk)
[![License](https://img.shields.io/cocoapods/l/samsara-ios-sdk.svg?style=flat)](http://cocoapods.org/pods/samsara-ios-sdk)
[![Platform](https://img.shields.io/cocoapods/p/samsara-ios-sdk.svg?style=flat)](http://cocoapods.org/pods/samsara-ios-sdk)

## Usage

<<<<<<< HEAD
### Initialization
To initialize the Samsara client with default configuration, just do:

```swift
import samsara_ios_sdk

var config:SSConfig = ["url": "http://samsara-ingestion.local/",
"publishInterval": 60,
"maxBufferSize": 10000]

//Samsara.initializeWithConfig returns an Optional NSError
//if the config passed is incorrect. NSError.UserInfo
//contains the relevant error message.
if let err = Samsara.initializeWithConfig(config) {
NSLog("Failed to initialise Samsara")
}
```

### Publish an Event
To publish events in bulk, do:

```swift
//
var events:[SSEvent] = [
   ["timestamp": 1433004485870,
    "sourceId": "johndoe@gmail.com",
    "eventName": "session.started"],
   ["timestamp": 1433004490171,
    "sourceId": "johndoe@gmail.com",
    "eventName": "user.scored",
    "location-x": Float(ex),
    "location-y": Float(ey)],
   ["timestamp": 1433004494454,
    "sourceId": "johndoe@gmail.com",
    "eventName": "session.ended"]]

Samsara.sharedInstance?.publishEvents(events)

//publishEvents also provides the ability to configure
//a completionHandler.
Samsara.sharedInstance.publishEvents(events) {(error:NSError?) -> Void in
//Handle errors here.
}
```
Samsara client can also buffer events in a ring buffer and flush them to the API periodically. To record events in the buffer do:

```swift
Samsara.sharedInstance.recordEvent([eventName: "app.opened"])

//recordEvent also provides the ability to configure
//a completionHandler.
Samsara.sharedInstance.recordEvent(event) {(error:NSError?) -> Void in
//Handle errors here.
}
```

### Structure of the Samsara Event
The following properties are mandator

```swift
[
 "timestamp": 1432822847123,      //autopopulated if not supplied.
 "sourceId": "johndoe@gmail.com", //defaulted to UDID
 "eventName": "user.scored",
]
```

### App Lifecycle events
Samsara client automatically publishes the following app lifecycle events:

* UIApplicationWillTerminateNotification
* UIApplicationWillResignActiveNotification
* UIApplicationDidBecomeActiveNotification
* UIApplicationWillEnterForegroundNotification
* UIApplicationDidEnterBackgroundNotification
* UIApplicationDidReceiveMemoryWarningNotification
* UIApplicationSignificantTimeChangeNotification
* UIApplicationDidFinishLaunchingNotification

The eventName for these events will follow the pattern below:

```Swift
"app.willterminate"
```

### Device Info
Samsara client automatically gathers and publishes information about the iOS device from the UIDevice class. It collects the following parameters:
* device_name
* system_name
* system_version
* model
* localisedModel


### UDID - Unique Device Identifier
Samsara client will auto generate an Unique Identifier for each device. This property will be added to every event in the "UDID" field. The UDID generated is preserved even when the app is deleted and re-installed.

Samsara will associate all events originiating from the device with this UDID.

If sourceId is not supplied in the event, Samsara client autopopulates it with UDID.

### Configuration
Samsara client buffers events and peridically flushes them to Samsara API. If network is not available or if Samsara API is not reachable, the events are retained in the buffer. The event buffer is a ring buffer whose max size can be configured. When the buffers hit their max size, the oldest items in the buffer are removed. The interval to publish events can also be configured.


```swift
var config:SSConfig = ["url": "http://samsara-ingestion.local/",
"publishInterval": 60,
"maxBufferSize": 10000]

//Samsara.initializeWithConfig returns an Optional NSError
//if the config passed is incorrect. NSError.UserInfo
//contains the relevant error message.
if let err = Samsara.initializeWithConfig(config) {
NSLog("Failed to initialise Samsara")
}
```


### Upcoming features
* Archive events to a file when 'willTerminate' event is received.
* Automatically resume uploads when network is available.
* If user allows capturing location automatically derive the location and append it with the event.
=======
To see how to use this client please [read the documentation](/docs/clients/ios-client.md)
>>>>>>> master
