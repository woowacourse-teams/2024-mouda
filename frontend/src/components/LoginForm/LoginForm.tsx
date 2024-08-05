import Button from '@_components/Button/Button';
import LabeledInput from '@_components/Input/MoimInput';
import * as S from './LoginForm.style';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ROUTES from '@_constants/routes';
import { setToken } from '@_utils/tokenManager';
import { login } from '@_apis/auth';

// TODO: 로그인 기능 요구사항 변경 예정
export default function LoginForm() {
  const navigate = useNavigate();

  const [nickname, setNickname] = useState('');
  const [isValid, setIsValid] = useState(false);

  const handleNicknameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const handleLoginFormSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const response = await login({ nickname });
    setToken(response.data.accessToken);
    navigate(ROUTES.main);
  };

  useEffect(() => {
    setIsValid(nickname.length > 0);
  }, [nickname]);

  return (
    <form css={S.formContainerStyle} onSubmit={handleLoginFormSubmit}>
      <div css={S.inputContainerStyle}>
        <LabeledInput
          value={nickname}
          title="닉네임"
          onChange={handleNicknameChange}
        />
      </div>
      <Button shape="bar" onClick={() => {}} disabled={!isValid}>
        로그인
      </Button>
    </form>
  );
}
