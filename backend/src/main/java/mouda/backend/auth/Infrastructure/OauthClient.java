package mouda.backend.auth.Infrastructure;

public interface OauthClient {

	String getIdToken(String code);
}
