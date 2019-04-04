Pod::Spec.new do |s|
    s.name              = 'react-native-ffmpeg'
    s.version           = '0.3.2'
    s.summary           = 'FFmpeg for React Native'
    s.description       = 'React Native FFmpeg based on mobile-ffmpeg'
    s.homepage          = 'https://github.com/tanersener/react-native-ffmpeg'

    s.author            = { 'Taner Sener' => 'tanersener@gmail.com' }
    s.license           = { :type => 'LGPL-3.0' }

    s.platform          = :ios
    s.requires_arc      = true
    s.ios.deployment_target = '9.3'
    s.static_framework  = true

    s.source            = { :git => 'https://github.com/tanersener/react-native-ffmpeg.git', :tag => 'v0.3.2'}
    s.source_files      = 'ios/RNFFmpegModule.m',
                          'ios/RNFFmpegModule.h'

    s.default_subspec   = 'https'

    s.dependency        'React'

    s.subspec 'min' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-min', '4.2.1'
    end

    s.subspec 'min-lts' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-min', '4.2.LTS'
    end

    s.subspec 'min-gpl' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-min-gpl', '4.2.1'
    end

    s.subspec 'min-gpl-lts' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-min-gpl', '4.2.LTS'
    end

    s.subspec 'https' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-https', '4.2.1'
    end

    s.subspec 'https-lts' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-https', '4.2.LTS'
    end

    s.subspec 'https-gpl' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-https-gpl', '4.2.1'
    end

    s.subspec 'https-gpl-lts' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-https-gpl', '4.2.LTS'
    end

    s.subspec 'audio' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-audio', '4.2.1'
    end

    s.subspec 'audio-lts' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-audio', '4.2.LTS'
    end

    s.subspec 'video' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-video', '4.2.1'
    end

    s.subspec 'video-lts' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-video', '4.2.LTS'
    end

    s.subspec 'full' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-full', '4.2.1'
    end

    s.subspec 'full-lts' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-full', '4.2.LTS'
    end

    s.subspec 'full-gpl' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-full-gpl', '4.2.1'
    end

    s.subspec 'full-gpl-lts' do |ss|
        ss.source_files      = 'ios/RNFFmpegModule.m',
                               'ios/RNFFmpegModule.h'
        ss.dependency 'mobile-ffmpeg-full-gpl', '4.2.LTS'
    end

end
