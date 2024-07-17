import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_video_plugin_method_channel.dart';

abstract class FlutterVideoPluginPlatform extends PlatformInterface {
  /// Constructs a FlutterVideoPluginPlatform.
  FlutterVideoPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterVideoPluginPlatform _instance = MethodChannelFlutterVideoPlugin();

  /// The default instance of [FlutterVideoPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterVideoPlugin].
  static FlutterVideoPluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterVideoPluginPlatform] when
  /// they register themselves.
  static set instance(FlutterVideoPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
