package com.hiennv.flutter_callkit_incoming

import android.app.Activity
import android.content.Context
import android.util.Log
import org.apache.cordova.*
import org.json.JSONArray
import org.json.JSONObject

class CordovaCallkitIncomingPlugin : CordovaPlugin() {
    companion object {
        const val PREFIX_TAG: String = "flutter_callkit_incoming"
        private const val TAG: String = "$PREFIX_TAG (CordovaCallkitIncomingPlugin)"

        const val EXTRA_CALLKIT_CALL_DATA = "EXTRA_CALLKIT_CALL_DATA"
    }


    private val activity: Activity? get() = cordova.activity as? Activity
    private val context: Context get() = activity?.applicationContext!!
    private val callkitNotificationManager: CallkitNotificationManager get() = CallkitNotificationManager(context)

//    public fun showIncomingNotification(data: Data) {
//        data.from = "notification"
//        callkitNotificationManager?.showIncomingNotification(data.toBundle())
//        //send BroadcastReceiver
//        context?.sendBroadcast(
//            CallkitIncomingBroadcastReceiver.getIntentIncoming(
//                requireNotNull(context),
//                data.toBundle()
//            )
//        )
//    }
//
//    public fun showMissCallNotification(data: Data) {
//        callkitNotificationManager?.showIncomingNotification(data.toBundle())
//    }
//
//    public fun startCall(data: Data) {
//        context?.sendBroadcast(
//            CallkitIncomingBroadcastReceiver.getIntentStart(
//                requireNotNull(context),
//                data.toBundle()
//            )
//        )
//    }
//
//    public fun endCall(data: Data) {
//        context?.sendBroadcast(
//            CallkitIncomingBroadcastReceiver.getIntentEnded(
//                requireNotNull(context),
//                data.toBundle()
//            )
//        )
//    }
//
//    public fun endAllCalls() {
//        val calls = getDataActiveCalls(context)
//        calls.forEach {
//            context?.sendBroadcast(
//                CallkitIncomingBroadcastReceiver.getIntentEnded(
//                    requireNotNull(context),
//                    it.toBundle()
//                )
//            )
//        }
//        removeAllCalls(context)
//    }

    /**
     * Performs various push plugin related tasks:
     *
     *  - Initialize
     *  - Unregister
     *  - Has Notification Permission Check
     *  - Set Icon Badge Number
     *  - Get Icon Badge Number
     *  - Clear All Notifications
     *  - Clear Notification
     *  - Subscribe
     *  - Unsubscribe
     *  - Create Channel
     *  - Delete Channel
     *  - List Channels
     *
     *  @param action
     *  @param data
     *  @param callbackContext
     */
    override fun execute(
        action: String,
        data: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
        Log.v("TAG", "Execute: Action = $action")

        when (action) {
            "showCallkitIncoming" -> {
                data.getJSONObject(0)?.let {
                    val data = Data(toMap(it))
                    data.from = "notification"
                    //send BroadcastReceiver
                    context?.sendBroadcast(
                        CallkitIncomingBroadcastReceiver.getIntentIncoming(
                            requireNotNull(context),
                            data.toBundle()
                        )
                    )
                }
                callbackContext.success()
            }
            "showMissCallNotification" -> {
                data.getJSONObject(0)?.let {
                    val data = Data(toMap(it))
                    data.from = "notification"
                    callkitNotificationManager?.showMissCallNotification(data.toBundle())
                }
                callbackContext.success()
            }
            "startCall" -> {
                data.getJSONObject(0)?.let {
                    val data = Data(toMap(it))
                    context?.sendBroadcast(
                        CallkitIncomingBroadcastReceiver.getIntentStart(
                            requireNotNull(context),
                            data.toBundle()
                        )
                    )
                }
                callbackContext.success()
            }
            "muteCall" -> {
                callbackContext.success()
            }
            "holdCall" -> {
                callbackContext.success()
            }
            "isMuted" -> {
                callbackContext.success("false")
            }
            "endCall" -> {
                data.getJSONObject(0)?.let {
                    context?.sendBroadcast(
                        CallkitIncomingBroadcastReceiver.getIntentEnded(
                            requireNotNull(context),
                            Data(toMap(it)).toBundle()
                        )
                    )
                }
                callbackContext.success("OK")
            }
            "callConnected" -> {
                callbackContext.success("OK")
            }
            "endAllCalls" -> {
                val calls = getDataActiveCalls(context)
                calls.forEach {
                    if (it.isAccepted) {
                        context?.sendBroadcast(
                            CallkitIncomingBroadcastReceiver.getIntentEnded(
                                requireNotNull(context),
                                it.toBundle()
                            )
                        )
                    } else {
                        context?.sendBroadcast(
                            CallkitIncomingBroadcastReceiver.getIntentDecline(
                                requireNotNull(context),
                                it.toBundle()
                            )
                        )
                    }
                }
                removeAllCalls(context)
                callbackContext.success("OK")
            }
            "activeCalls" -> {
                callbackContext.success(JSONArray(getDataActiveCallsForFlutter(context)))
            }
            "getDevicePushTokenVoIP" -> {
                callbackContext.success("")
            }
            "requestNotificationPermission" -> {
                data.getJSONObject(0)?.let {
                    callkitNotificationManager.requestNotificationPermission(activity, toMap(it))
                }
            }
            else -> {
                Log.e(TAG, "Execute: Invalid Action $action")
                callbackContext.sendPluginResult(PluginResult(PluginResult.Status.INVALID_ACTION))
                return false
            }
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        callkitNotificationManager.onRequestPermissionsResult(activity, requestCode, grantResults)
    }

    private fun toMap(jsonObject: JSONObject): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        val keysItr = jsonObject.keys()
        while (keysItr.hasNext()) {
            val key = keysItr.next()
            var value = jsonObject[key]
            if (value is JSONArray) {
                value = toList(value)
            } else if (value is JSONObject) {
                value = toMap(value)
            }
            map[key] = value!!
        }
        return map
    }

    private fun toList(array: JSONArray): List<Any> {
        val list: MutableList<Any> = ArrayList()
        for (i in 0 until array.length()) {
            var value = array[i]
            if (value is JSONArray) {
                value = toList(value)
            } else if (value is JSONObject) {
                value = toMap(value)
            }
            list.add(value!!)
        }
        return list
    }
}