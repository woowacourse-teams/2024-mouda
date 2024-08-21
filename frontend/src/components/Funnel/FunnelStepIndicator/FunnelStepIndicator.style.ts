import { css, Theme } from '@emotion/react';

export const container = css`
  display: flex;
  flex-direction: column;
  gap: 4px;
  align-items: end;

  width: 100%;
  padding: 0 22px;
`;

export const progressBar = ({ theme }: { theme: Theme }) => css`
  overflow: hidden;

  width: 100%;
  height: 4px;

  background-color: ${theme.semantic.disabled};
  border-radius: 2px;
`;

export const progress = ({
  theme,
  progress,
}: {
  theme: Theme;
  progress: number;
}) => css`
  width: ${progress}%;
  height: 100%;
  background-color: ${theme.semantic.primary};
  transition: width 0.3s ease-out;
`;
