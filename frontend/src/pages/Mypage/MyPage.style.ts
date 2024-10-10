import { css, Theme } from '@emotion/react';

export const AccountButton = (props: { theme: Theme }) => css`
  ${props.theme.typography.b2}
  background: none;
  border: none;
`;

export const mainContainer = () => css`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
`;

export const editButton = (props: { theme: Theme }) => css`
  display: flex;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  width: 25rem;
  padding: 1.6rem 5.9rem;

  color: ${props.theme.colorPalette.white[100]};

  background-color: ${props.theme.semantic.primary};
  border: none;
  border-radius: 3rem;

  &:active {
    background-color: ${props.theme.colorPalette.orange[900]};
  }
`;
