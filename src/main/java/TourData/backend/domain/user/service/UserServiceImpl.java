package TourData.backend.domain.user.service;

import static TourData.backend.domain.user.exception.UserExceptionMessage.DUPLICATE_NAME;

import TourData.backend.domain.user.dto.UserSignUpRequest;
import TourData.backend.domain.user.exception.UserException;
import TourData.backend.domain.user.model.entity.User;
import TourData.backend.domain.user.model.Role;
import TourData.backend.domain.user.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserSerivce{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Override
    public void signUp(UserSignUpRequest requestParam) {
        // 이름 중복 검증
        validateDuplicateName(requestParam.username());
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestParam.password());
        // User 엔티티 저장
        saveUser(requestParam, encodedPassword);
    }

    private void validateDuplicateName(String username){
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()) {
            throw new UserException(DUPLICATE_NAME.getMessage());
        }
    }

    private void saveUser(UserSignUpRequest requestParam, String encodedPassword){
        User user = User.builder()
                .username(requestParam.username())
                .email(requestParam.email())
                .password(encodedPassword)
                .roles(Collections.singletonList(Role.USER))
                .build();
        userRepository.save(user);
    }

}
