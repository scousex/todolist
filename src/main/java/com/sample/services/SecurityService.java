package com.sample.services;

import com.sample.entities.Note;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Сервис для обеспечения безопасности (авторизация, аутентификация).
 * Интерфейс.
 */

public interface SecurityService {

    String findUserInUsername();

    Authentication autoLogin(String username, String password);

    public String getUserByToken(String token);

    void logout();
}
