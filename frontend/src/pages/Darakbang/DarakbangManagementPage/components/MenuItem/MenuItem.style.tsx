import { Theme, css } from '@emotion/react';

export const item = ({
  theme,
  isLastItem,
}: {
  theme: Theme;
  isLastItem: boolean;
}) => css`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  height: 9rem;
  padding: 3rem;

  border-top: 1px solid ${theme.colorPalette.grey[300]};
  ${isLastItem && `border-bottom:1px solid ${theme.colorPalette.grey[300]};`}
`;
