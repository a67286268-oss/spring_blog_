package com.tenco.spring_blog._core.config;

import com.tenco.spring_blog._core.interceptor.AdminInterceptor;
import com.tenco.spring_blog._core.interceptor.LoginInterceptor;
import com.tenco.spring_blog._core.interceptor.SessionUserModelInterceptor;
import com.tenco.spring_blog._core.util.FileUtil;
import com.tenco.spring_blog.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

// IoC
@RequiredArgsConstructor
@Configuration // 스프링 설정 크래스임을 표시 (빈으로도 등록 됨)
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final SessionUserModelInterceptor sessionUserModelInterceptor;
    private final AdminInterceptor adminInterceptor;

    // 업로드 폴더의 파일일 /images/** 주소로 제공
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드 폴더 경로를 file:///.... 형식의 주소로 변환(운영체제에 맞게 자동 변환)
        String location = Paths.get(FileUtil.UPLOAD_DIR).toUri().toString();
        // 윈도우 기준 : C:\Users\사용자명\blog_uploads\

        // 폴더 위치는 반드시 / 로 끝나야 그 안의 파일을 찾을 수 있임
        if (!location.endsWith("/")) {
            location = location + "/";
        }
        registry.addResourceHandler("/images/**")
                .addResourceLocations(location);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. 화면에 사용자 정보 자동 주입 인터셉터
        registry.addInterceptor(sessionUserModelInterceptor)
                .addPathPatterns("/**"); // 모든 경로
        // 2. 로그인 검사
        registry.addInterceptor(loginInterceptor)
                // 인터셉터가 동작할 URL 패턴을 지정
                .addPathPatterns("/user/**", "/board/**", "/reply/**",
                        "/admin/**", "/refund/**")
                // 인터셉터에서 제외할 URL 패턴을 지정
                .excludePathPatterns("/board/list", "/board/{id:\\d+}");
        // 3. 관리자 검사 : 로그인 검사 다음에 등록 (등록한 순서대로 실행 됨)
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");
    }

    // 비밀번호 해싱 도구를 빈으로 등록 (필요한 곳에서 DI 할 수 있다)
    @Bean // IoC 처리 됨
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
