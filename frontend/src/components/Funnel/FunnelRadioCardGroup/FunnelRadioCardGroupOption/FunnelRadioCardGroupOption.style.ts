import { css, Theme } from '@emotion/react';

export const container = ({
  theme,
  isSelected,
}: {
  theme: Theme;
  isSelected: boolean;
}) => css`
  display: flex;
  justify-content: space-between;

  padding: 30px 20px 22px;

  color: ${isSelected
    ? theme.colorPalette.white[100]
    : theme.colorPalette.black[100]};

  background-color: ${isSelected
    ? theme.semantic.primary
    : theme.colorPalette.orange[50]};
  border-radius: 24px;
`;

export const contentContainer = () => css`
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: start;
  justify-content: center;
`;

export const selectionContainer = () => css`
  display: flex;
  align-items: center;
`;
