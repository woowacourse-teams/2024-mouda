import { css, Theme } from '@emotion/react';

export const body = ({
  theme,
  currentY,
  size,
  isDragging,
}: {
  theme: Theme;
  currentY: number;
  size?: 'small' | 'medium' | 'large' | 'full';
  isDragging: boolean;
}) => css`
  z-index: 2;

  /* 터치 드래그에 따른 Y축 이동 */
  transform: translateY(${currentY}px);

  display: flex;
  flex-direction: column;
  gap: 24px;

  width: 100%;
  max-width: 600px;
  height: ${size === 'medium'
    ? '50vh'
    : size === 'large'
      ? '80vh'
      : size === 'full'
        ? '100vh'
        : 'auto'};
  padding-bottom: 32px;

  background-color: ${theme.colorPalette.white[100]};
  border-radius: 28px 28px 0 0;

  /* 터치 드래그에 따른 Y축 이동 */
  transition: ${isDragging ? 'none' : 'transform 0.3s ease'};
`;
