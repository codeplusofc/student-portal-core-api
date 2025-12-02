package br.com.student.portal.service;

import br.com.student.portal.dto.user.UserRequest;
import br.com.student.portal.dto.user.UserResponse;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.mapper.UserMapper;
import br.com.student.portal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static br.com.student.portal.validation.UserValidator.validateFieldsUserRequest;

@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registrar novo usuário
     * Cria um novo usuário no banco de dados com senha criptografada
     */
    public UserResponse registerUser(UserRequest userRequest) {
        // Validar campos do formulário
        validateFieldsUserRequest(userRequest);

        // Converter DTO para Entity usando o mapper
        UserEntity user = userMapper.userRequestIntoUserEntity(userRequest);

        // ✅ Criptografar a senha com BCrypt
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        // Salvar no banco de dados
        UserEntity savedUser = userRepository.save(user);

        // Retornar resposta
        return userMapper.userEntityIntoUserResponse(savedUser);
    }

    /**
     * Fazer login - Autenticar usuário com matrícula e senha
     *
     * @param registration Matrícula do usuário
     * @param password Senha em texto plano (não criptografada)
     * @return Dados do usuário se autenticado com sucesso
     * @throws ObjectNotFoundException Se usuário não encontrado
     * @throws BadRequestException Se senha incorreta
     */
    public UserResponse login(String registration, String password) {
        // Buscar usuário no banco pela matrícula
        UserEntity user = userRepository.findByRegistration(registration)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Usuário com matrícula " + registration + " não encontrado"
                ));

        // ✅ IMPORTANTE: Usar PasswordEncoder.matches() para verificar a senha
        // Isso compara a senha em texto plano com a senha criptografada no banco
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Matrícula ou senha incorretos");
        }

        // ✅ Senha correta! Retornar dados do usuário
        return userMapper.userEntityIntoUserResponse(user);
    }
}