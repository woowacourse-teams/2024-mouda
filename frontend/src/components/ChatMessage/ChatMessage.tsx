import {
  ChatMessageStyle,
  messageContainer,
  messageStyle,
  senderStyle,
  timeStyle,
} from './ChatMessage.style';

import UserPreview from '@_components/UserPreview/UserPreview';
import { formatHhmmToKoreanWithPrefix } from '@_utils/formatters';
import { useTheme } from '@emotion/react';

interface ChatMessageProps {
  sender: string;
  message: string;
  isMyMessage: boolean;
  time: string;
  imageUrl?: string;
}

export default function ChatMessage(props: ChatMessageProps) {
  const { sender, message, isMyMessage, time, imageUrl } = props;
  const theme = useTheme();
  return (
    <div css={ChatMessageStyle({ isMyMessage })}>
      <UserPreview imageUrl={imageUrl} />
      <div css={messageContainer}>
        <span css={senderStyle({ theme })}>{sender}</span>
        <div css={messageStyle({ theme, isMyMessage })}>{message}</div>
        <span css={timeStyle({ theme })}>
          {formatHhmmToKoreanWithPrefix(time)}
        </span>
      </div>
    </div>
  );
}
