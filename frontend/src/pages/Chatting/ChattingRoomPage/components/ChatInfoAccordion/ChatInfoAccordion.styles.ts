import { Theme, css } from '@emotion/react';

export const accordion = css`
  display: flex;
  align-items: center;
  justify-content: space-between;

  height: 7rem;
  padding: 2rem;

  background-color: white;
  border-radius: 0 0 1rem 1rem;
  box-shadow: 0 4px 5px rgb(0 0 0 / 30%);
`;

export const textArea = css`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;

export const tag = ({
  theme,
  tagTheme,
}: {
  theme: Theme;
  tagTheme: 'yellow' | 'orange';
}) => css`
  ${theme.typography.small}
  display: flex;
  align-items: center;
  justify-content: center;

  height: 24px;
  padding: 0.2rem 0.6rem;

  color: ${tagTheme === 'yellow'
    ? theme.colorPalette.yellow[800]
    : theme.colorPalette.white[100]};

  background-color: ${tagTheme === 'yellow'
    ? theme.colorPalette.yellow[50]
    : theme.colorPalette.orange[100]};
  border-radius: 1rem;
`;
