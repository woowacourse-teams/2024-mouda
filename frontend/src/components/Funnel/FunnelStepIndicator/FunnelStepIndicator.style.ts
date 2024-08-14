import { css, Theme } from '@emotion/react';

export const container = css`
  display: flex;
  gap: 0.6rem;
  align-items: center;
  justify-content: center;
`;

export const step = css`
  width: 0.8rem;
  height: 0.8rem;
  border-radius: 50%;
  transition: background-color 0.3s ease;
`;

export const activeStep = ({ theme }: { theme: Theme }) => css`
  background-color: ${theme.semantic.primary};
`;

export const inactiveStep = ({ theme }: { theme: Theme }) => css`
  background-color: ${theme.semantic.disabled};
`;
