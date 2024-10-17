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
`;

export const title = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.Giant}
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
  font-size: 22px;
  font-weight: bold;
  color: ${isAnnounced || leftMinute < 0
    ? theme.colorPalette.grey[400]
    : leftMinute < 5
      ? theme.colorPalette.red[400]
      : leftMinute < 10
        ? theme.colorPalette.yellow[700]
        : leftMinute < 15
          ? theme.colorPalette.green[300]
          : theme.colorPalette.black[100]};
`;

export const participantCount = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.b3}
  display: flex;
  align-items: center;
  color: ${theme.colorPalette.grey[400]};
`;

export const banner = ({ theme }: { theme: Theme }) => css`
  padding: 0.6rem 1rem;

  font-size: ${theme.typography.Medium};
  color: white;

  background-color: ${theme.semantic.primary};
  border-radius: 0.4rem;
`;

export const rightSection = css`
  display: flex;
  align-items: center;
`;
