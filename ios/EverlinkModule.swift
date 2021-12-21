//
//  EverlinkModule.swift
//  RNEverlinkSample
//
//  Created by Admin on 12/21/21.
//

import Foundation

@objc(EverlinkModule)
class EverlinkModule: RCTEventEmitter {
  
  override func supportedEvents() -> [String]! {
    return ["onAudioCodeReceived", "onEverLinkError", "onMyTokenGenerated"]
  }
  
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }
  
  @objc func initialize(_ appIDKey: String) -> Void {
  }
  
  @objc func startListening(_ isOffline: Bool) -> Void {
  }
  
  @objc func stopListening() -> Void {
  }
  
  @objc func playVolume(_ volume: Double, useLoudspeaker: Bool) -> Void {
  }
  
  @objc func startEmitting() -> Void {
  }
  
  @objc func stopEmitting() -> Void {
  }
  
  @objc func startEmittingToken(_ token: String, isOffline: Bool) -> Void {
  }
  
  @objc func createNewToken(_ startDate: String) -> Void {
  }
  
  @objc func saveSounds(_ tokensArray: [String]) -> Void {
  }
  
  @objc func clearSounds() -> Void {
  }
  
}
