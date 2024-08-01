import LoginForm from '@_components/LoginForm/LoginForm';
import LoginLayout from '@_layouts/LoginLayout/LoginLayout';

export default function LoginPage() {
  return (
    <LoginLayout>
      <LoginLayout.Header>로그인</LoginLayout.Header>
      <LoginLayout.Main>
        <LoginForm />
      </LoginLayout.Main>
    </LoginLayout>
  );
}
