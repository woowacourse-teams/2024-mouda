import { css, Theme } from '@emotion/react';

export const ProfileBox = css`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  align-items: center;

  width: auto;
  width: fit-content;
`;

export const ProfileName = (props: { theme: Theme }) => css`
  ${props.theme.typography.s2}
`;
