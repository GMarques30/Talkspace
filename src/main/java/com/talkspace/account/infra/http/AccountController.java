package com.talkspace.account.infra.http;

import com.talkspace.account.application.dto.SigninInput;
import com.talkspace.account.application.dto.SigninOutput;
import com.talkspace.account.application.dto.SignupInput;
import com.talkspace.account.application.usecase.Signin;
import com.talkspace.account.application.usecase.Signup;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final Signup signup;
    private final Signin signin;

    @Autowired
    public AccountController(Signup signup, Signin signin) {
        this.signup = signup;
        this.signin = signin;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignupInput input) {
        this.signup.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninOutput> signIn(@RequestBody SigninInput input) {
        SigninOutput output = this.signin.execute(input);
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }
}
