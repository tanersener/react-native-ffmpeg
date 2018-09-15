Pod::Spec.new do |s|
    s.name              = 'react-native-ffmpeg-audio'
    s.version           = '0.0.1'
    s.summary           = 'FFmpeg for React Native'
    s.description       = 'React Native FFmpeg based on mobile-ffmpeg-audio'
    s.homepage          = 'https://github.com/tanersener/react-native-ffmpeg'

    s.author            = { 'Taner Sener' => 'tanersener@gmail.com' }
    s.license           = { :type => 'LGPL-3.0' }

    s.platform          = :ios
    s.requires_arc      = true
    s.ios.deployment_target = '8.0'

    s.source            = { :http => 'https://www.dropbox.com/s/atj0nh1jwkz9ssj/Archive.zip' }
    s.source_files      = 'RNFFmpegModule.m',
                          'RNFFmpegModule.h'

    s.dependency        'mobile-ffmpeg-audio'

    s.xcconfig = { 'HEADER_SEARCH_PATHS' => '"${PODS_ROOT}/Headers/Public"/**' }

end
