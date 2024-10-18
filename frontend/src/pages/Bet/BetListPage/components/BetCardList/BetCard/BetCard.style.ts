import { Theme, css } from '@emotion/react';

export const cardBox = css`
  display: flex;
  justify-content: space-between;

  padding: 1.6rem 2rem 1.6rem 3.2rem;

  background: white;
  border-radius: 16px;
  box-shadow: 0 7px 29px rgb(100 100 111 / 20%);
`;

export const leftSection = css`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
  align-items: flex-start;
`;

export const title = ({ theme }: { theme: Theme }) => css`
  font-size: 1.4rem;
  font-weight: bold;
  color: ${theme.colorPalette.grey[400]};
`;

export const deadline = ({
  theme,
  isAnnounced,
  leftMinute,
}: {
  theme: Theme;
  isAnnounced: boolean;
  leftMinute: number;
}) => css`
  font-size: ${theme.typography.b2};
  font-size: 2.2rem;
  font-weight: bold;
  color: ${isAnnounced || leftMinute < 0
    ? theme.colorPalette.grey[400]
    : leftMinute === 0
      ? theme.colorPalette.red[400]
      : leftMinute < 5
        ? theme.colorPalette.yellow[700]
        : leftMinute < 10
          ? theme.colorPalette.yellow[700]
          : leftMinute < 15
            ? theme.colorPalette.green[300]
            : theme.colorPalette.black[100]};
`;

export const participantCount = ({ theme }: { theme: Theme }) => css`
  display: flex;
  align-items: center;
  font-size: 1.2rem;
  color: ${theme.colorPalette.grey[400]};
`;

export const banner = ({ theme }: { theme: Theme }) => css`
  padding: 0.5rem 0.9rem;

  font-size: 1rem;
  font-weight: bold;
  color: white;

  background-color: ${theme.semantic.primary};
  border-radius: 0.4rem;
`;

export const rightSection = css`
  display: flex;
  align-items: center;
`;
