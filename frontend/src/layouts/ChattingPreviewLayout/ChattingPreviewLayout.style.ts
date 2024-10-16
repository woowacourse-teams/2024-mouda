import { Theme, css } from '@emotion/react';

export const layoutStyle = ({ theme }: { theme: Theme }) => css`
  overflow-y: scroll;
  display: flex;
  flex-direction: column;

  height: 100vh;

  background-color: ${theme.colorPalette.grey[100]};

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const headerBottom = css`
  position: sticky;
  top: 5rem;
  left: -2rem;
  margin: 2rem 2rem 0;
`;
