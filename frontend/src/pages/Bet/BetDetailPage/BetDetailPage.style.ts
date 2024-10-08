import { css, Theme } from '@emotion/react';

export const containerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

export const titleBox = () => css`
  display: flex;
  gap: 1rem;
  align-items: center;
`;

export const title = (props: { theme: Theme }) => css`
  ${props.theme.typography.h4};
`;
