package com.realmofvalyron.ms_auth.service;

import com.realmofvalyron.ms_auth.dto.AuthResponse;
import com.realmofvalyron.ms_auth.dto.LoginRequest;
import com.realmofvalyron.ms_auth.dto.RegisterRequest;
import com.realmofvalyron.ms_auth.entity.Usuario;
import com.realmofvalyron.ms_auth.repository.UsuarioRepository;
import com.realmofvalyron.ms_auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El username ya está en uso");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
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

        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol().name());

        return AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .mensaje("Usuario registrado exitosamente")
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol().name());

        return AuthResponse.builder()
                .token(token)
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .mensaje("Login exitoso")
                .build();
    }

}