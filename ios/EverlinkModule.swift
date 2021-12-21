//
//  EverlinkModule.swift
//  RNEverlinkSample
//
//  Created by Admin on 12/21/21.
//

import Foundation
import EverlinkBroadcastSDK

@objc(EverlinkModule)
class EverlinkModule: RCTEventEmitter, EverlinkConnectDelegate {
  
  var connectObj:Everlink?
  
  override func supportedEvents() -> [String]! {
    return ["onAudioCodeReceived", "onEverLinkError", "onMyTokenGenerated"]
  }
  
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }
  
  @objc func initialize(_ appIDKey: String) -> Void {
    connectObj = Everlink(appID: appIDKey)
    connectObj?.delegate = self
  }
  
  func onAudioCodeReceived(token: String) {
    print(token)
    sendEvent(withName: "onAudioCodeReceived", body: token)
  }
  
  func onMyTokenGenerated(token: String, oldToken: String) {
    print("New token: " + token)
    print("Old token: " + oldToken)
    sendEvent(withName: "onMyTokenGenerated", body: ["oldToken": oldToken, "newToken": token])
  }
  
  @objc func startListening(_ isOffline: Bool) -> Void {
    connectObj?.startRecording(isOffline: isOffline)
  }
  
  @objc func stopListening() -> Void {
    connectObj?.stopRecording()
  }
  
  @objc func playVolume(_ volume: Double, useLoudspeaker: Bool) -> Void {
    connectObj?.playVolume(volume: Float(volume), loudspeaker: useLoudspeaker)
  }
  
  @objc func startEmitting() -> Void {
    connectObj?.startEmitting()
  }
  
  @objc func stopEmitting() -> Void {
    connectObj?.stopEmitting()
  }
  
  @objc func startEmittingToken(_ token: String, isOffline: Bool) -> Void {
    connectObj?.startEmittingToken(token: token, isOffline: isOffline)
  }
  
  @objc func createNewToken(_ startDate: String) -> Void {
    connectObj?.newToken(startDate: startDate)
  }
  
  @objc func saveSounds(_ tokensArray: [String]) -> Void {
    connectObj?.saveSounds(tokensArray: tokensArray)
  }
  
  @objc func clearSounds() -> Void {
    connectObj?.clearSounds()
  }
  
}
