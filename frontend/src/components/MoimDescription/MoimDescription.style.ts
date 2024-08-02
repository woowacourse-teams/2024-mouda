import { css, Theme } from '@emotion/react';

export const containerStyle = (props: { theme: Theme; color: string }) => css`
  display: flex;
  flex-direction: column;
  gap: 8px;

  padding: 20px 24px;

  color: ${props.color === 'grey'
    ? props.theme.colorPalette.black[100]
    : props.theme.colorPalette.white[100]};

  background: ${props.color === 'grey'
    ? props.theme.colorPalette.grey[100]
    : props.theme.semantic.primary};
  border-radius: 14px;
`;

export const titleStyle = (props: { theme: Theme }) => css`
  ${props.theme.typography.s2}
`;

export const descriptionStyle = (props: { theme: Theme }) => css`
  display: flex;
  flex-direction: column;
  gap: 10px;

  ${props.theme.typography.b3}
`;
