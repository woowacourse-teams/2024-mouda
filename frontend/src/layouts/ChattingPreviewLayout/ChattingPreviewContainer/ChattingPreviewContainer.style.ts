import { Theme, css } from '@emotion/react';

export const Container = ({ theme }: { theme: Theme }) => css`
  overflow-y: scroll;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
  justify-content: flex-start;

  width: 100%;
  padding: 28px 28px 130px;

  background-color: ${theme.colorPalette.grey[100]};

  &::-webkit-scrollbar {
    display: none;
  }
`;
