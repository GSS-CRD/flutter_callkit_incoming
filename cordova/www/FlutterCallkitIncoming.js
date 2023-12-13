var exec = require('cordova/exec');

exports.on = (callback) => {
  exec((results) => {
    const { eventName, data } = results;
    callback(eventName, data);
  }, () => {
    new Error('CordovaCallkitIncomingPlugin on error');
  }, "CordovaCallkitIncomingPlugin", "on", []);
}

exports.showCallkitIncoming = async (params) => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "showCallkitIncoming", [params]);
  });
}

exports.showMissCallNotification = async (params) => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "showMissCallNotification", [params]);
  });
}

exports.startCall = async (params) => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "startCall", [params]);
  });
}

exports.muteCall = async (params) => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "muteCall", [params]);
  });
}

exports.holdCall = async (params) => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "holdCall", [params]);
  });
}

exports.isMuted = async (params) => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "isMuted", [params]);
  });
}

exports.endCall = async (params) => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "endCall", [params]);
  });
}

exports.callConnected = async (params) => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "callConnected", [params]);
  });
}

exports.endAllCalls = async () => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "endAllCalls", []);
  });
}

exports.activeCalls = async () => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "activeCalls", []);
  });
}

exports.getDevicePushTokenVoip = async () => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "getDevicePushTokenVoip", []);
  });
}

exports.requestNotificationPermission = async () => {
  return new Promise((resolve, reject) => {
    exec(resolve, reject, "CordovaCallkitIncomingPlugin", "requestNotificationPermission", []);
  });
}

