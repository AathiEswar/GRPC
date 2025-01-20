package org.fslabs.Collections;

import com.fslabs.collections.Login;
import com.fslabs.collections.SignIn;
import com.fslabs.collections.SignUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneOf {
    private static final Logger logger = LoggerFactory.getLogger(OneOf.class);

    public static void main(String[] args) {
        SignIn signIn = SignIn.newBuilder()
                .setEmail("aathi28082003@gmail.com")
                .setPassword("********")
                .build();

        SignUp signUp = SignUp.newBuilder()
                .setEmail("aathi12345@gmail.com")
                .setPassword("123456789")
                .setConfirmPassword("123456789")
                .build();

        logger.info("Sign Up -  {}" , signUp);
        logger.info("Sign In -  {}" , signIn);

        Login login1 = Login.newBuilder()
                .setSignIn(signIn)
                .build();

        Login login2 = Login.newBuilder()
                .setSignUp(signUp)
                .build();

        Auth(login1);
        Auth(login2);

    }

    public static void Auth(Login login){
        logger.info("{}" ,login.getLoginCase());
    }
}
