package com.git.ravaee.tezpishir.utility;

import java.util.Random;

public class Config {

    public static String SERVER_API_URL(){
        return "http://10.0.2.2:3000/api/v1/";
    }

    public static String SERVER_PUBLIC_URL(){
        return "http://10.0.2.2:3000/";
    }

    public static String GOOGLE_API_URL(){
        return "https://www.googleapis.com/";
    }

    public static String getRandomString() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

}
