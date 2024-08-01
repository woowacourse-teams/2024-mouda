import {
  chatMessageStyle,
  messageContainer,
  messageStyle,
  senderStyle,
  timeStyle,
} from './Chatstyle';

import UserPreview from '@_components/UserPreview/UserPreview';
import { formatHhmmToKoreanWithPrefix } from '@_utils/formatters';
import { useTheme } from '@emotion/react';

export interface ChatMessageProps {
  sender: string;
  message: string;
  isMyMessage: boolean;
  time: string;
  imageUrl?: string;
}

export default function Chat(props: ChatMessageProps) {
  const { sender, message, isMyMessage, time, imageUrl } = props;
  const theme = useTheme();
  return (
    <div css={chatMessageStyle({ isMyMessage })}>
      <UserPreview imageUrl={imageUrl} />
      <div css={messageContainer({ isMyMessage })}>
        <span css={senderStyle({ theme })}>{sender}</span>
        <div css={messageStyle({ theme, isMyMessage })}>{message}</div>
        <span css={timeStyle({ theme })}>
          {formatHhmmToKoreanWithPrefix(time)}
        </span>
      </div>
    </div>
  );
}
