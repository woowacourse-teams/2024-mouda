package mouda.backend.auth.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import mouda.backend.auth.dto.request.LoginRequest;
import mouda.backend.auth.dto.response.LoginResponse;
import mouda.backend.auth.dto.response.OauthResponse;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.security.JwtProvider;

@Service
public class AuthService {

	private final JwtProvider jwtProvider;

	private final MemberRepository memberRepository;
	private final RestClient restClient;

	public AuthService(JwtProvider jwtProvider, MemberRepository memberRepository, RestClient restClient) {
		this.jwtProvider = jwtProvider;
		this.memberRepository = memberRepository;
		this.restClient = restClient;
	}

	public LoginResponse login(LoginRequest loginRequest) {
		return memberRepository.findByNickname(loginRequest.nickname())
			.map(member -> {
				String token = jwtProvider.createToken(member);
				return new LoginResponse(token);
			})
			.orElseGet(() -> {
				Member newMember = new Member(loginRequest.nickname());
				memberRepository.save(newMember);
				String token = jwtProvider.createToken(newMember);
				return new LoginResponse(token);
			});
	}

	public Member findMember(String token) {
		long memberId = jwtProvider.extractMemberId(token);
		return memberRepository.findById(memberId)
			.orElseThrow(
				() -> new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.UNAUTHORIZED));
	}

	public void checkAuthentication(String token) {
		jwtProvider.validateExpiration(token);
	}

	public String oauthLogin(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set(HttpHeaders.ACCEPT_CHARSET, "utf-8");

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("client_id", "ca3adf9a52671fdbb847b809c0fdb980");
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", "http://localhost:8081/login2");

		ResponseEntity<OauthResponse> response = restClient.method(HttpMethod.POST)
			.uri("/oauth/token")
			.headers(httpHeaders -> httpHeaders.addAll(headers))
			.body(formData)
			.retrieve()
			.toEntity(OauthResponse.class);

		String kakaoIdToken = response.getBody().id_token();
		try {
			// JWT는 점(.)으로 구분된 세 부분으로 이루어져 있다
			String[] parts = kakaoIdToken.split("\\.");
			if (parts.length != 3) {
				throw new IllegalArgumentException("Invalid JWT token format");
			}
			// 두 번째 부분이 payload이다.
			String payload = parts[1];
			// Base64Url 디코딩 (Base64Url은 표준 Base64와 약간 다름)
			byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
			// 디코딩된 바이트 배열을 문자열로 변환
			String decodedPayload = new String(decodedBytes);
			// JSON 파싱하여 Map으로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			Map payloadJson = objectMapper.readValue(decodedPayload, Map.class);
			String nickname = payloadJson.get("nickname").toString();
			return nickname;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Map<String, String> token(String code) {
		HashMap<String, String> map = new HashMap<>();
		map.put("grant_type", "authorization_code");
		map.put("client_id", "ca3adf9a52671fdbb847b809c0fdb980");
		map.put("redirect_uri", "https://dev.mouda.site/v1/auth/kakao/oauth");
		map.put("code", code);
		return map;
	}
}
