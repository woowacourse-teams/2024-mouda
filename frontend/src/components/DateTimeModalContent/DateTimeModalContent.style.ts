import { Theme, css } from '@emotion/react';

export const content = ({ theme }: { theme: Theme }) => css`
  display: flex;
  flex-direction: column;
  width: 75vw;
  background-color: ${theme.semantic.white};
`;

export const buttonContainer = css`
  display: flex;
  flex-direction: row;
  gap: 1rem;
  justify-content: space-evenly;

  height: 4rem;
  margin: 1rem;
`;

export const buttonWrapper = css`
  width: 75%;

  button {
    height: 5rem;
    padding: 0;
  }
`;
