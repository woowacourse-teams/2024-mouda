import { Theme, css } from '@emotion/react';

const bitbit = 'bitbit';
export const profileCard = ({ theme }: { theme: Theme }) => css`
  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 2rem;

  font-size: 1.2em;

  background-color: ${theme.colorPalette.white[100]};
  border: 0;
  border-radius: 10px;
  box-shadow: 0 7px 29px rgb(100 100 111 / 20%);
`;

export const profileImage = ({ theme }: { theme: Theme }) => css`
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;

  width: 4.5rem;
  height: 4.5rem;

  background-color: ${theme.colorPalette.white[100]};
  border: 2px solid ${theme.colorPalette.grey[200]};
  border-radius: 300rem;
`;

export const profileNickname = ({ theme }: { theme: Theme }) => css`
  ${theme.coloredTypography.b1(theme.colorPalette.grey[400])}
  font: 300 normal 2rem ${bitbit};
  text-align: center;
  white-space: pre-line;
`;
