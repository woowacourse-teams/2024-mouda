import * as S from './FormHeader.style';

import BackLogo from '../../../common/assets/back.svg';
import { ReactNode } from 'react';

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
        <BackLogo />
      </span>
      <span css={S.headerTitleStyle}>{children}</span>
      <span></span>
    </header>
  );
}
