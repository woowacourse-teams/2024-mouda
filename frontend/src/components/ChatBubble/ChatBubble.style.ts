import { Theme, css } from '@emotion/react';

export const chatBubble = ({
  theme,
  backgroundColor,
  isMyMessage,
}: {
  theme: Theme;
  isMyMessage: boolean;
  backgroundColor?: string;
}) => css`
  ${theme.typography.b4};
  display: inline-block;

  max-width: 25rem;
  padding: 10px;

  word-break: break-all;

  background-color: ${backgroundColor || theme.semantic.primary};
  border-radius: ${isMyMessage ? '12px' : 0} ${isMyMessage ? 0 : '12px'} 12px
    12px;
`;
