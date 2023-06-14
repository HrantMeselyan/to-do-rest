package com.example.todo.endpoint;

;
import com.example.todo.dto.CreateUserRequestDto;
import com.example.todo.dto.UserAuthRequestDto;
import com.example.todo.dto.UserAuthResponseDto;
import com.example.todo.dto.UserDto;
import com.example.todo.entity.Role;
import com.example.todo.entity.User;
import com.example.todo.mapper.UserMapper;
import com.example.todo.repository.UserRepository;
import com.example.todo.security.CurrentUser;
import com.example.todo.service.UserService;
import com.example.todo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserEndpoint {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;
    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userService.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(userAuthRequestDto.getEmail());
        return ResponseEntity.ok(new UserAuthResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto) {
        Optional<User> byEmail = userService.findByEmail(createUserRequestDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(createUserRequestDto);
        return ResponseEntity.ok(userMapper.mapToDto(userService.save(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> user(@PathVariable("id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(userMapper.mapToDto(byId.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        if (userService.delete(id,currentUser.getUser().getId())) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") int id, @RequestBody CreateUserRequestDto user) {
        Optional<User> byId = userService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if (byEmail.isPresent() && byEmail.get().getId() != id) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User userFromDb = byId.get();
        if (user.getName() != null && !user.getName().isEmpty()) {
            userFromDb.setName(user.getName());
        }
        if (user.getSurname() != null && !user.getSurname().isEmpty()) {
            userFromDb.setSurname(user.getSurname());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            user.setEmail(user.getEmail());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userFromDb.setRole(Role.USER);
        return ResponseEntity.ok(userMapper.mapToDto(userService.save(userFromDb)));
    }

}
