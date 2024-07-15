import { ReactNode } from 'react';
import BackArrowLogo from './BackArrowLogo';
import * as S from './FormHeader.style';

interface FormHeaderProps {
  onBackArrowClick: () => void;
  children: ReactNode;
}

export default function FormHeader(props: FormHeaderProps) {
  const { children, onBackArrowClick } = props;

  return (
    // TODO: '모임등록하기'가 정가운데에 오지 않음
    <header css={S.headerStyle}>
      <span onClick={onBackArrowClick}>
        <BackArrowLogo />
      </span>
      <span css={S.headerTitleStyle}>{children}</span>
      <span></span>
    </header>
  );
}
