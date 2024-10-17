import { css, Theme } from '@emotion/react';

export const AccountButton = ({
  theme,
  isValidMyInfo = true,
}: {
  theme: Theme;
  isValidMyInfo?: boolean;
}) => css`
  ${theme.typography.b2}
  color: ${isValidMyInfo ? '' : theme.colorPalette.grey[200]};
  background: none;
  border: none;
`;

export const mainContainer = () => css`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
`;

export const editButton = ({ theme }: { theme: Theme }) => css`
  display: flex;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  width: 25rem;
  padding: 1.6rem 5.9rem;

  color: ${theme.colorPalette.white[100]};

  background-color: ${theme.semantic.primary};
  border: none;
  border-radius: 3rem;

  &:active {
    background-color: ${theme.colorPalette.orange[900]};
  }
`;
