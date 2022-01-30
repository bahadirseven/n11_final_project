package com.n11.graduation.cs.util;

import com.n11.graduation.cs.constant.ErrorMessage;
import com.n11.graduation.cs.exception.CsNoSuchAlgorithmException;
import lombok.experimental.UtilityClass;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@UtilityClass
public class CreditScoreGeneratorUtil {
    private Random rand;

    static {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new CsNoSuchAlgorithmException(ErrorMessage.SCORE_NOT_GENERATED);
        }
    }

    public static Integer generateRandomCreditScore() {
        int min = 1;
        int max = 1900;
        return rand.nextInt(max - min) + min;
    }
}
