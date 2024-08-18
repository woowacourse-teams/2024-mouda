import { Theme } from './../../common/theme/theme.type';
import { css } from '@emotion/react';

export const panel = (width: string, movedWidth: string) => css`
  position: absolute;
  transform: translate(0, ${movedWidth});

  overflow: hidden;

  width: ${width};

  border: 0;
  border-radius: 2.4rem;
  box-shadow: 0 0 12.6px 0 rgb(178 178 178 / 33%);
`;

export const option = ({
  theme,
  hasTopBorder,
}: {
  theme: Theme;
  hasTopBorder?: boolean;
}) => css`
  overflow: hidden;

  max-width: 100%;
  padding: 1rem;

  text-overflow: ellipsis;

  background-color: ${theme.colorPalette.white[100]};

  ${hasTopBorder && `border-top:1px solid ${theme.colorPalette.grey[200]}`};
`;
