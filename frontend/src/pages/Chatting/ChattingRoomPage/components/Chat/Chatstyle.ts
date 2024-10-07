import { Theme, css } from '@emotion/react';

export const chatMessageStyle = ({
  isMyMessage,
}: {
  isMyMessage: boolean;
}) => css`
  display: flex;
  flex-direction: ${isMyMessage ? 'row-reverse' : 'row'};
  gap: 1rem;
`;

export const messageContainer = ({
  isMyMessage,
}: {
  isMyMessage: boolean;
}) => css`
  display: flex;
  flex-direction: column;
  align-items: ${isMyMessage ? 'flex-end;' : 'flex-start;'};
`;

export const senderStyle = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.Medium}
  color:${theme.colorPalette.grey[900]};
`;

export const timeStyle = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.c3}
  color:${theme.colorPalette.grey[400]};
`;

export const chatLine = ({ isMyMessage }: { isMyMessage: boolean }) => css`
  display: flex;
  flex-direction: ${isMyMessage ? 'row-reverse' : 'row'};
  gap: 0.5rem;
  align-items: flex-end;
`;
