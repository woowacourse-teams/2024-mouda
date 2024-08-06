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

export const messageStyle = ({
  theme,
  isMyMessage,
}: {
  theme: Theme;
  isMyMessage: boolean;
}) => css`
  ${theme.typography.b4};
  display: inline-block;

  max-width: 25rem;
  padding: 10px;

  word-break: break-all;

  background-color: ${isMyMessage
    ? theme.colorPalette.yellow[200]
    : theme.colorPalette.orange[100]};
  border-radius: ${isMyMessage ? '12px' : 0} ${isMyMessage ? 0 : '12px'} 12px
    12px;
`;

export const timeStyle = ({ theme }: { theme: Theme }) => css`
  ${theme.typography.c3}
  color:${theme.colorPalette.grey[400]};
`;
