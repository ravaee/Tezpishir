package com.git.ravaee.tezpishir.utility;

public interface Session {

    boolean isLoggedIn();

    void saveToken(String token);

    String getToken();

    void saveEmail(String email);

    String getEmail();

    void saveName(String name);

    String getName();

    void saveImage(String image);

    String getImage();


}
