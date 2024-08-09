import { Theme, css } from '@emotion/react';

export const cardBox = css`
  display: flex;
  gap: 1.6rem;
  justify-content: space-between;

  width: 100%;
  padding: 2rem 2.4rem;

  background: white;
  border-radius: 2.5rem;
  box-shadow: rgb(0 0 0 / 24%) 0 3px 8px;
`;

export const contentWrapper = css`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

export const headerWrapper = css`
  display: flex;
  gap: 0.6rem;
  align-items: center;
`;

export const title = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.Giant}
`;

export const count = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.c1}
`;

export const countAccent = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.c1}
  color: ${theme.semantic.primary};
`;

export const description = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.c1}
`;

export const actionWrapper = css`
  display: flex;
`;

export const actionButton = css`
  display: flex;
  flex-direction: column;
  gap: 14px;
  align-items: center;
  justify-content: center;

  padding: 0;

  background: none;
  border: none;
`;

export const actionIconWrapper = ({ theme }: { theme: Theme }) => css`
  display: flex;

  padding: 8px;

  background-color: ${theme.colorPalette.grey[100]};
  border: none;
  border-radius: 16px;
  box-shadow: rgb(0 0 0 / 24%) 0 3px 8px;
`;

export const actionText = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.c2}
  white-space: nowrap;
`;
