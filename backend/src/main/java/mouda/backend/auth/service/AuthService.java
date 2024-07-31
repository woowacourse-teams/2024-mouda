package mouda.backend.auth.service;

import mouda.backend.auth.dto.LoginRequest;
import mouda.backend.auth.dto.LoginResponse;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.security.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final MemberRepository memberRepository;


    public AuthService(JwtProvider jwtProvider, MemberRepository memberRepository) {
        this.jwtProvider = jwtProvider;
        this.memberRepository = memberRepository;
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
}
