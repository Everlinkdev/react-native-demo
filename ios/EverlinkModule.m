//
//  EverlinkModule.m
//  RNEverlinkSample
//
//  Created by Admin on 12/21/21.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(EverlinkModule, RCTEventEmitter)

RCT_EXTERN_METHOD(initialize:(NSString *)appIDKey)

RCT_EXTERN_METHOD(startListening:(BOOL)isOffline)

RCT_EXTERN_METHOD(stopListening)

RCT_EXTERN_METHOD(playVolume:(double)volume useLoudspeaker:(BOOL)useLoudspeaker)

RCT_EXTERN_METHOD(startEmitting)

RCT_EXTERN_METHOD(stopEmitting)

RCT_EXTERN_METHOD(startEmittingToken:(NSString *)token isOffline:(BOOL)isOffline)

RCT_EXTERN_METHOD(createNewToken:(NSString *)startDate)

RCT_EXTERN_METHOD(saveSounds:(NSArray *)tokensArray)

RCT_EXTERN_METHOD(clearSounds)

@end
