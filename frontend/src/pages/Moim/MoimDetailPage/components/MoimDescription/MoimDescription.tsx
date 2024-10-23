import * as S from './MoimDescription.style';

import { ReactNode } from 'react';
import { useTheme } from '@emotion/react';

interface MoimDescriptionProps {
  title: string;
  children: ReactNode;
  color?: string;
}

export default function MoimDescription(props: MoimDescriptionProps) {
  const theme = useTheme();
  const { title, children, color = '' } = props;

  if (title === '') {
    return;
  }

  return (
    <div css={S.containerStyle({ theme, color })}>
      <h2 css={S.titleStyle({ theme })} aria-label={title}>
        {title}
      </h2>
      <div
        css={S.descriptionStyle({ theme })}
        aria-label={typeof children === 'string' ? children : ''}
      >
        {children}
      </div>
    </div>
  );
}
