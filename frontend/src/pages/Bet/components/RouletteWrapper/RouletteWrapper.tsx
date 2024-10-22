import * as S from './RouletteWrapper.style';

import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';

interface RouletteWrapperProps extends PropsWithChildren {
  title: string;
  description: string;
  mainDescription: string;
}

export default function RouletteWrapper(props: RouletteWrapperProps) {
  const theme = useTheme();
  const { title, description, mainDescription, children } = props;

  return (
    <div css={S.container({ theme })}>
      <h2 css={S.title({ theme })}>{title}</h2>
      <div css={S.descriptionBox({ theme })}>
        <div css={S.descriptionWrapper}>
          {description.split('*').map((str, index) => (
            <span
              key={`${str}-${index}`}
              css={index % 2 ? theme.typography.h5 : theme.typography.b4}
            >
              {str}
            </span>
          ))}
        </div>
      </div>
      <span css={S.time({ theme })}>{mainDescription}</span>
      <div css={S.rouletteContainer({ theme })}>{children}</div>
    </div>
  );
}
