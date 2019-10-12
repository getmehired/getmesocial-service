package co.getmehired.social.rest;

import co.getmehired.social.exception.InvalidIdTokenException;
import co.getmehired.social.model.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class FirebaseInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if(!request.getRequestURI().contains("/api/users/register")) {

			String idToken = request.getHeader("idToken");
			FirebaseUser user = getUserIdFromIdToken(idToken);
			
			HttpSession session = request.getSession();
			if (user == null) {
				session.setAttribute("firebaseUser", null);
				throw new InvalidIdTokenException("Invalid Id Token");
			} else {
				session.setAttribute("firebaseUser", user);
			}
		}
		
		return true;
	}
	
	public FirebaseUser getUserIdFromIdToken(String idToken) {
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
