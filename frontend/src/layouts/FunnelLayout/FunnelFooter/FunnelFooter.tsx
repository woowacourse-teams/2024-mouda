import { PropsWithChildren, useEffect, useState } from 'react';
import * as S from './FunnelFooter.style';

interface FunnelFooterProps {
  isResize?: boolean;
}

export default function FunnelFooter(
  props: PropsWithChildren<FunnelFooterProps>,
) {
  const { children, isResize = true } = props;
  const [keyboardHeight, setKeyboardHeight] = useState(0);

  useEffect(() => {
    if (!isResize) return;

    const handleResize = () => {
      if (window.visualViewport) {
        const keyboardHeight =
          window.innerHeight - window.visualViewport.height;
        setKeyboardHeight(Math.max(0, keyboardHeight));
        console.log(Math.max(0, keyboardHeight));
      }
    };

    window.visualViewport?.addEventListener('resize', handleResize);
    return () =>
      window.visualViewport?.removeEventListener('resize', handleResize);
  }, [isResize]);

  return <div css={S.container(keyboardHeight)}>{children}</div>;
}
