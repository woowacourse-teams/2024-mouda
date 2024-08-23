import { PropsWithChildren, useEffect, useState } from 'react';
import * as S from './FunnelFooter.style';

export default function FunnelFooter(props: PropsWithChildren) {
  const { children } = props;
  const [keyboardHeight, setKeyboardHeight] = useState(0);

  useEffect(() => {
    const handleResize = () => {
      if (window.visualViewport) {
        const keyboardHeight =
          window.innerHeight - window.visualViewport.height;
        setKeyboardHeight(Math.max(0, keyboardHeight));
      }
    };

    window.visualViewport?.addEventListener('resize', handleResize);
    return () =>
      window.visualViewport?.removeEventListener('resize', handleResize);
  }, []);

  return <div css={S.container(keyboardHeight)}>{children}</div>;
}
