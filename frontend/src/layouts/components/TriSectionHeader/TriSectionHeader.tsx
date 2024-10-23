import * as S from './TriSectionHeader.style';

import { MutableRefObject, PropsWithChildren, useEffect, useRef } from 'react';

import { useTheme } from '@emotion/react';

// TODO: ref를 외부에서 쓸 수 있게 되면 해당 인터페이스를 PropsWithChildren으로 대체
interface TriSectionHeaderProps extends PropsWithChildren {
  borderBottomColor?: string;
  onEffect?: (ref: MutableRefObject<null | HTMLDivElement>) => void;
  afterEffect?: (ref: MutableRefObject<null | HTMLDivElement>) => void;
}
function TriSectionHeader(props: TriSectionHeaderProps) {
  const { children, borderBottomColor, onEffect, afterEffect } = props;
  const theme = useTheme();
  const headerRef = useRef(null);

  useEffect(() => {
    if (onEffect) onEffect(headerRef);
    return () => afterEffect && afterEffect(headerRef);
  }, [onEffect, afterEffect]);

  return (
    <header
      css={S.getTriSectionHeaderStyle({ borderBottomColor, theme })}
      ref={headerRef}
    >
      {children}
    </header>
  );
}

TriSectionHeader.Left = function Left(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();
  return <div css={S.leftSectionStyle({ theme })}>{children}</div>;
};

TriSectionHeader.Center = function Center(props: PropsWithChildren) {
  const { children } = props;
  const theme = useTheme();
  return <div css={S.centerSectionStyle({ theme })}>{children}</div>;
};

TriSectionHeader.Right = function Right(props: PropsWithChildren) {
  const { children } = props;
  return <div css={S.rightSectionStyle}>{children}</div>;
};

export default TriSectionHeader;
