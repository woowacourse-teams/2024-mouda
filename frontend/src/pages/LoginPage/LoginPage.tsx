import LoginForm from '@_components/LoginForm/LoginForm';
import LoginLayout from '@_layouts/LoginLayout/LoginLayout';
import { useTheme } from '@emotion/react';

export default function LoginPage() {
  const theme = useTheme();
  return (
    <LoginLayout>
      <LoginLayout.Header>
        <span css={theme.typography.h5}>로그인</span>
      </LoginLayout.Header>
      <LoginLayout.Main>
        <LoginForm />
      </LoginLayout.Main>
    </LoginLayout>
  );
}
