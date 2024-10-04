import { Theme } from '@_common/theme/theme.type';
import { css } from '@emotion/react';

export const separatorWrapper = css`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  width: 100%;
`;

export const separator = ({ theme }: { theme: Theme }) => css`
  display: block;

  width: 20rem;

  text-align: center;

  background-color: ${theme.colorPalette.grey[200]};
  border-radius: 10px;
`;
