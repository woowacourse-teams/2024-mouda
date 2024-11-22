import { Theme, css } from '@emotion/react';

export const containerStyle = () => css`
  display: flex;
  flex-direction: column;
  gap: 14px;
`;

export const titleStyle = (props: { theme: Theme }) => css`
  ${props.theme.typography.s1}
`;

export const cardStyle = (props: { theme: Theme }) => css`
  padding: 16px 24px;
  color: ${props.theme.colorPalette.grey[700]};
  background-color: ${props.theme.semantic.secondary};
  border-radius: 18px;
`;

export const rowStyle = (props: { theme: Theme }) => css`
  display: flex;
  ${props.theme.typography.h5}
  justify-content: space-between;
  padding: 5px 0;
  border-top: 1px solid ${props.theme.colorPalette.grey[400]};

  &:first-of-type {
    padding-top: 0;
    border-top: none;
  }

  &:last-of-type {
    padding-bottom: 0;
    border-bottom: none;
  }
`;
