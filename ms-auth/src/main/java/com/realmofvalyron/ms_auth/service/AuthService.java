package com.realmofvalyron.ms_auth.service;

import com.realmofvalyron.ms_auth.dto.AuthResponse;
import com.realmofvalyron.ms_auth.dto.LoginRequest;
import com.realmofvalyron.ms_auth.dto.RegisterRequest;
import com.realmofvalyron.ms_auth.entity.Usuario;
import com.realmofvalyron.ms_auth.repository.UsuarioRepository;
import com.realmofvalyron.ms_auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        log.info("Iniciando registro para usuario: {}", request.getUsername());

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            log.warn("Intento de registro con username ya existente: {}", request.getUsername());
            throw new RuntimeException("El username ya está en uso");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            log.warn("Intento de registro con email ya existente: {}", request.getEmail());
            throw new RuntimeException("El email ya está en uso");
        }

        Usuario.Rol rol = Usuario.Rol.AVENTURERO;
        if (request.getRol() != null) {
            try {
                rol = Usuario.Rol.valueOf(request.getRol());
            } catch (IllegalArgumentException e) {
                rol = Usuario.Rol.AVENTURERO;
            }
        }

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(rol)
                .build();

        usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente: {} con rol: {}", usuario.getUsername(), usuario.getRol());

        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol().name());

        return AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .mensaje("Usuario registrado exitosamente")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Intento de login para usuario: {}", request.getUsername());

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.warn("Login fallido: usuario no encontrado: {}", request.getUsername());
                    return new RuntimeException("Usuario no encontrado");
                });

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            log.warn("Login fallido: contraseña incorrecta para usuario: {}", request.getUsername());
            throw new RuntimeException("Contraseña incorrecta");
        }
        
        log.info("Login exitoso para usuario: {}", request.getUsername());

        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol().name());

        return AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .mensaje("Login exitoso")
                .build();
    }

}