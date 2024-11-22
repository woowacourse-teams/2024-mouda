import * as S from './StickyTriSectionHeader.style';

import { MutableRefObject, PropsWithChildren, useEffect, useRef } from 'react';

import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';
import { useTheme } from '@emotion/react';

// TODO: ref를 외부에서 쓸 수 있게 되면 해당 인터페이스를 PropsWithChildren으로 대체
interface StickyTriSectionHeaderProps extends PropsWithChildren {
  onEffect?: (ref: MutableRefObject<null | HTMLDivElement>) => void;
  afterEffect?: (ref: MutableRefObject<null | HTMLDivElement>) => void;
}
function StickyTriSectionHeader(props: StickyTriSectionHeaderProps) {
  const { children, onEffect, afterEffect } = props;
  const theme = useTheme();
  const headerRef = useRef(null);

  useEffect(() => {
    if (onEffect) onEffect(headerRef);
    return () => afterEffect && afterEffect(headerRef);
  }, [onEffect, afterEffect]);

  return (
    <div css={S.Header({ theme })} ref={headerRef}>
      <TriSectionHeader onEffect={onEffect} afterEffect={afterEffect}>
        {children}
      </TriSectionHeader>
    </div>
  );
}

StickyTriSectionHeader.Left = TriSectionHeader.Left;
StickyTriSectionHeader.Center = TriSectionHeader.Center;
StickyTriSectionHeader.Right = TriSectionHeader.Right;

export default StickyTriSectionHeader;
