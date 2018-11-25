package utils;

import com.jimmoores.quandl.SessionOptions;
import com.jimmoores.quandl.classic.ClassicQuandlSession;

public class SessionUtility {

    private static String apiKey = "wyx9oz7nE2po9YVU9k-1";

    public static ClassicQuandlSession getSessionId() {

        SessionOptions sessionParameters = SessionOptions.Builder.withAuthToken(apiKey).build();
        return ClassicQuandlSession.create(sessionParameters);
    }
}
