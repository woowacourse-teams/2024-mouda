import { Theme, css } from '@emotion/react';

const bitbit = 'bitbit';

export const container = ({ theme }: { theme: Theme }) => css`
  display: flex;
  flex-direction: column;
  align-items: center;

  padding: 3.5rem;

  background-color: ${theme.colorPalette.yellow[200]};
  border-radius: 24px;
  box-shadow: 0 5px 15px rgb(0 0 0 / 20%);
`;

export const title = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.h2}
  width: 30rem;

  font: 300 normal 5rem ${bitbit};
  text-align: center;
  word-wrap: break-word;
  white-space: pre-line;
`;

export const descriptionBox = ({ theme }: { theme: Theme }) => css`
  display: flex;
  align-items: center;
  justify-content: space-evenly;

  height: 4rem;
  padding: 0.5rem 2rem;

  color: ${theme.colorPalette.white[100]};

  background-color: ${theme.colorPalette.orange[300]};
  border-radius: 1rem;
`;

export const descriptionWrapper = css`
  display: flex;
  gap: 0.4rem;
  align-items: center;
`;

export const time = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.h2}
  height: 48px;
  margin: 3rem 0;
  font: 500 normal 4rem ${bitbit};
`;

export const rouletteContainer = ({ theme }: { theme: Theme }) => css`
  padding: 2rem;
  background-color: ${theme.colorPalette.grey[400]};
  border-radius: 28px;
`;