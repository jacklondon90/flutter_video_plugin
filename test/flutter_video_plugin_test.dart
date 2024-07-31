import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_video_plugin/flutter_video_plugin.dart';
import 'package:flutter_video_plugin/flutter_video_plugin_platform_interface.dart';
import 'package:flutter_video_plugin/flutter_video_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterVideoPluginPlatform
    with MockPlatformInterfaceMixin
    implements FlutterVideoPluginPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterVideoPluginPlatform initialPlatform =
      FlutterVideoPluginPlatform.instance;

  test('$MethodChannelFlutterVideoPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterVideoPlugin>());
  });

  test('getPlatformVersion', () async {
    SwiftFlutterVideoPlugin flutterVideoPlugin = SwiftFlutterVideoPlugin();
    MockFlutterVideoPluginPlatform fakePlatform =
        MockFlutterVideoPluginPlatform();
    FlutterVideoPluginPlatform.instance = fakePlatform;

//    expect(await flutterVideoPlugin.getPlatformVersion(), '42');
  });
}
