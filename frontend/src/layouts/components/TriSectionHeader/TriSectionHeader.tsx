import * as S from './TriSectionHeader.style';

import { PropsWithChildren } from 'react';

interface TriSectionHeaderProps extends PropsWithChildren {
  borderBottomColor?: string;
}
function TriSectionHeader(props: TriSectionHeaderProps) {
  const { children, borderBottomColor } = props;
  return (
    <header css={S.getTriSectionHeaderStyle({ borderBottomColor })}>
      {children}
    </header>
  );
}

TriSectionHeader.Left = function Left(props: PropsWithChildren) {
  const { children } = props;
  return <div css={S.leftSectionStyle}>{children}</div>;
};

TriSectionHeader.Center = function Center(props: PropsWithChildren) {
  const { children } = props;
  return <div css={S.centerSectionStyle}>{children}</div>;
};

TriSectionHeader.Right = function Right(props: PropsWithChildren) {
  const { children } = props;
  return <div css={S.rightSectionStyle}>{children}</div>;
};

export default TriSectionHeader;