import { Theme, css } from '@emotion/react';

export const containerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 4rem;
  padding: 2rem 0;
`;

export const titleBox = () => css`
  display: flex;
  flex-direction: column;
  gap: 4px;
  align-items: flex-start;
`;

export const title = (props: { theme: Theme }) => css`
  ${props.theme.typography.h4};
`;
