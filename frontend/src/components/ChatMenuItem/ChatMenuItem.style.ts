import { Theme, css } from '@emotion/react';

export const item = css`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 7.5rem;
  height: 8rem;
`;

export const button = ({ theme }: { theme: Theme }) => {
  return css`
    display: flex;
    align-items: center;
    justify-content: space-evenly;

    width: 5.6rem;
    height: 5.6rem;

    background-color: ${theme.colorPalette.orange[50]};
    border: ${theme.colorPalette.orange[300]} 0.2rem solid;
    border-radius: 15%;
  `;
};
