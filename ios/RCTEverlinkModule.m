//
//  RCTEverlinkModule.m
//  RNEverlinkSample
//
//  Created by Admin on 12/21/21.
//

#import "RCTEverlinkModule.h"

@implementation RCTEverlinkModule
{
  bool hasListeners;
}

// To export a module named RCTEverlinkModule
RCT_EXPORT_MODULE();

- (NSArray<NSString *> *)supportedEvents {
  return @[@"onAudioCodeReceived", @"onEverLinkError", @"onMyTokenGenerated"];
}

// Will be called when this module's first listener is added.
-(void)startObserving {
  hasListeners = YES;
  // Set up any upstream listeners or background tasks as necessary
}

-(void)stopObserving {
  hasListeners = NO;
  // Remove upstream listeners, stop unnecessary background tasks
}

RCT_EXPORT_METHOD(initialize:(NSString *)appIDKey)
{
}

RCT_EXPORT_METHOD(startListening:(BOOL)isOffline)
{
}

RCT_EXPORT_METHOD(stopListening)
{
}

RCT_EXPORT_METHOD(playVolume:(double)volume useLoudspeaker:(BOOL)useLoudspeaker)
{
}

RCT_EXPORT_METHOD(startEmitting)
{
}

RCT_EXPORT_METHOD(stopEmitting)
{
}

RCT_EXPORT_METHOD(startEmittingToken:(NSString *)token isOffline:(BOOL)isOffline)
{
}

RCT_EXPORT_METHOD(createNewToken:(NSString *)startDate)
{
}

RCT_EXPORT_METHOD(saveSounds:(NSArray *)startDate)
{
}

RCT_EXPORT_METHOD(clearSounds)
{
}

@end
