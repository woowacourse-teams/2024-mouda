import { Theme, css } from '@emotion/react';

export const container = css`
  display: flex;
  align-items: center;
  justify-content: space-between;

  height: 10rem;
  padding: 0 2rem;

  border-radius: 25px;
  box-shadow: 0 0 10px 0 #00000040;
`;

export const peopleContainer = css`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;

export const smallGrey400 = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.small}
  color:${theme.colorPalette.grey[400]}
`;
