/* stylelint-disable font-family-no-missing-generic-family-keyword */

import * as S from './RouletteWrapper.style';

import { css, useTheme } from '@emotion/react';

import { PropsWithChildren } from 'react';

interface RouletteWrapperProps extends PropsWithChildren {
  title: string;
  description: string;
  mainDescription: string;
}
const bitbit = 'bitbit';

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
              css={
                index % 2
                  ? css`
                      font: 400 normal 2.5rem ${bitbit};
                    `
                  : css`
                      font: 300 normal 2rem ${bitbit};
                    `
              }
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
