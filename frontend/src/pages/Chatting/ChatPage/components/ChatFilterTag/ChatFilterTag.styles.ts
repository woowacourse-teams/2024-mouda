import { Theme, css } from '@emotion/react';

export const tag = ({
  theme,
  isChecked,
}: {
  theme: Theme;
  isChecked: boolean;
}) => css`
  ${theme.typography.b4};
  box-sizing: content-box;
  height: 25px;
  padding: 2px 10px 0;

  color: ${isChecked
    ? theme.colorPalette.white[100]
    : theme.colorPalette.grey[600]};
  text-align: center;

  background-color: ${isChecked
    ? theme.colorPalette.orange[400]
    : theme.colorPalette.white[100]};
  border: ${isChecked
      ? theme.colorPalette.white[100]
      : theme.colorPalette.grey[300]}
    1px solid;
  border-radius: 15px;
`;
