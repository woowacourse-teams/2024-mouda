import { css, Theme } from '@emotion/react';

export const container = ({
  theme,
  isSelected,
  description,
}: {
  theme: Theme;
  isSelected: boolean;
  description?: string;
}) => css`
  display: flex;
  justify-content: space-between;

  padding: ${description ? `30px 20px 22px` : `12px`};

  color: ${isSelected
    ? theme.colorPalette.white[100]
    : theme.colorPalette.black[100]};

  background-color: ${isSelected
    ? theme.semantic.primary
    : theme.colorPalette.orange[50]};
  border-radius: ${description ? `24px` : `8px`};
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
