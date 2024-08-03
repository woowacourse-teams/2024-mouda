import { DISPLAY_MAX_WIDTH } from '@_constants/styles';
import { css, Theme } from '@emotion/react';

export const navigationBarContainer = (props: { theme: Theme }) => {
  const { theme } = props;

  return css`
    display: flex;
    justify-content: space-between;

    width: 100%;
    max-width: ${DISPLAY_MAX_WIDTH};
    height: 100%;

    background-color: ${theme.colorPalette.white[100]};
    box-shadow: 0 -2px 10px rgb(0 0 0 / 10%);
  `;
};
export const navigationBarList = css`
  display: flex;
  width: 100%;
`;
