package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by lauremendieb on 06/08/2017.
 */

public class Compose extends AppCompatActivity {
    private String profileImageUrl;
    private EditText etComposeTweet;

    public Compose () {
    }

    public static Compose newInstance (String profileImageUrl) {
        // get the user profile image URL to display
        Compose compose = new Compose();
        Bundle args = new Bundle();
        args.putString("profileImageUrl", profileImageUrl);
        return compose;
    }
}
