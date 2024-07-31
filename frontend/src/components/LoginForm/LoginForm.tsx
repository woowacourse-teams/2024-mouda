import Button from '@_components/Button/Button';
import LabeledInput from '@_components/Input/MoimInput';
import * as S from './LoginForm.style';

export default function LoginForm() {
  return (
    <form css={S.formContainerStyle}>
      <div css={S.inputContainerStyle}>
        <LabeledInput title="아이디" />
        <LabeledInput title="비밀번호" />
      </div>
      <Button shape="bar" onClick={() => {}} disabled>
        로그인
      </Button>
    </form>
  );
}
