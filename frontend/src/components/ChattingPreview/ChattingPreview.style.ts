import { Theme, css } from '@emotion/react';

export const container = ({
  isStarted,
  theme,
}: {
  isStarted: boolean;
  theme: Theme;
}) => css`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 10rem;
  padding: 2rem;

  background-color: ${theme.colorPalette.white[100]};
  border: 0.3rem solid;
  border-color: ${isStarted
    ? theme.colorPalette.yellow[100]
    : theme.colorPalette.orange[100]};
  border-radius: 25px;
  box-shadow: 0 0 10px 0 #00000040;
`;

export const titleContainer = css`
  display: flex;
  gap: 1rem;
  align-items: center;
`;

export const tag = ({
  theme,
  isStarted,
}: {
  theme: Theme;
  isStarted: boolean;
}) => css`
  ${theme.typography.small}
  display: flex;
  align-items: center;
  justify-content: center;

  height: 24px;
  padding: 0.2rem 0.6rem;

  color: ${isStarted
    ? theme.colorPalette.yellow[800]
    : theme.colorPalette.white[100]};

  background-color: ${isStarted
    ? theme.colorPalette.yellow[50]
    : theme.colorPalette.orange[100]};
  border-radius: 1rem;
`;

export const messageContainer = css`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  justify-content: space-evenly;
`;

export const peopleContainer = css`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;

export const unreadContentCountContainer = css`
  overflow: hidden;
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
  align-items: flex-start;
`;

export const unreadContentWrapper = css`
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;

  max-height: 3rem;

  text-overflow: ellipsis;
  white-space: pre-line;
`;

export const smallGrey400 = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.small}
  color:${theme.colorPalette.grey[400]}
`;
