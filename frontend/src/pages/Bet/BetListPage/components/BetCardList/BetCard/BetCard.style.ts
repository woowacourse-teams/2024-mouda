import { Theme, css } from '@emotion/react';

export const cardBox = css`
  display: flex;
  gap: 1.6rem;
  justify-content: space-between;

  width: 100%;
  padding: 2rem 2.4rem;

  background: white;
  border-radius: 24px;
  box-shadow: rgb(0 0 0 / 12%) 0 3px 8px;
`;

export const leftSection = css`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

export const title = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.Giant}
`;

export const count = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.b3}
  color: ${theme.colorPalette.black[30]};
`;

export const tagBox = css`
  display: flex;
  align-items: flex-start;
  justify-content: flex-end;
  min-width: 80px;
`;
