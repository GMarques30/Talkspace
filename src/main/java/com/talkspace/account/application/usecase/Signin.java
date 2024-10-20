package com.talkspace.account.application.usecase;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.talkspace.account.application.dto.SigninInput;
import com.talkspace.account.application.dto.SigninOutput;
import com.talkspace.account.application.exception.InvalidCredentials;
import com.talkspace.account.application.repository.AccountRepository;
import com.talkspace.account.domain.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class Signin {
    private final AccountRepository accountRepository;

    @Value("${api.security.token.secret}")
    private String secretKey;

    @Autowired
    public Signin(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public SigninOutput execute(SigninInput input) {
        Account account = this.accountRepository.findByEmail(input.email()).orElseThrow(InvalidCredentials::new);
        if(!account.passwordMatches(input.password())) throw new InvalidCredentials();
        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
        String token = JWT.create()
                .withIssuer("talkspace-api")
                .withSubject(account.accountId)
                .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                .sign(algorithm);
        return new SigninOutput(token);
    }
}
