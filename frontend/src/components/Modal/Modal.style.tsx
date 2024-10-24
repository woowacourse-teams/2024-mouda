import { Theme, css } from '@emotion/react';

export const dimmer = ({ hasDarkDimmer }: { hasDarkDimmer?: boolean }) => css`
  position: fixed;
  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;

  background-color: ${hasDarkDimmer ? 'rgba(0,0,0,23%)' : 'transparent'};
`;

export const content = ({
  position,
  theme,
}: {
  position: 'bottom' | 'center';
  theme: Theme;
}) => {
  const defaultStyle = css`
    position: fixed;

    max-width: 100%;
    padding: 2.4rem 3.2rem;

    background-color: ${theme.colorPalette.white[100]};
    border-radius: 1rem;
    box-shadow: 0 0 10px rgb(0 0 0 / 25%);
  `;
  if (position === 'center') {
    return css`
      ${defaultStyle}
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    `;
  }
  if (position === 'bottom') {
    return css`
      ${defaultStyle}
      bottom: 0;
      left: 50%;
      transform: translateX(-50%);

      margin: 0 auto;

      border-bottom-right-radius: 0;
      border-bottom-left-radius: 0;
    `;
  }
};
