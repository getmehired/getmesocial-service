package co.getmehired.social.util;

import co.getmehired.social.exception.InvalidIdTokenException;
import co.getmehired.social.model.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.util.StringUtils;

/**
 * Created by Supal on 13-Nov-19.
 */
public class FirebaseUtil {

    public static FirebaseUser getUserIdFromIdToken(String idToken) {
        String uid = null;
        String name = null;
        String email = null;
        try {
            uid = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getUid();
            name = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getName();
            email = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getEmail();
        } catch (Exception e) {
            throw new InvalidIdTokenException("Invalid Id Token.");
        }

        if (!StringUtils.isEmpty(uid) && !StringUtils.isEmpty(email)) {
            FirebaseUser firebaseUser = new FirebaseUser(uid, name, email);
            return firebaseUser;
        } else {
            return null;
        }
    }


}
