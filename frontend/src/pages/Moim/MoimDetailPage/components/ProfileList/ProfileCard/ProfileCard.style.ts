import { css, Theme } from '@emotion/react';

export const profileCard = css`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  align-items: center;
  justify-content: flex-start;

  width: auto;
  width: fit-content;
  padding-top: 14px;
`;

export const profileName = (props: { theme: Theme }) => css`
  ${props.theme.typography.c2}
  text-align: center;
  white-space: pre-line;
`;
