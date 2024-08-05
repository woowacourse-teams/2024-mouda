import { Theme, css } from '@emotion/react';

export const list = ({ theme }: { theme: Theme }) => css`
  overflow-y: scroll;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 1rem;

  height: 100%;
  min-height: 100vh;
  padding: 2rem;

  background-color: ${theme.colorPalette.grey[100]};

  &::-webkit-scrollbar {
    display: none;
  }
`;
