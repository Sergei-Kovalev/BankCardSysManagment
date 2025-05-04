package jdev.kovalev.BankCardSysManagment.service.impl;

import jdev.kovalev.BankCardSysManagment.dto.request.JwtRequestDto;
import jdev.kovalev.BankCardSysManagment.dto.response.JwtResponseDto;
import jdev.kovalev.BankCardSysManagment.entity.User;
import jdev.kovalev.BankCardSysManagment.exception.UserNotFoundException;
import jdev.kovalev.BankCardSysManagment.repository.UserRepository;
import jdev.kovalev.BankCardSysManagment.service.AuthService;
import jdev.kovalev.BankCardSysManagment.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public JwtResponseDto createAuthToken(@RequestBody JwtRequestDto authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        return new JwtResponseDto(user.getUserId().toString(), token);
    }
}
