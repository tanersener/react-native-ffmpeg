Pod::Spec.new do |s|
    s.name              = 'react-native-ffmpeg-https-gpl'
    s.version           = '0.0.1'
    s.summary           = 'FFmpeg for React Native'
    s.description       = 'React Native FFmpeg based on mobile-ffmpeg-https-gpl'
    s.homepage          = 'https://github.com/tanersener/react-native-ffmpeg'

    s.author            = { 'Taner Sener' => 'tanersener@gmail.com' }
    s.license           = { :type => 'GPL-3.0' }

    s.platform          = :ios
    s.requires_arc      = true
    s.ios.deployment_target = '8.0'

    s.source            = { :http => 'https://www.dropbox.com/s/9jiszzz8fstemoq/Archive.zip' }
    s.source_files      = 'RNFFmpegModule.m',
                          'RNFFmpegModule.h'

    s.dependency        'mobile-ffmpeg-https-gpl'

    s.xcconfig = { 'HEADER_SEARCH_PATHS' => '"${PODS_ROOT}/Headers/Public"/**' }

end
