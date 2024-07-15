import { PropsWithChildren } from 'react';
import BackArrowLogo from './BackArrowLogo';
import * as S from './FormHeader.style';

export default function FormHeader(props: PropsWithChildren) {
  const { children } = props;

  return (
    // TODO: '모임등록하기'가 정가운데에 오지 않음
    <header css={S.headerStyle}>
      <span>
        <BackArrowLogo />
      </span>
      <span css={S.headerTitleStyle}>{children}</span>
      <span></span>
    </header>
  );
}
