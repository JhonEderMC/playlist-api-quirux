package com.playlist.infrastructure.config;

import com.playlist.domain.model.Usuario;
import com.playlist.domain.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario por defecto si no existe
        if (usuarioService.obtenerPorNombre("admin").isEmpty()) {
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .password( passwordEncoder.encode("admin123"))
                    .role("ADMIN")
                    .build();

            usuarioService.crearUsuario(admin);
            System.out.println("Usuario admin creado: admin/admin123");
        }

        if (usuarioService.obtenerPorNombre("user").isEmpty()) {
            Usuario user = Usuario.builder()
                    .username("user")
                    .password( passwordEncoder.encode("user123"))
                    .role("USER")
                    .build();

            usuarioService.crearUsuario(user);
            System.out.println("Usuario user creado: user/user123");
        }
    }
}