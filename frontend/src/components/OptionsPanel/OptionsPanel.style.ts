import { Theme } from './../../common/theme/theme.type';
import { css } from '@emotion/react';

export const panel = ({
  width,
  movedHeight,
  movedWidth,
  maxHeight,
}: {
  width: string;
  movedHeight: string;
  movedWidth: string;
  maxHeight?: string;
}) => css`
  ${maxHeight && 'max-height:' + maxHeight};
  position: absolute;
  transform: translateX(${movedWidth}) translateY(${movedHeight});

  overflow: hidden scroll;
  display: flex;
  flex-direction: column;

  width: ${width};

  background-color: white;
  border: 0;
  border-radius: 2.4rem;
  box-shadow: 0 0 12.6px 0 rgb(178 178 178 / 33%);

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const option = ({
  theme,
  hasTopBorder,
}: {
  theme: Theme;
  hasTopBorder?: boolean;
}) => css`
  overflow: hidden;
  flex-shrink: 0;

  max-width: 100%;
  padding: 1rem;

  text-overflow: ellipsis;

  background-color: ${theme.colorPalette.white[100]};

  ${hasTopBorder && `border-top:1px solid ${theme.colorPalette.grey[200]}`};
`;

export const dimmer = css`
  position: fixed;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;
`;
